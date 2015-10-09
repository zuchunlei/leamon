package socket.udp.multicast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class MulticastSocketTester {

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        SocketAddress address = new InetSocketAddress("228.5.6.7", 54321);

        DatagramPacket pack = new DatagramPacket("123".getBytes(), "123".getBytes().length, address);

        socket.send(pack);
    }

}
