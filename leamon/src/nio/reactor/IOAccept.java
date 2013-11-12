package nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class IOAccept {

	private Selector selector;

	public IOAccept() {
		try {
			selector = Selector.open();
			ServerSocketChannel channel = ServerSocketChannel.open();
			channel.socket().bind(new InetSocketAddress(7788));
			channel.configureBlocking(false);
			channel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new IOAccept().accept();
	}

	public void accept() {
		while (true) {
			try {
				int selected = selector.select();
				if (selected != 0) {
					continue;
				}
				handle(selector.selectedKeys());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void handle(Set<SelectionKey> set) throws Exception {
		Iterator<SelectionKey> iter = set.iterator();
		while (iter.hasNext()) {
			SelectionKey key = iter.next();
			iter.remove();
			if (key.isAcceptable()) {
				ServerSocketChannel servChannel = (ServerSocketChannel) key
						.channel();
				SocketChannel channel = servChannel.accept();
				channel.configureBlocking(false);
				channel.register(key.selector(), SelectionKey.OP_READ
						| SelectionKey.OP_WRITE);
			}

			if (key.isReadable()) {
				SocketChannel channel = (SocketChannel) key.channel();
				ByteBuffer buffer = ByteBuffer.allocate(100);
				buffer.clear();
				channel.read(buffer);
				buffer.flip();
				channel.write(buffer);
			}
		}

	}
}
