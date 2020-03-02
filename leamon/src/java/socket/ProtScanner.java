package socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * ��ѯ����TCP�˿��Ƿ��ڼ���״̬
 */
public class ProtScanner {

    public static void main(String[] args) {
        String host = "localhost";
        Socket sock = null;
        for (int port = 1; port < 65535; port++) {
            try {
                sock = new Socket();
                sock.connect(new InetSocketAddress(host, port), 1000);
                System.out.println("port :" + port + " is openning!");
            } catch (UnknownHostException e) {
                break;
            } catch (IOException e) {
                // ignore
            } finally {
                if (sock != null) {
                    try {
                        sock.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

}
