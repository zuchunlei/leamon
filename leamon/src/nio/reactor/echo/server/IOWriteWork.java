package nio.reactor.echo.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import nio.reactor.echo.server.Server.Poller;

/**
 * ����IO�¼����������������ⲿ�̳߳ص��á�
 */
public class IOWriteWork implements Runnable {
	private Server.Poller poller;
	private SelectionKey key;

	public IOWriteWork(Poller poller, SelectionKey key) {
		this.poller = poller;
		this.key = key;
	}

	// ȡ����Ȥд��д���ݵ��ŵ���ע����Ȥ����
	public void run() {
		SocketChannel channel = (SocketChannel) key.channel();
		try {
			// unRegisterWrite();
			// ByteBuffer buffer = poller.getData(key);
			ByteBuffer buffer = (ByteBuffer) key.attachment();// �Ը����ķ�ʽ��ȡ��д������
			buffer.flip();
			channel.write(buffer);
			poller.registerRead(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
