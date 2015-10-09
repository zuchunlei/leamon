package network;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) {
        try {
            new NioServer().server();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void server() throws Exception {
        ServerSocketChannel servChannel = ServerSocketChannel.open();
        ServerSocket servSock = servChannel.socket();
        servSock.bind(new InetSocketAddress("127.0.0.1", 12345));
        servChannel.configureBlocking(false);// 设置监听套接字通道为非阻塞

        Selector selector = Selector.open();// 开始默认复用器
        servChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int number = selector.select(1000 * 10);
            if (number == 0) {
                continue;
            }
            Set<SelectionKey> set = selector.selectedKeys();
            Iterator<SelectionKey> iter = set.iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();// 必须手动删除当前选择的key，要不该key永久存在于selector的选择就绪键集中。
                handle(key);// 处理选择键
            }
        }
    }

    protected void handle(SelectionKey key) throws Exception {
        if (key.isAcceptable()) {
            ServerSocketChannel servChannel = (ServerSocketChannel) key.channel();
            SocketChannel channel = servChannel.accept();
            channel.configureBlocking(false);// 必须将Channel配置为非阻塞模式下，才可用注册到Selector中。
            channel.register(key.selector(), SelectionKey.OP_READ);// accept()操作返回的SocketChannel对象，不许显式的通过register()操作注册该信道。
        } else if (key.isReadable()) {
            // 第一步取消该信道的兴趣读，否者在多线程环境下会造成该读就绪事件被多个线程消费的现象。
            key.interestOps(key.interestOps() ^ SelectionKey.OP_READ);

            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1000);
            int len = channel.read(buffer);
            if (len == -1) {
                channel.close();
            } else {
                // channel.register(key.selector(), SelectionKey.OP_READ
                // | SelectionKey.OP_WRITE);
                // 如果信道Channel已经与选择器Selector具有了注册关系，则该Channel的兴趣事件改变将由SelectionKey对象处理。
                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);// 信道关注兴趣写
            }
        } else if (key.isWritable()) {
            // 取消信道的兴趣写关注
            key.interestOps(key.interestOps() ^ SelectionKey.OP_WRITE);

            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1000);
            buffer.put(new Date().toString().getBytes());
            buffer.flip();
            channel.write(buffer);
            // channel.register(key.selector(), SelectionKey.OP_READ);
            // 使用SelectionKey来改变Channel的兴趣关注的改变
            key.interestOps(key.interestOps() | SelectionKey.OP_READ);// 信道关注兴趣读
        }
    }
}
