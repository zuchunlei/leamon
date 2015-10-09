package network;

import java.net.Socket;

public class SockOptionTester {

    public static void main(String[] args) throws Exception {
        Socket sock = new Socket("192.168.40.6", 8000);
        byte[] buffer = "×æ´ºÀ×".getBytes();
        System.out.println(buffer);
        System.out.println(sock);
    }
}
