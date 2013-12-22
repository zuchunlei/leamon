package nio.reactor.echo.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * IOSession封装了一个网络连接的读写操作，并且内部维护了读写过程中需要的状态信息。
 */
public class IOSession {

	private SelectionKey key;// key对象包含了io就绪事件
	private volatile AtomicBoolean inReading;// 是否处于读状态

	public IOSession(SelectionKey key) {
		this.key = key;
		this.inReading = new AtomicBoolean();// 默认为false
	}

	public boolean isReading() {
		return inReading.get();
	}

	public void readData() {
		if (inReading.compareAndSet(false, true)) {
			SocketChannel channel = (SocketChannel) key.channel();
			try {
				channel.read(ByteBuffer.allocate(1024));
			} catch (IOException e) {
				e.printStackTrace();
			}
			inReading.compareAndSet(true, false);
		}
	}

	public void writeData() {
	}

	/**
	 * 获得SelectionKey对象关联的IOSession对象
	 */
	public static IOSession getSession(SelectionKey key) {
		return (IOSession) key.attachment();
	}
}
