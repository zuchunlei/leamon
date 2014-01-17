package nio.reactor.general.cli;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Echo Client
 */
public class EchoClient {

	public static void main(String[] args) {

		Runnable task = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Socket socket = new Socket();
						socket.connect(new InetSocketAddress("192.168.1.35",
								12345));

						String data = "zuchunlei!";
						byte[] content = data.getBytes();

						// Echo«Î«Û
						OutputStream os = socket.getOutputStream();
						DataOutputStream out = new DataOutputStream(os);
						out.writeInt(content.length);
						out.write(content);

						// EchoœÏ”¶
						InputStream in = socket.getInputStream();

						byte[] buffer = new byte[128];
						int length = in.read(buffer);
						System.out.println(new String(buffer, 0, length));
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		};

		for (int i = 0; i < 100; i++) {
			new Thread(task).start();
		}
	}
}
