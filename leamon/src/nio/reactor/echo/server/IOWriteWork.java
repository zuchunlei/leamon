package nio.reactor.echo.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import nio.reactor.echo.server.Server.Poller;

/**
 * 具体IO事件处理的任务对象，由外部线程池调用。
 */
public class IOWriteWork implements Runnable {
	private Server.Poller poller;
	private SelectionKey key;

	public IOWriteWork(Poller poller, SelectionKey key) {
		this.poller = poller;
		this.key = key;
	}

	// 取消兴趣写，写数据到信道，注册兴趣读。
	public void run() {
		SocketChannel channel = (SocketChannel) key.channel();
		IOReadWork work = (IOReadWork) key.attachment();
		try {
			unRegisterWrite();
			ByteBuffer buffer = work.getBuffer();
			buffer.flip();
			channel.write(buffer);
			poller.registerRead(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 取消兴趣写
	 */
	void unRegisterWrite() {
		key.interestOps(key.interestOps() ^ SelectionKey.OP_WRITE);
	}
}
