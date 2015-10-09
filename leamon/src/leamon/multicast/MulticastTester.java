package leamon.multicast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
// 多播套接字
// 存活兄弟节点列表

public class MulticastTester {

    public static void main(String[] args) throws Exception {
        InetAddress group = InetAddress.getByName("228.5.6.7");
        MulticastSocket socket = new MulticastSocket(6789);
        socket.joinGroup(group);
        new Sender(socket).start();
        new Receiver(socket).start();
    }
}

class Receiver extends Thread {
    private MulticastSocket sock;// 多播套接字

    public Receiver(MulticastSocket sock) {
        this.sock = sock;
    }

    @Override
    public void run() {
        while (true) {
            byte[] buf = new byte[1024];
            DatagramPacket pack = new DatagramPacket(buf, buf.length);
            try {
                sock.receive(pack);
                ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(pack.getData()));
                HeartBeat heartBeat = (HeartBeat) in.readObject();
                System.out.println(heartBeat);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Sender extends Thread {
    private static ByteArrayOutputStream baos;
    private static ObjectOutputStream out;

    static {
        baos = new ByteArrayOutputStream();
        try {
            out = new ObjectOutputStream(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MulticastSocket sock;// 多播套接字

    public Sender(MulticastSocket sock) {
        this.sock = sock;
    }

    @Override
    public void run() {
        while (true) {
            HeartBeat heartBeat = new HeartBeat(System.currentTimeMillis());
            try {
                out.writeObject(heartBeat);
                byte[] buf = baos.toByteArray();
                DatagramPacket pack = new DatagramPacket(buf, buf.length, InetAddress.getByName("228.5.6.7"),
                        sock.getLocalPort());
                sock.send(pack);
                Thread.sleep(1000 * 10);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class HeartBeat implements Serializable {
    private static final long serialVersionUID = -1642890147631211529L;

    private long time;

    public HeartBeat(long time) {
        this.time = time;
    }

    public long interval() {
        return System.currentTimeMillis() - this.time;
    }
}