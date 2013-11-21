package nio.socketchannel;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * NIO������ӿͻ���
 */
public class Connector {

	public static void main(String[] args) throws Exception {
		Selector selector = Selector.open();

		// �����޲�����open(),�����ص��ŵ�����Ϊ��������
		SocketChannel sockChannel = SocketChannel.open();
		sockChannel.configureBlocking(false);

		// ͨ��������host/port����ȥ����Զ��������
		sockChannel.connect(new InetSocketAddress("baidu.com", 80));
		// ��������Ӵ���pending״̬���ظ�ȥ����
		while (sockChannel.isConnectionPending()) {
			sockChannel.finishConnect();
		}

		// ���ص���SocketAdaptor
		Socket socket = sockChannel.socket();
		System.out.println(socket);

		sockChannel.register(selector, SelectionKey.OP_CONNECT);
	}
}
