package nio.reactor.echo.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * ����IO���¼����������������ⲿ�̳߳ص��á�
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

	public void run() {
		// ��ȡ���ݣ�ȡ����Ȥ����ҵ����ע����Ȥд��
		SocketChannel channel = (SocketChannel) key.channel();
		try {
			if (channel.read(buffer) == -1) {
				key.cancel();
				key.channel().close();
				return ;
			}
			// unRegisterRead();
			poller.putData(key, buffer);
			poller.registerWrite(key);
		} catch (IOException e) {
		}
	}
}
