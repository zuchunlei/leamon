package nio.reactor.general.server;


/**
 * ����IO���¼����������������ⲿ�̳߳ص��á�
 */
public class IOReadWork implements Runnable {

	// private Server.Poller poller;
	// private SelectionKey key;
	//
	// private ByteBuffer buffer;
	//
	// public IOReadWork(Server.Poller poller, SelectionKey key) {
	// this.poller = poller;
	// this.key = key;
	// this.buffer = ByteBuffer.allocate(1024);
	// }

	private IOSession session;

	public IOReadWork(IOSession session) {
		this.session = session;
	}

	public void run() {
		// ��ȡ���ݣ�ȡ����Ȥ����ҵ����ע����Ȥд��
		// SocketChannel channel = (SocketChannel) key.channel();
		// try {
		// if (channel.read(buffer) == -1) {
		// key.cancel();
		// key.channel().close();
		// return;
		// }
		// // unRegisterRead();
		// // poller.putData(key, buffer);
		// key.attach(buffer);// ����������SelectionKey�����ķ�ʽ����
		// poller.registerWrite(key);
		// } catch (IOException e) {
		// }
		session.readData();
	}
}
