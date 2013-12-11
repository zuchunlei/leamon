package nio.reactor.echo.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicBoolean;

import nio.reactor.echo.server.Server.Poller;

/**
 * IOSession封装了一个网络连接的读写操作，并且内部维护了读写过程中需要的状态信息。
 */
public class IOSession {

	private SelectionKey key;// key对象包含了io就绪事件
	private Poller poller;//

	private volatile AtomicBoolean inReading;// 是否处于读状态

	public IOSession(SelectionKey key, Poller poller) {
		this.key = key;
		this.poller = poller;
		this.inReading = new AtomicBoolean();// 默认为false
	}

	public AtomicBoolean getInReading() {
		return inReading;
	}

	public void readData() {
		poller.getExecutor().execute(new Runnable() {
			@Override
			public void run() {
				innerRead();
			}
		});
	}

	public void innerRead() {
		if (!inReading.compareAndSet(false, true)) {
			return;
		}
		SocketChannel channel = (SocketChannel) key.channel();
		try {
			channel.read(ByteBuffer.allocate(1024));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeData() {
		poller.getExecutor().execute(new Runnable() {
			@Override
			public void run() {
				innerWrite();
			}
		});
	}

	public void innerWrite() {

	}

	/**
	 * 获得SelectionKey对象关联的IOSession对象
	 */
	public static IOSession getSession(SelectionKey key) {
		return (IOSession) key.attachment();
	}
}
