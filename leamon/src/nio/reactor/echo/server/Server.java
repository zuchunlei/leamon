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
 * NIO Reactor模式的Echo Server实现
 */
public class Server {

	private String host;
	private int port;
	private int pcount;// Poller的个数

	private AtomicInteger incr;// 计数器

	private volatile boolean running;

	private Poller[] pollers;// 轮询对象，处理SocketChannel的I/O事件

	public Server(String host, int port) {
		this.host = host;
		this.port = port;
		this.pcount = Runtime.getRuntime().availableProcessors();
		this.incr = new AtomicInteger(Integer.MIN_VALUE);
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
				servSockChannel.register(selector, SelectionKey.OP_ACCEPT);
				while (running) {
					int selected = selector.select();
					if (selected > 0) {

						Iterator<SelectionKey> it = selector.selectedKeys()
								.iterator();
						while (it.hasNext()) {
							SelectionKey key = it.next();
							it.remove();// 必须显示的删除
							if (!key.isValid()) {
								key.cancel();
								key.channel().close();
								break;
							}
							// 策略选择一个Poller将该key注册到Poller上。
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
		private ConcurrentLinkedQueue<SelectionKey> channels;// 等待注册到Poller中的SelectionKey
		private ConcurrentLinkedQueue<SelectionKey> readkeys;// 读就绪选择键集合
		private ConcurrentLinkedQueue<SelectionKey> writekeys;// 写就绪选择键集合

		private AtomicBoolean wakeup;// 唤醒标识，原子类，排他。

		private Selector selector;

		public Poller() {
			this.channels = new ConcurrentLinkedQueue<SelectionKey>();
			this.readkeys = new ConcurrentLinkedQueue<SelectionKey>();
			this.writekeys = new ConcurrentLinkedQueue<SelectionKey>();
			this.wakeup = new AtomicBoolean();// 默认为false
			try {
				this.selector = Selector.open();
			} catch (IOException e) {
			}
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

							}
							if (key.isValid() && key.isWritable()) {// 处理写就绪事件

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
					SocketChannel channel = (SocketChannel) key.channel();
					channel.register(selector, key.interestOps()
							| SelectionKey.OP_READ);
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
					SocketChannel channel = (SocketChannel) key.channel();
					channel.register(selector, key.interestOps()
							| SelectionKey.OP_WRITE);
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
