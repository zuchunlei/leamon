package network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class NioTester {

    public static void main(String[] args) {
        String name = "我是中国人！";
        byte[] buffer = name.getBytes();
        System.out.println(buffer.length);

        try {
            new NioTester().server();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void server() throws Exception {
        ExecutorService service = new ScheduledThreadPoolExecutor(10);
        ServerSocketChannel servChannel = ServerSocketChannel.open();
        ServerSocket servSock = servChannel.socket();
        servSock.bind(new InetSocketAddress("127.0.0.1", 12345));
        servChannel.configureBlocking(false);// 设置监听套接字通道为非阻塞

        final Selector selector = Selector.open();// 开始默认复用器
        servChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int readyChannels = selector.select();
            if (readyChannels == 0) {
                continue;
            }
            Set<SelectionKey> readySet = selector.selectedKeys();
            Iterator<SelectionKey> iter = readySet.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel schannel = (ServerSocketChannel) key.channel();
                    SocketChannel channel = schannel.accept();
                    channel.configureBlocking(false);
                    channel.register(key.selector(), SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    service.execute(new Task(key));
                }
                iter.remove();
            }
        }
    }

}

class Task implements Runnable {
    private SocketChannel channel;
    private SelectionKey key;

    public Task(SelectionKey key) {
        this.key = key;
    }

    public void run() {
        channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            try {
                int length = channel.read(buffer);
                // 当前channel如果没有数据准备就绪，则read调用直接返回0，不阻塞。
                System.out.println(length);
                Thread.sleep(1000 * 30);
                // key.selector().wakeup();
                buffer.clear();
                for (int i = 0; i < 200; i++) {
                    buffer.put(String.valueOf(i).getBytes());
                }
                buffer.flip();
                key.interestOps(SelectionKey.OP_WRITE);
                length = channel.write(buffer);
                System.out.println(length);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}