package network;

import java.io.OutputStream;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", 7788);
		OutputStream out = socket.getOutputStream();
		out.write(257);
		out.close();
	}

}
