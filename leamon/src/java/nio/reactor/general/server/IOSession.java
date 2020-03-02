package nio.reactor.general.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nio.reactor.general.server.Server.Poller;

/**
 * IOSession��װ��һ���������ӵĶ�д�����������ڲ�ά���˶�д��������Ҫ��״̬��Ϣ��
 */
public class IOSession {
    // private static final int DEFAULT_SIZE = 1024;

    private SelectionKey key;// key���������io�����¼�
    // private volatile AtomicBoolean inReading;// �Ƿ��ڶ�״̬
    // private ByteBuffer buffer;// ���ݻ�����
    private Poller poller;// ��ѯ��
    private SocketChannel channel;// IO�ŵ�
    // private DataPacket packet;

    // private IOFilterChain chain;// IO������

    // public IOSession(SelectionKey key, Poller poller) {
    // this.key = key;
    // this.channel = (SocketChannel) key.channel();
    // this.poller = poller;
    // this.inReading = new AtomicBoolean();// Ĭ��Ϊfalse
    // this.buffer = ByteBuffer.allocate(DEFAULT_SIZE);
    // }

    private final Map<String, Object> attributes = new ConcurrentHashMap<>();

    public IOSession(SocketChannel channel, Poller poller) {
        this.channel = channel;
        this.key = channel.keyFor(poller.getSelector());
        this.key.attach(this);
        this.poller = poller;
        // this.inReading = new AtomicBoolean();// Ĭ��Ϊfalse
        // this.buffer = ByteBuffer.allocate(DEFAULT_SIZE);
    }

    public boolean isReadable() {
        return this.key.isReadable();
    }

    public boolean isWritable() {
        return this.key.isWritable();
    }

    public void close() {
        if (key != null && key.isValid()) {
            key.cancel();
        }
        if (channel != null) {
            try {
                channel.close();
            } catch (IOException e) {
            }
        }
    }

    public IOSession interestOps(int ops) {
        this.key.interestOps(ops);
        return this;
    }

    public IOSession interestRead() {
        this.key.interestOps(key.interestOps() | SelectionKey.OP_READ);
        return this;
    }

    public IOSession interestWrite() {
        this.key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
        return this;
    }

    public IOSession interestReadWrite() {
        this.key.interestOps(key.interestOps() | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        return this;
    }

    public IOSession uninterestRead() {
        this.key.interestOps(key.interestOps() & ~SelectionKey.OP_READ);
        return this;
    }

    public IOSession uninterestWrite() {
        this.key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
        return this;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    // public boolean isReading() {
    // return inReading.get();
    // }
    //
    // public void setChain(IOFilterChain chain) {
    // this.chain = chain;
    // }

    /**
     * ��ȡ���ݣ�OP_READ�����¼��Ĵ��������ں�̬--->�û�̬
     */
    // public void readData() {
    // if (inReading.compareAndSet(false, true)) {
    // // SocketChannel channel = (SocketChannel) key.channel();
    // try {
    // // channel.read(buffer);
    // // ʹ����Ϣ���Զ���߽�����������⣨���ͷ�����շ�Э�����������ֽڳ���
    // int length = 0;
    // int total = 0;
    // boolean readed = false;// �Ƿ��ȡ�����ݰ�����
    // do {
    // length += channel.read(buffer);
    // if (!readed && length >= 4) {// ͨ��readed��ʶ�����ѭ��ִ�У�Ӱ��Ч�ʵ�����
    // total = buffer.getInt(0);
    // readed = true;
    // }
    // if (length == -1) {// ����close��ʶEOF
    // key.cancel();
    // channel.close();
    // return;// ��֤��ȷ����
    // }
    // } while (length != total + 4);
    //
    // // ȥ��ǰ�ĸ��ֽ�
    // ByteBuffer content = ByteBuffer.allocate(total);
    // for (int i = 4; i < buffer.position(); i++) {
    // content.put(buffer.get(i));
    // }
    //
    // // ���buffer
    // buffer.clear();
    //
    // // ���ݶ�ȡ��ɣ�����DataPacket����Ӧ�ò㴦��
    // packet = new DataPacket(channel.getRemoteAddress(), content);
    //
    // // �������ɣ������Ѵ��ں�̬���û�̬��������Ӧ�ò㴦��
    // onReadComplete(packet);
    //
    // } catch (IOException e) {
    // // ��channel��дʱ�����쳣
    // this.key.cancel();
    // try {
    // channel.close();
    // } catch (IOException ioe) {
    // // ignore
    // }
    // return;
    // }
    // inReading.compareAndSet(true, false);
    // }
    // // ע��д������ע
    // this.poller.registerWrite(key);
    // }

    /**
     * д�����ݣ�OP_WRITE�����¼��Ĵ��������û�̬--->�ں�̬
     */
    // public void writeData() {
    // // Ӧ�ò�����д����
    // onWriteReady(packet);
    //
    // // ��������д����
    // try {
    // ByteBuffer conetnt = packet.getContent();
    // conetnt.flip();
    //
    // channel.write(conetnt);
    // } catch (IOException e) {
    // // ��channel��дʱ�����쳣
    // this.key.cancel();
    // try {
    // channel.close();
    // } catch (IOException ioe) {
    // // ignore
    // }
    // return;
    // }
    //
    // // ע���������ע
    // this.poller.registerRead(key);
    // }

    // /**
    // * �������ɣ�����Ӧ�ò㴦��
    // *
    // * @param packet
    // */
    // private void onReadComplete(DataPacket packet) {
    // chain.onReadComplete(packet);
    // }

    // /**
    // * ����д����������Ӧ�ò㴦��
    // *
    // * @param packet
    // */
    // private void onWriteReady(DataPacket packet) {
    // chain.onWriteReady(packet);
    // }

    /**
     * ���SelectionKey���������IOSession����
     */
    public static IOSession getSession(SelectionKey key) {
        return (IOSession) key.attachment();
    }
}