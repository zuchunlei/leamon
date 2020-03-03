package network;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ReadEOFTester {

    public static void main(String[] args) throws Exception {
        new ReadEOFTester().service();
    }

    public void service() throws Exception {
        ServerSocket servSock = new ServerSocket(7788, 100);
        Socket sock = servSock.accept();
        System.out.println(sock.getKeepAlive());
        sock.setKeepAlive(true);
        InputStream is = sock.getInputStream();

        int data = is.read();
        System.out.println(data);

        is.close();
        sock.close();
        servSock.close();
    }
}
