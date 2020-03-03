package network;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastUDPDemo {

    private static final int port = 7777;

    public void send() throws Exception {
        MulticastSocket sock = new MulticastSocket(port);
        InetAddress address = InetAddress.getByName("230.0.0.1");
        // sock.joinGroup(address);
        byte[] buffer = "zuchunlei".getBytes();
        DatagramPacket data = new DatagramPacket(buffer, buffer.length, address, port);

        sock.send(data);
        sock.close();
    }

    public void recv() throws Exception {
        MulticastSocket sock = new MulticastSocket(port);
        InetAddress address = InetAddress.getByName("230.0.0.1");
        sock.joinGroup(address);
        byte[] buffer = new byte[1024];
        DatagramPacket data = new DatagramPacket(buffer, buffer.length);
        sock.receive(data);
        sock.close();
        System.out.println(data.getSocketAddress());
    }

    public static void main(String[] args) {
        final MulticastUDPDemo demo = new MulticastUDPDemo();
        try {
            new Thread("sender") {
                @Override
                public void run() {
                    while (true) {
                        try {
                            demo.send();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            new Thread("recver") {
                @Override
                public void run() {
                    while (true) {
                        try {
                            demo.recv();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
