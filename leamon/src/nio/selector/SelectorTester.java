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
 * ����Selector��ע��ͨ�����̰߳�ȫ�ԡ� ��ʵ�ֻᵼ���������̱߳�������
 * ��Ϊselector.select()�������������أ���accept�¼�û�б�����֮ǰ��
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
				// �����ֶ�ɾ����ѡ������������´�ѡ���У���ѡ�����Ȼ���ڡ�
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
					// accept �����ˡ����ܾ������¼�
					SocketChannel channel = servChannel.accept();
					channel.configureBlocking(false);
					// ��channel��һ��ע�ᣬ�����Selector����select״̬����ע�᲻�ɹ���
					// ��Ϊ����Selectorʵ������publicKeys������channelע��������
					channel.register(selector, SelectionKey.OP_READ);
				} else if (key.isReadable()) {
					SocketChannel channel = (SocketChannel) key.channel();
					// read�����ˡ����������¼�
					channel.read(ByteBuffer.allocate(1024));
					channel.register(selector, SelectionKey.OP_WRITE);
				}
			} catch (Exception e) {
				// ignore
			}
		}
	}
}
