package socket.option;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * SoLinger�Ĳ��ԣ���֤�Ƿ�Send-Q�д�������ʱ��close()��������������ʱ��
 * 
 * ������֤����socket����������solingerѡ��ʱ������socket������Send-Q����Ȼ����������Ϊ���͵��Զ˵�Recv-Q��ʱ��
 * ����socket.close()��������ʱ������
 */
public class SoLingerTester {

    public static void main(String[] args) throws IOException {
        Socket sock = new Socket();
        // ����socketѡ��soliger��Ĭ��Ϊfalse
        sock.setSoLinger(true, 100);

        sock.connect(new InetSocketAddress("localhost", 12345));

        OutputStream out = sock.getOutputStream();
        // ���ʹ������ֽ�����
        for (int i = 0; i < 10000; i++) {
            out.write("zu_chunlei".getBytes());
        }
        // �رո�socket���󣬲������Send-Q����δ������ȫ������ʱ���Ƿ������ȴ���
        // ȷʵ������ָ��ʱ���ر�
        long time = System.currentTimeMillis();
        sock.close();
        System.out.println(System.currentTimeMillis() - time);
    }
}
