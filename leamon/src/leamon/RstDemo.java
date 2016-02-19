package leamon;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

/**
 * ����TCP/IP�е�RST��ʶλ
 */
public class RstDemo {
    // �������� ucs-2 16bit->char

    public static void main(String[] args) {

        Socket socket = null;
        try {
            socket = new Socket("192.168.124.153", 12345);
            InputStream in = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = in.read(buffer)) != -1) {
                String s = new String(buffer, 0, length, Charset.forName("utf-8"));
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
