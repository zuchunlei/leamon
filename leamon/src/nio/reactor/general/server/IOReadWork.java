package nio.reactor.general.server;


/**
 * 具体IO读事件处理的任务对象，由外部线程池调用。
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
		// 读取数据，取消兴趣读，业务处理，注册兴趣写。
		// SocketChannel channel = (SocketChannel) key.channel();
		// try {
		// if (channel.read(buffer) == -1) {
		// key.cancel();
		// key.channel().close();
		// return;
		// }
		// // unRegisterRead();
		// // poller.putData(key, buffer);
		// key.attach(buffer);// 将缓冲区以SelectionKey附件的方式挂载
		// poller.registerWrite(key);
		// } catch (IOException e) {
		// }
		session.readData();
	}
}
