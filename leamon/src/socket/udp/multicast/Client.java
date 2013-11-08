package socket.udp.multicast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Client {

	public static void main(String[] args) throws Exception {
		MulticastSocket socket = new MulticastSocket(54321);
		socket.joinGroup(InetAddress.getByName("228.5.6.7"));
		
		byte[] buffer = new byte[1024];
		while (true) {
			DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
			socket.receive(pack);
			System.out.println(pack.getPort());
			System.out.println(pack.getAddress().getHostAddress());
		}
	}

}
