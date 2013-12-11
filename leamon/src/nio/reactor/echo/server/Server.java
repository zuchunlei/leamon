package nio.reactor.echo.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * NIO Reactorģʽ��Echo Serverʵ��
 */
public class Server {

	private String host;
	private int port;

	private AtomicInteger incr;// ������
	private volatile boolean running;

	private Poller[] pollers;// ��ѯ���󣬴���SocketChannel��I/O�¼�

	// private AtomicBoolean accept;// �������󱻽��յı�ʶ

	public Server(String host, int port) {
		this.host = host;
		this.port = port;
		this.incr = new AtomicInteger(Integer.MIN_VALUE);
		// this.accept = new AtomicBoolean();
		this.pollers = new Poller[Runtime.getRuntime().availableProcessors() + 1];// Poller�ĸ���Ϊ��ǰ����CPU+1
		for (int i = 0; i < pollers.length; i++) {
			pollers[i] = new Poller();
		}
	}

	/**
	 * �����������ӵĽ�����
	 */
	class Acceptor implements Runnable {
		public void run() {
			try {
				Selector selector = Selector.open();
				ServerSocketChannel servSockChannel = ServerSocketChannel
						.open();
				servSockChannel.configureBlocking(false);
				servSockChannel.socket().bind(
						new InetSocketAddress(host, port), 200);
				servSockChannel.register(selector, SelectionKey.OP_ACCEPT);
				while (running) {
					int selected = selector.select();
					if (selected > 0) {

						Iterator<SelectionKey> it = selector.selectedKeys()
								.iterator();
						while (it.hasNext()) {
							SelectionKey key = it.next();
							// System.out.println("��������");
							it.remove();// ������ʾ��ɾ��
							if (!key.isValid()) {
								key.cancel();
								key.channel().close();
								break;
							}

							// OP_ACCEPT�����¼�Ӧ����Acceptor�����ѣ�accept()���ص�SocketChannel����Poller������
							SocketChannel channel = accept(key);
							// ����ѡ��һ��Poller����keyע�ᵽPoller�ϡ�
							// int offset = Math.abs(incr.decrementAndGet()
							// % pollers.length);

							pollers[Math.abs(incr.decrementAndGet()
									% pollers.length)].registerChannel(channel);

							// while (!accept.compareAndSet(true, false))
							// ;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * ��Acceptor������OP_ACCEPT�����¼�
		 */
		SocketChannel accept(SelectionKey key) throws IOException {
			SocketChannel channel = null;
			if (key.isAcceptable()) {
				ServerSocketChannel servSockChannel = (ServerSocketChannel) key
						.channel();
				channel = servSockChannel.accept();
			}
			return channel;
		}
	}

	/**
	 * IO��ѯ���󣬴���SocketChannel��I/O�¼���
	 */
	class Poller implements Runnable {
		// private ConcurrentLinkedQueue<SelectionKey> channels;//
		// �ȴ�ע�ᵽPoller�е�SelectionKey

		// �ȴ�ע�ᵽPoller�е�SocketChannel����Channel��ServerSocketChannel.accept()���أ���Selectorû��ע���ϵ
		private ConcurrentLinkedQueue<SocketChannel> channels;

		private ConcurrentLinkedQueue<SelectionKey> readkeys;// ������ѡ�������
		private ConcurrentLinkedQueue<SelectionKey> writekeys;// д����ѡ�������

		// ȥ����д���ݳأ�ԭ������SelectionKey��ΪKey�������д������Map����Ϊʹ��SelectionKey�����ķ�ʽ���ݡ�
		// private ConcurrentHashMap<SelectionKey, ByteBuffer> datapools;//
		// ��дͨ�������ݳ�

		private AtomicBoolean wakeup;// ���ѱ�ʶ��ԭ���࣬������
		private Selector selector;

		private Executor executor;// ����IOִ�е��̳߳�

		public Poller() {
			// this.channels = new ConcurrentLinkedQueue<SelectionKey>();
			this.channels = new ConcurrentLinkedQueue<SocketChannel>();

			this.readkeys = new ConcurrentLinkedQueue<SelectionKey>();
			this.writekeys = new ConcurrentLinkedQueue<SelectionKey>();
			// this.datapools = new ConcurrentHashMap<SelectionKey,
			// ByteBuffer>();

			this.wakeup = new AtomicBoolean();// Ĭ��Ϊfalse

			try {
				this.selector = Selector.open();
			} catch (IOException e) {
			}
			this.executor = Executors.newCachedThreadPool();
		}

		public void run() {
			while (running) {
				try {
					int selected = selector.select(1000);// ѡ��ȴ�1s
					interRegisterEvent();// ͳһ�¼�ע�᷽��
					if (selected > 0) {
						Iterator<SelectionKey> it = selector.selectedKeys()
								.iterator();
						while (it.hasNext()) {
							SelectionKey key = it.next();
							it.remove();
							if (key.isValid() && key.isReadable()) {// ����������¼�
								// ȡ����Ȥ���������ݣ�ҵ����ע����Ȥд��
								unRegisterRead(key);// ȡ��������Ȥ��ע��
								IOReadWork work = new IOReadWork(this, key);
								executor.execute(work);
							}
							if (key.isValid() && key.isWritable()) {// ����д�����¼�
								// ȡ����Ȥд
								unRegisterWrite(key);
								IOWriteWork work = new IOWriteWork(this, key);
								executor.execute(work);
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * �ڲ�ͳһע���ŵ����¼�
		 */
		@SuppressWarnings("resource")
		void interRegisterEvent() {
			// ע���ŵ��Ľ����¼�
			SocketChannel channel = channels.poll();
			while (channel != null) {
				try {
					// if (key.isValid() && key.isAcceptable()) {
					// ServerSocketChannel servSockChannel =
					// (ServerSocketChannel) key
					// .channel();
					// SocketChannel channel = servSockChannel.accept();
					// // while (!accept.compareAndSet(false, true))
					// // ;

					channel.configureBlocking(false);// ���뽫�ŵ�����Ϊ������ģʽ
					channel.register(selector, SelectionKey.OP_READ);
					// } else {
					// key.cancel();
					// }
				} catch (IOException e) {
				}
				channel = channels.poll();
			}

			// ע���ŵ��Ķ������¼�
			SelectionKey key = readkeys.poll();
			while (key != null) {
				try {
					if (!key.isValid()) {
						key.cancel();
						key.channel().close();
					}
					// SocketChannel channel = (SocketChannel) key.channel();
					// SelectionKey sk = channel.register(selector,
					// key.interestOps() | SelectionKey.OP_READ);
					// System.out.println(sk.attachment() != null);
					key.interestOps(key.interestOps() | SelectionKey.OP_READ);
				} catch (IOException e) {
				}
				key = readkeys.poll();
			}
			// ע���ŵ���д�����¼�
			key = writekeys.poll();
			while (key != null) {
				try {
					if (!key.isValid()) {
						key.cancel();
						key.channel().close();
					}
					// ��Channel�ķ�ʽ����ע�ᣬ������Key�Ƿ�Ը�����Ӱ�죿
					// SocketChannel channel = (SocketChannel) key.channel();
					// SelectionKey sk = channel.register(selector,
					// key.interestOps() | SelectionKey.OP_WRITE);
					// System.out.println(sk.attachment() != null);
					key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
					// System.out.println(key.attachment() != null);
				} catch (IOException e) {
				}
				key = writekeys.poll();
			}

			// ���û��ѱ�ʶ
			wakeup.set(false);
		}

		// void registerChannel(SelectionKey key) {
		// channels.offer(key);
		// if (wakeup.compareAndSet(false, true)) {
		// selector.wakeup();
		// }
		// }
		/**
		 * ע���ŵ��Ľ����¼�
		 * 
		 * @param channel
		 *            �ȴ�ע���SocketChannel
		 */
		void registerChannel(SocketChannel channel) {
			channels.offer(channel);
			if (wakeup.compareAndSet(false, true)) {
				selector.wakeup();
			}
		}

		/**
		 * ע���ŵ��Ķ������¼�
		 * 
		 * @param key
		 */
		void registerRead(SelectionKey key) {
			readkeys.offer(key);
			if (wakeup.compareAndSet(false, true)) {
				selector.wakeup();
			}
		}

		/**
		 * ע���ŵ���д�����¼�
		 * 
		 * @param key
		 */
		void registerWrite(SelectionKey key) {
			writekeys.offer(key);
			if (wakeup.compareAndSet(false, true)) {
				selector.wakeup();
			}
		}

		/**
		 * ȡ����Ȥ��
		 * 
		 * @param key
		 */
		void unRegisterRead(SelectionKey key) {
			key.interestOps(key.interestOps() ^ SelectionKey.OP_READ);
		}

		/**
		 * ȡ����Ȥд
		 * 
		 * @param key
		 */
		void unRegisterWrite(SelectionKey key) {
			key.interestOps(key.interestOps() ^ SelectionKey.OP_WRITE);
		}

		/**
		 * ��д���ݵĻ�����
		 * 
		 * @param key
		 * @param buffer
		 */
		// void putData(SelectionKey key, ByteBuffer buffer) {
		// datapools.put(key, buffer);
		// }

		/**
		 * ͨ��Key����ȡ����
		 * 
		 * @param key
		 * @return
		 */
		// ByteBuffer getData(SelectionKey key) {
		// return datapools.get(key);
		// }
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public boolean start() {
		running = true;
		// ����Poller
		for (int i = 0; i < pollers.length; i++) {
			String name = "Poller Thread - " + i;
			new Thread(pollers[i], name).start();
		}
		// ����Acceptor
		new Thread(new Acceptor(), "Acceptor Thread").start();

		return running;
	}

	public void stop() {
		running = false;
	}
}
