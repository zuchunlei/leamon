package nio.socketchannel;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * NIO版的链接客户端
 */
public class Connector {

	public static void main(String[] args) throws Exception {
		Selector selector = Selector.open();
		SocketChannel sockChannel = SocketChannel.open();
		sockChannel.configureBlocking(false);

		sockChannel.connect(new InetSocketAddress("192.168.40.6", 12345));
		sockChannel.register(selector, SelectionKey.OP_CONNECT);
	}
}
