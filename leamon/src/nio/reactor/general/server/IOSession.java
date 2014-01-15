package nio.reactor.echo.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * IOSession��װ��һ���������ӵĶ�д�����������ڲ�ά���˶�д��������Ҫ��״̬��Ϣ��
 */
public class IOSession {

	private SelectionKey key;// key���������io�����¼�
	private volatile AtomicBoolean inReading;// �Ƿ��ڶ�״̬

	public IOSession(SelectionKey key) {
		this.key = key;
		this.inReading = new AtomicBoolean();// Ĭ��Ϊfalse
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
	 * ���SelectionKey���������IOSession����
	 */
	public static IOSession getSession(SelectionKey key) {
		return (IOSession) key.attachment();
	}
}
