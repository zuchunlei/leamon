package nio.reactor.general.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicBoolean;

import nio.reactor.general.data.DataPacket;
import nio.reactor.general.server.Server.Poller;

/**
 * IOSession封装了一个网络连接的读写操作，并且内部维护了读写过程中需要的状态信息。
 */
public class IOSession {
	private static final int DEFAULT_SIZE = 1024;

	private SelectionKey key;// key对象包含了io就绪事件
	private volatile AtomicBoolean inReading;// 是否处于读状态
	private ByteBuffer buffer;// 数据缓冲区
	private Poller poller;// 轮询器

	private SocketChannel channel;// IO信道
	private DataPacket packet;

	public IOSession(SelectionKey key, Poller poller) {
		this.key = key;
		this.channel = (SocketChannel) key.channel();
		this.poller = poller;
		this.inReading = new AtomicBoolean();// 默认为false
		this.buffer = ByteBuffer.allocate(DEFAULT_SIZE);
	}

	public boolean isReading() {
		return inReading.get();
	}

	/**
	 * 读取数据，OP_READ就绪事件的处理方法，内核态--->用户态
	 */
	public void readData() {
		if (inReading.compareAndSet(false, true)) {
			// SocketChannel channel = (SocketChannel) key.channel();
			try {
				channel.read(buffer);

				// 数据读取完成，构造DataPacket进行应用层处理
				packet = new DataPacket(channel.getRemoteAddress(), buffer);

				// 网络读完成（数据已从内核态到用户态），进行应用层处理
				onReadComplete(packet);

			} catch (IOException e) {
				// 在channel读写时发生异常
				this.key.cancel();
				try {
					channel.close();
				} catch (IOException ioe) {
					// ignore
				}
				return;
			}
			inReading.compareAndSet(true, false);
		}
		// 注册写就绪关注
		this.poller.registerWrite(key);
	}

	/**
	 * 写入数据，OP_WRITE就绪事件的处理方法，用户态--->内核态
	 */
	public void writeData() {
		// 应用层数据写处理
		onWriteReady(packet);

		// 进行网络写操作
		try {
			channel.write(packet.getContent());
		} catch (IOException e) {
			// 在channel读写时发生异常
			this.key.cancel();
			try {
				channel.close();
			} catch (IOException ioe) {
				// ignore
			}
			return;
		}

		// 注册读就绪关注
		this.poller.registerRead(key);
	}

	/**
	 * 网络读完成，进行应用层处理
	 * 
	 * @param packet
	 */
	public void onReadComplete(DataPacket packet) {

	}

	/**
	 * 网络写就绪，进行应用层处理
	 * 
	 * @param packet
	 */
	public void onWriteReady(DataPacket packet) {

	}

	/**
	 * 获得SelectionKey对象关联的IOSession对象
	 */
	public static IOSession getSession(SelectionKey key) {
		return (IOSession) key.attachment();
	}
}
