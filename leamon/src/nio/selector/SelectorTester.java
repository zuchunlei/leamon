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
 * 因为selector.select()操作会立即返回，当accept时间没有被消费之前。
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
				new Thread(new Handler(key)).start();
				it.remove();
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
					SocketChannel channel = servChannel.accept();
					channel.configureBlocking(false);
					channel.register(selector, SelectionKey.OP_READ);
				} else if (key.isReadable()) {
					SocketChannel channel = (SocketChannel) key.channel();
					channel.read(ByteBuffer.allocate(1024));
					channel.register(selector, SelectionKey.OP_WRITE);
				}
			} catch (Exception e) {
				// ignore
			}
		}
	}
}
