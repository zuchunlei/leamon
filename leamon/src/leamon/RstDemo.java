package leamon;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 测试TCP/IP中的RST标识位
 */
public class RstDemo {
	// 定长编码 ucs-2 16bit->char

	public static void main(String[] args) {

		Socket socket = null;
		try {
			socket = new Socket("192.168.40.6", 12345);
			InputStream in = socket.getInputStream();
			byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = in.read(buffer)) != -1) {
				String s = String.valueOf(buffer);
				System.out.println(s);
			}
			System.out.println(length);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
