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
 * NIO Reactor模式的Echo Server实现
 */
public class Server {

	private String host;
	private int port;

	private AtomicInteger incr;// 计数器
	private volatile boolean running;

	private Poller[] pollers;// 轮询对象，处理SocketChannel的I/O事件
	private AtomicBoolean accept;// 连接请求被接收的标识

	public Server(String host, int port) {
		this.host = host;
		this.port = port;
		this.incr = new AtomicInteger(Integer.MIN_VALUE);
		this.accept = new AtomicBoolean();
		this.pollers = new Poller[Runtime.getRuntime().availableProcessors() + 1];// Poller的个数为当前可以CPU+1
		for (int i = 0; i < pollers.length; i++) {
			pollers[i] = new Poller();
		}
	}

	/**
	 * 接收网络连接的接收器
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
							// System.out.println("连接来了");
							it.remove();// 必须显示的删除
							if (!key.isValid()) {
								key.cancel();
								key.channel().close();
								break;
							}
							// 策略选择一个Poller将该key注册到Poller上。
							int offset = Math.abs(incr.decrementAndGet()
									% pollers.length);
							pollers[offset].registerChannel(key);

							while (!accept.compareAndSet(true, false))
								;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @author a
	 * 
	 */
	class Poller implements Runnable {
		private ConcurrentLinkedQueue<SelectionKey> channels;// 等待注册到Poller中的SelectionKey
		private ConcurrentLinkedQueue<SelectionKey> readkeys;// 读就绪选择键集合
		private ConcurrentLinkedQueue<SelectionKey> writekeys;// 写就绪选择键集合

		// 去掉读写数据池，原来基于SelectionKey作为Key来保存读写的数据Map，改为使用SelectionKey附件的方式传递。
		// private ConcurrentHashMap<SelectionKey, ByteBuffer> datapools;//
		// 读写通道的数据池

		private AtomicBoolean wakeup;// 唤醒标识，原子类，排他。
		private Selector selector;

		private Executor executor;// 具体IO执行的线程池

		public Poller() {
			this.channels = new ConcurrentLinkedQueue<SelectionKey>();
			this.readkeys = new ConcurrentLinkedQueue<SelectionKey>();
			this.writekeys = new ConcurrentLinkedQueue<SelectionKey>();
			// this.datapools = new ConcurrentHashMap<SelectionKey,
			// ByteBuffer>();

			this.wakeup = new AtomicBoolean();// 默认为false

			try {
				this.selector = Selector.open();
			} catch (IOException e) {
			}
			this.executor = Executors.newCachedThreadPool();
		}

		public void run() {
			while (running) {
				try {
					int selected = selector.select(1000);// 选择等待1s
					interRegisterEvent();// 统一事件注册方法
					if (selected > 0) {
						Iterator<SelectionKey> it = selector.selectedKeys()
								.iterator();
						while (it.hasNext()) {
							SelectionKey key = it.next();
							it.remove();
							if (key.isValid() && key.isReadable()) {// 处理读就绪事件
								// 取消兴趣读，读数据，业务处理，注册兴趣写。
								unRegisterRead(key);// 取消所有兴趣关注点
								IOReadWork work = new IOReadWork(this, key);
								executor.execute(work);
							}
							if (key.isValid() && key.isWritable()) {// 处理写就绪事件
								// 取消兴趣写
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
		 * 内部统一注册信道的事件
		 */
		void interRegisterEvent() {
			// 注册信道的接收事件
			SelectionKey key = channels.poll();
			while (key != null) {
				try {
					if (key.isValid() && key.isAcceptable()) {
						ServerSocketChannel servSockChannel = (ServerSocketChannel) key
								.channel();
						SocketChannel channel = servSockChannel.accept();
						while (!accept.compareAndSet(false, true))
							;

						channel.configureBlocking(false);// 必须将信道配置为非阻塞模式
						channel.register(selector, SelectionKey.OP_READ);
					} else {
						key.cancel();
					}
				} catch (IOException e) {
				}
				key = channels.poll();
			}
			// 注册信道的读就绪事件
			key = readkeys.poll();
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
			// 注册信道的写就绪事件
			key = writekeys.poll();
			while (key != null) {
				try {
					if (!key.isValid()) {
						key.cancel();
						key.channel().close();
					}
					// 以Channel的方式重新注册，产生的Key是否对附件有影响？
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

			// 重置唤醒标识
			wakeup.set(false);
		}

		/**
		 * 注册信道的接收事件
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
		 * 注册信道的读就绪事件
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
		 * 注册信道的写就绪事件
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
		 * 取消兴趣读
		 * 
		 * @param key
		 */
		void unRegisterRead(SelectionKey key) {
			key.interestOps(key.interestOps() ^ SelectionKey.OP_READ);
		}

		/**
		 * 取消兴趣写
		 * 
		 * @param key
		 */
		void unRegisterWrite(SelectionKey key) {
			key.interestOps(key.interestOps() ^ SelectionKey.OP_WRITE);
		}

		/**
		 * 读写数据的缓冲区
		 * 
		 * @param key
		 * @param buffer
		 */
		// void putData(SelectionKey key, ByteBuffer buffer) {
		// datapools.put(key, buffer);
		// }

		/**
		 * 通过Key来获取数据
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
		// 启动Poller
		for (int i = 0; i < pollers.length; i++) {
			String name = "Poller Thread -" + i;
			new Thread(pollers[i], name).start();
		}
		// 启动Acceptor
		new Thread(new Acceptor(), "Acceptor Thread").start();

		return running;
	}

	public void stop() {
		running = false;
	}
}
