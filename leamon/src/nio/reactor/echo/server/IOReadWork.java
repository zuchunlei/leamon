package nio.reactor.echo.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * 具体IO读事件处理的任务对象，由外部线程池调用。
 */
public class IOReadWork implements Runnable {

	private Server.Poller poller;
	private SelectionKey key;

	private ByteBuffer buffer;

	public IOReadWork(Server.Poller poller, SelectionKey key) {
		this.poller = poller;
		this.key = key;
		this.buffer = ByteBuffer.allocate(1024);
	}

	public ByteBuffer getBuffer() {
		return buffer;
	}

	public void run() {
		// 读取数据，取消兴趣读，业务处理，注册兴趣写。
		SocketChannel channel = (SocketChannel) key.channel();
		try {
			int length = channel.read(buffer);
			unRegisterRead();
			System.out.println(length);
			poller.registerWrite(key);
		} catch (IOException e) {
		}
	}

	/**
	 * 取消兴趣读
	 */
	void unRegisterRead() {
		key.interestOps(key.interestOps() ^ SelectionKey.OP_READ);
	}
}
