package nio.reactor.echo.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicBoolean;

import nio.reactor.echo.server.Server.Poller;

/**
 * IOSession��װ��һ���������ӵĶ�д�����������ڲ�ά���˶�д��������Ҫ��״̬��Ϣ��
 */
public class IOSession {

	private SelectionKey key;// key���������io�����¼�
	private Poller poller;//

	private volatile AtomicBoolean inReading;// �Ƿ��ڶ�״̬

	public IOSession(SelectionKey key, Poller poller) {
		this.key = key;
		this.poller = poller;
		this.inReading = new AtomicBoolean();// Ĭ��Ϊfalse
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
	 * ���SelectionKey���������IOSession����
	 */
	public static IOSession getSession(SelectionKey key) {
		return (IOSession) key.attachment();
	}
}
