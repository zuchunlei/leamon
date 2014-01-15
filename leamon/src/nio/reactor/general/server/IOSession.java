package nio.reactor.general.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicBoolean;

import nio.reactor.general.server.Server.Poller;

/**
 * IOSession��װ��һ���������ӵĶ�д�����������ڲ�ά���˶�д��������Ҫ��״̬��Ϣ��
 */
public class IOSession {
	private static final int DEFAULT_SIZE = 1024;

	private SelectionKey key;// key���������io�����¼�
	private volatile AtomicBoolean inReading;// �Ƿ��ڶ�״̬
	private ByteBuffer buffer;// ���ݻ�����

	private Poller poller;// ��ѯ��

	public IOSession(SelectionKey key, Poller poller) {
		this.key = key;
		this.poller = poller;
		this.inReading = new AtomicBoolean();// Ĭ��Ϊfalse
		this.buffer = ByteBuffer.allocate(DEFAULT_SIZE);
	}

	public boolean isReading() {
		return inReading.get();
	}

	/**
	 * ��ȡ���ݣ�OP_READ�����¼��Ĵ��������ں�̬--->�û�̬
	 */
	public void readData() {
		if (inReading.compareAndSet(false, true)) {
			SocketChannel channel = (SocketChannel) key.channel();
			try {
				channel.read(buffer);
			} catch (IOException e) {
				// ��channel��дʱ�����쳣
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
		// ע��д������ע
		this.poller.registerWrite(key);
	}

	/**
	 * д�����ݣ�OP_WRITE�����¼��Ĵ��������û�̬--->�ں�̬
	 */
	public void writeData() {
		
		
		// ע���������ע
		this.poller.registerRead(key);
	}

	/**
	 * ���SelectionKey���������IOSession����
	 */
	public static IOSession getSession(SelectionKey key) {
		return (IOSession) key.attachment();
	}
}
