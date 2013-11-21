package nio.socketchannel;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * NIO版的链接客户端
 */
public class Connector {

	public static void main(String[] args) throws Exception {
		Selector selector = Selector.open();

		// 调用无参数的open(),将返回的信道配置为非阻塞的
		SocketChannel sockChannel = SocketChannel.open();
		sockChannel.configureBlocking(false);

		// 通过给定的host/port尝试去连接远程主机，
		sockChannel.connect(new InetSocketAddress("baidu.com", 80));
		// 如果该连接处于pending状态，重复去连接
		while (sockChannel.isConnectionPending()) {
			sockChannel.finishConnect();
		}

		// 返回的是SocketAdaptor
		Socket socket = sockChannel.socket();
		System.out.println(socket);

		sockChannel.register(selector, SelectionKey.OP_CONNECT);
	}
}
