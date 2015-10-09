package socket.option;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * SoLinger的测试，验证是否Send-Q中存在数据时，close()操作进行阻塞延时。
 * 
 * 经过验证，当socket对象设置了solinger选项时，当该socket连接中Send-Q中依然存在着数据为发送到对端的Recv-Q中时，
 * 调用socket.close()方法会延时阻塞。
 */
public class SoLingerTester {

    public static void main(String[] args) throws IOException {
        Socket sock = new Socket();
        // 设置socket选项soliger，默认为false
        sock.setSoLinger(true, 100);

        sock.connect(new InetSocketAddress("localhost", 12345));

        OutputStream out = sock.getOutputStream();
        // 发送大量的字节数据
        for (int i = 0; i < 10000; i++) {
            out.write("zu_chunlei".getBytes());
        }
        // 关闭该socket对象，测试如果Send-Q存在未发送完全的数据时，是否阻塞等待。
        // 确实阻塞到指定时间后关闭
        long time = System.currentTimeMillis();
        sock.close();
        System.out.println(System.currentTimeMillis() - time);
    }
}
