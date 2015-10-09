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
 * ����Selector��ע��ͨ�����̰߳�ȫ�ԡ� ��ʵ�ֻᵼ���������̱߳�������
 * ��Ϊselector.select()�������������أ���accept�¼�û�б�����֮ǰ��
 */
public class SelectorTester {

    // private ConcurrentHashMap<SelectionKey, ByteBuffer> dataMap;
    // private AtomicBoolean acceptLock;// ���ӽ�����

    public SelectorTester() {
        // this.dataMap = new ConcurrentHashMap<SelectionKey, ByteBuffer>();
        // this.acceptLock = new AtomicBoolean();// Ĭ��Ϊfalse
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
            int selected = selector.select(1000 * 10);// ��˯10s
            if (selected == 0) {
                continue;
            }
            Set<SelectionKey> set = selector.selectedKeys();
            Iterator<SelectionKey> it = set.iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();
                // �����ֶ�ɾ����ѡ������������´�ѡ���У���ѡ�����Ȼ���ڡ�
                it.remove();

                if (key.channel().equals(servChannel)) {// ���Ӿ�������
                    // �����߳̽���accept���������Է��ص�Channel����ע��
                    if (key.isAcceptable()) {// ���Ӿ���
                        // accept �����ˡ����ܾ������¼�
                        SocketChannel channel = servChannel.accept();
                        // ������ʽ�Ľ�channel����Ϊ������
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);// ע�����Ȥ�¼�
                    }
                } else {
                    // ��д�¼�����������߳̽��д���
                    new Thread(new Handler(key)).start();
                }

                // new Thread(new Handler(key)).start();
                // // new Handler(key).run();
                // if (key.channel().equals(servChannel)) {//
                // ���Ӿ�����ʹ��������֤���¼�ֻ������һ��
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
                 * (ServerSocketChannel) key .channel(); // accept �����ˡ����ܾ������¼�
                 * SocketChannel channel = servChannel.accept(); while
                 * (!acceptLock.compareAndSet(false, true)) ;
                 * channel.configureBlocking(false); //
                 * ��channel��һ��ע�ᣬ�����Selector����select״̬����ע�᲻�ɹ��� //
                 * ��Ϊ����Selectorʵ������publicKeys������channelע��������
                 * channel.register(selector, SelectionKey.OP_READ);
                 * selector.wakeup(); } else
                 */
                if (key.isReadable()) {
                    key.interestOps(key.interestOps() & ~SelectionKey.OP_READ);// ȡ����ע��Ȥ��

                    SocketChannel channel = (SocketChannel) key.channel();
                    // read�����ˡ����������¼�
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    if (channel.read(buffer) == -1) {// read EOF
                        key.cancel();
                        channel.close();
                        return;
                    }
                    key.attach(buffer);// ��ѡ�����ڵ�ѡ���������ʧ
                    // dataMap.put(key, buffer);
                    // channel.register(selector, SelectionKey.OP_WRITE);
                    key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);// ע����Ȥд
                    selector.wakeup();
                } else if (key.isWritable()) {
                    key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);// ȡ����ע��Ȥд

                    // ��ѡ�����ڵ�ѡ���������ʧ
                    // ������NIO�����⣬��������fastdebug�汾��JDK��ԭ��
                    // ������֤��fastdebug���JDK��ȫ������ɸ���������ԭ��
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    System.out.println(buffer != null);

                    SocketChannel channel = (SocketChannel) key.channel();
                    // ByteBuffer buffer = dataMap.get(key);
                    buffer.flip();
                    // write�����ˡ�д�������¼�
                    channel.write(buffer);
                    // ���channel�Ѿ���selector������ע���ϵ�����channel���е���Ȥ���Ķ�Ӧ����key���ı䡣
                    key.interestOps(key.interestOps() | SelectionKey.OP_READ);// ����ע����Ȥ��
                    selector.wakeup();
                }
            } catch (Exception e) {
                // ignore
            }
        }
    }
}
