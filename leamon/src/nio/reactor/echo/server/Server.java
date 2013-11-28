package nio.reactor.echo.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * NIO Reactorģʽ��Echo Serverʵ��
 */
public class Server {

	private String host;
	private int port;
	private int pcount;// Poller�ĸ���

	private AtomicInteger incr;// ������

	private volatile boolean running;

	private Poller[] pollers;// ��ѯ���󣬴���SocketChannel��I/O�¼�

	public Server(String host, int port) {
		this.host = host;
		this.port = port;
		this.pcount = Runtime.getRuntime().availableProcessors();
		this.incr = new AtomicInteger(Integer.MIN_VALUE);
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
				servSockChannel.register(selector, SelectionKey.OP_ACCEPT);
				while (running) {
					int selected = selector.select();
					if (selected > 0) {

						Iterator<SelectionKey> it = selector.selectedKeys()
								.iterator();
						while (it.hasNext()) {
							SelectionKey key = it.next();
							it.remove();// ������ʾ��ɾ��
							if (!key.isValid()) {
								key.cancel();
								key.channel().close();
								break;
							}
							// ����ѡ��һ��Poller����keyע�ᵽPoller�ϡ�
							pollers[Math.abs(incr.decrementAndGet()
									% pollers.length)].registerChannel(key);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class Poller implements Runnable {
		private ConcurrentLinkedQueue<SelectionKey> channels;// �ȴ�ע�ᵽPoller�е�SelectionKey
		private ConcurrentLinkedQueue<SelectionKey> readkeys;// ������ѡ�������
		private ConcurrentLinkedQueue<SelectionKey> writekeys;// д����ѡ�������

		private AtomicBoolean wakeup;// ���ѱ�ʶ��ԭ���࣬������

		private Selector selector;

		public Poller() {
			this.channels = new ConcurrentLinkedQueue<SelectionKey>();
			this.readkeys = new ConcurrentLinkedQueue<SelectionKey>();
			this.writekeys = new ConcurrentLinkedQueue<SelectionKey>();
			this.wakeup = new AtomicBoolean();// Ĭ��Ϊfalse
			try {
				this.selector = Selector.open();
			} catch (IOException e) {
			}
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

							}
							if (key.isValid() && key.isWritable()) {// ����д�����¼�

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
		void interRegisterEvent() {
			// ע���ŵ��Ľ����¼�
			SelectionKey key = channels.poll();
			while (key != null) {
				try {
					if (key.isValid() && key.isAcceptable()) {
						ServerSocketChannel servSockChannel = (ServerSocketChannel) key
								.channel();
						SocketChannel channel = servSockChannel.accept();
						channel.register(selector, SelectionKey.OP_READ);
					} else {
						key.cancel();
					}
				} catch (IOException e) {
				}
				key = channels.poll();
			}
			// ע���ŵ��Ķ������¼�
			key = readkeys.poll();
			while (key != null) {
				try {
					if (!key.isValid()) {
						key.cancel();
						key.channel().close();
					}
					SocketChannel channel = (SocketChannel) key.channel();
					channel.register(selector, key.interestOps()
							| SelectionKey.OP_READ);
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
					SocketChannel channel = (SocketChannel) key.channel();
					channel.register(selector, key.interestOps()
							| SelectionKey.OP_WRITE);
				} catch (IOException e) {
				}
				key = writekeys.poll();
			}

			// ���û��ѱ�ʶ
			wakeup.set(false);
		}

		/**
		 * ע���ŵ��Ľ����¼�
		 * 
		 * @param key
		 */
		void registerChannel(SelectionKey key) {
			channels.offer(key);
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
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public boolean start() {
		running = true;
		return false;
	}

	public void stop() {
		running = false;
	}
}
