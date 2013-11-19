package nio.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 测试Selector的注册通道的线程安全性。 该实现会导致无数的线程被创建。
 * 因为selector.select()操作会立即返回，当accept事件没有被消费之前。
 */
public class SelectorTester {

	public static void main(String[] args) throws Exception {
		new SelectorTester().go();
	}

	public void go() throws Exception {
		Selector selector = Selector.open();

		ServerSocketChannel servChannel = ServerSocketChannel.open();
		servChannel.socket().bind(new InetSocketAddress(12345));
		servChannel.configureBlocking(false);
		servChannel.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			int selected = selector.select();
			if (selected == 0) {
				continue;
			}
			Set<SelectionKey> set = selector.selectedKeys();
			Iterator<SelectionKey> it = set.iterator();

			while (it.hasNext()) {
				SelectionKey key = it.next();
				// 必须手动删除该选择键，否则在下次选择中，该选择键依然存在。
				it.remove();
				new Thread(new Handler(key)).start();
			}
		}
	}

	class Handler implements Runnable {
		private SelectionKey key;

		public Handler(SelectionKey key) {
			this.key = key;
		}

		public void run() {
			try {
				Selector selector = key.selector();
				if (key.isAcceptable()) {
					ServerSocketChannel servChannel = (ServerSocketChannel) key
							.channel();
					// accept 消费了“接受就绪”事件
					SocketChannel channel = servChannel.accept();
					channel.configureBlocking(false);
					// 该channel第一次注册，如果该Selector处于select状态，则注册不成功。
					// 因为竞争Selector实例的中publicKeys，导致channel注册阻塞。
					channel.register(selector, SelectionKey.OP_READ);
				} else if (key.isReadable()) {
					SocketChannel channel = (SocketChannel) key.channel();
					// read消费了“读继续”事件
					channel.read(ByteBuffer.allocate(1024));
					channel.register(selector, SelectionKey.OP_WRITE);
				}
			} catch (Exception e) {
				// ignore
			}
		}
	}
}
