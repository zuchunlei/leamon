package network;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
	public static void main(String[] args) {
		try {
			new NioServer().server();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void server() throws Exception {
		ServerSocketChannel servChannel = ServerSocketChannel.open();
		ServerSocket servSock = servChannel.socket();
		servSock.bind(new InetSocketAddress("127.0.0.1", 12345));
		servChannel.configureBlocking(false);// ���ü����׽���ͨ��Ϊ������

		Selector selector = Selector.open();// ��ʼĬ�ϸ�����
		servChannel.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			int number = selector.select(1000 * 10);
			if (number == 0) {
				continue;
			}
			Set<SelectionKey> set = selector.selectedKeys();
			Iterator<SelectionKey> iter = set.iterator();

			while (iter.hasNext()) {
				SelectionKey key = iter.next();
				iter.remove();// �����ֶ�ɾ����ǰѡ���key��Ҫ����key���ô�����selector��ѡ����������С�
				handle(key);// ����ѡ���
			}
		}
	}

	protected void handle(SelectionKey key) throws Exception {
		if (key.isAcceptable()) {
			ServerSocketChannel servChannel = (ServerSocketChannel) key
					.channel();
			SocketChannel channel = servChannel.accept();
			channel.configureBlocking(false);
			channel.register(key.selector(), SelectionKey.OP_READ);
		} else if (key.isReadable()) {
			SocketChannel channel = (SocketChannel) key.channel();
			ByteBuffer buffer = ByteBuffer.allocate(1000);
			int len = channel.read(buffer);
			if (len == -1) {
				channel.close();
			} else {
				channel.register(key.selector(), SelectionKey.OP_READ
						| SelectionKey.OP_WRITE);
			}
		} else if (key.isWritable()) {
			SocketChannel channel = (SocketChannel) key.channel();
			Date date = new Date();
			ByteBuffer buffer = ByteBuffer.allocate(1000);
			buffer.put(date.toString().getBytes());
			buffer.flip();
			channel.write(buffer);
			channel.register(key.selector(), SelectionKey.OP_READ);
		}
	}
}
