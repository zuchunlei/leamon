package nio.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 测试Selector的注册通道的线程安全性。 该实现会导致无数的线程被创建。
 * 因为selector.select()操作会立即返回，当accept事件没有被消费之前。
 */
public class SelectorTester {

    // private ConcurrentHashMap<SelectionKey, ByteBuffer> dataMap;
    // private AtomicBoolean acceptLock;// 连接接受锁

    public SelectorTester() {
        // this.dataMap = new ConcurrentHashMap<SelectionKey, ByteBuffer>();
        // this.acceptLock = new AtomicBoolean();// 默认为false
    }

    public static void main(String[] args) throws Exception {
        new SelectorTester().go();
    }

    public void go() throws Exception {
        Selector selector = Selector.open();

        ServerSocketChannel servChannel = ServerSocketChannel.open();
        servChannel.socket().bind(new InetSocketAddress(12345));
        servChannel.configureBlocking(false);
        servChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int selected = selector.select(1000 * 10);// 沉睡10s
            if (selected == 0) {
                continue;
            }
            Set<SelectionKey> set = selector.selectedKeys();
            Iterator<SelectionKey> it = set.iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();
                // 必须手动删除该选择键，否则在下次选择中，该选择键依然存在。
                it.remove();

                if (key.channel().equals(servChannel)) {// 连接就绪到达
                    // 由主线程进行accept操作，并对返回的Channel进行注册
                    if (key.isAcceptable()) {// 连接就绪
                        // accept 消费了“接受就绪”事件
                        SocketChannel channel = servChannel.accept();
                        // 必须显式的将channel配置为非阻塞
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);// 注册读兴趣事件
                    }
                } else {
                    // 读写事件到达，交与新线程进行处理。
                    new Thread(new Handler(key)).start();
                }

                // new Thread(new Handler(key)).start();
                // // new Handler(key).run();
                // if (key.channel().equals(servChannel)) {//
                // 连接就绪，使用锁来保证该事件只被消费一次
                // while (!acceptLock.compareAndSet(true, false))
                // ;
                // }
            }
        }
    }

    class Handler implements Runnable {
        private SelectionKey key;

        public Handler(SelectionKey key) {
            this.key = key;
        }

        public void run() {
            try {
                Selector selector = key.selector();
                /*
                 * if (key.isAcceptable()) { ServerSocketChannel servChannel =
                 * (ServerSocketChannel) key .channel(); // accept 消费了“接受就绪”事件
                 * SocketChannel channel = servChannel.accept(); while
                 * (!acceptLock.compareAndSet(false, true)) ;
                 * channel.configureBlocking(false); //
                 * 该channel第一次注册，如果该Selector处于select状态，则注册不成功。 //
                 * 因为竞争Selector实例的中publicKeys，导致channel注册阻塞。
                 * channel.register(selector, SelectionKey.OP_READ);
                 * selector.wakeup(); } else
                 */
                if (key.isReadable()) {
                    key.interestOps(key.interestOps() & ~SelectionKey.OP_READ);// 取消关注兴趣读

                    SocketChannel channel = (SocketChannel) key.channel();
                    // read消费了“读继续”事件
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    if (channel.read(buffer) == -1) {// read EOF
                        key.cancel();
                        channel.close();
                        return;
                    }
                    key.attach(buffer);// 跨选择周期的选择键附件丢失
                    // dataMap.put(key, buffer);
                    // channel.register(selector, SelectionKey.OP_WRITE);
                    key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);// 注册兴趣写
                    selector.wakeup();
                } else if (key.isWritable()) {
                    key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);// 取消关注兴趣写

                    // 跨选择周期的选择键附件丢失
                    // （不是NIO的问题，而是由于fastdebug版本的JDK的原因）
                    // （后经验证，fastdebug版的JDK完全不是造成该现象发生的原因）
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    System.out.println(buffer != null);

                    SocketChannel channel = (SocketChannel) key.channel();
                    // ByteBuffer buffer = dataMap.get(key);
                    buffer.flip();
                    // write消费了“写就绪”事件
                    channel.write(buffer);
                    // 如果channel已经与selector存在了注册关系，则该channel所有的兴趣更改都应该由key来改变。
                    key.interestOps(key.interestOps() | SelectionKey.OP_READ);// 重新注册兴趣读
                    selector.wakeup();
                }
            } catch (Exception e) {
                // ignore
            }
        }
    }
}
