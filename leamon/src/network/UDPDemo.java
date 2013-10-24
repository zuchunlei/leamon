package network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

public class UDPDemo {
	public static final int DEFAULT_PORT = 54321;// UDPÄ¬ÈÏ¼àÌý¶Ë¿Ú

	private DatagramSocket sock;// UDP SOCK

	public UDPDemo(int port) {
		try {
			sock = new DatagramSocket(port);
		} catch (SocketException e) {
		}
	}

	public UDPDemo() {
		this(DEFAULT_PORT);
	}

	public void send(String data, String host, int port) throws Exception {
		SocketAddress to = new InetSocketAddress(host, port);
		DatagramPacket pack = new DatagramPacket(data.getBytes(),
				data.getBytes().length, to);
		this.sock.send(pack);
	}

	public String recv() throws Exception {
		return null;
	}

	public static void main(String[] args) {
		try {
			DatagramSocket socket1 = new DatagramSocket(1000);
			socket1.setReuseAddress(true);
			DatagramSocket socket2 = new DatagramSocket(1000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
}
