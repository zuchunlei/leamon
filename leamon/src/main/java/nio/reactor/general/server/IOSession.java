package nio.reactor.general.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nio.reactor.general.server.Server.Poller;

/**
 * IOSession封装了一个网络连接的读写操作，并且内部维护了读写过程中需要的状态信息。
 */
public class IOSession {
    // private static final int DEFAULT_SIZE = 1024;

    private SelectionKey key;// key对象包含了io就绪事件
    // private volatile AtomicBoolean inReading;// 是否处于读状态
    // private ByteBuffer buffer;// 数据缓冲区
    private Poller poller;// 轮询器
    private SocketChannel channel;// IO信道
    // private DataPacket packet;

    // private IOFilterChain chain;// IO过滤链

    // public IOSession(SelectionKey key, Poller poller) {
    // this.key = key;
    // this.channel = (SocketChannel) key.channel();
    // this.poller = poller;
    // this.inReading = new AtomicBoolean();// 默认为false
    // this.buffer = ByteBuffer.allocate(DEFAULT_SIZE);
    // }

    private final Map<String, Object> attributes = new ConcurrentHashMap<>();

    public IOSession(SocketChannel channel, Poller poller) {
        this.channel = channel;
        this.key = channel.keyFor(poller.getSelector());
        this.key.attach(this);
        this.poller = poller;
        // this.inReading = new AtomicBoolean();// 默认为false
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
     * 读取数据，OP_READ就绪事件的处理方法，内核态--->用户态
     */
    // public void readData() {
    // if (inReading.compareAndSet(false, true)) {
    // // SocketChannel channel = (SocketChannel) key.channel();
    // try {
    // // channel.read(buffer);
    // // 使用消息的自定义边界来解决该问题（发送方与接收方协定），基于字节长度
    // int length = 0;
    // int total = 0;
    // boolean readed = false;// 是否读取了数据包长度
    // do {
    // length += channel.read(buffer);
    // if (!readed && length >= 4) {// 通过readed标识，解决循环执行，影响效率的问题
    // total = buffer.getInt(0);
    // readed = true;
    // }
    // if (length == -1) {// 读到close标识EOF
    // key.cancel();
    // channel.close();
    // return;// 保证正确返回
    // }
    // } while (length != total + 4);
    //
    // // 去除前四个字节
    // ByteBuffer content = ByteBuffer.allocate(total);
    // for (int i = 4; i < buffer.position(); i++) {
    // content.put(buffer.get(i));
    // }
    //
    // // 清空buffer
    // buffer.clear();
    //
    // // 数据读取完成，构造DataPacket进行应用层处理
    // packet = new DataPacket(channel.getRemoteAddress(), content);
    //
    // // 网络读完成（数据已从内核态到用户态），进行应用层处理
    // onReadComplete(packet);
    //
    // } catch (IOException e) {
    // // 在channel读写时发生异常
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
    // // 注册写就绪关注
    // this.poller.registerWrite(key);
    // }

    /**
     * 写入数据，OP_WRITE就绪事件的处理方法，用户态--->内核态
     */
    // public void writeData() {
    // // 应用层数据写处理
    // onWriteReady(packet);
    //
    // // 进行网络写操作
    // try {
    // ByteBuffer conetnt = packet.getContent();
    // conetnt.flip();
    //
    // channel.write(conetnt);
    // } catch (IOException e) {
    // // 在channel读写时发生异常
    // this.key.cancel();
    // try {
    // channel.close();
    // } catch (IOException ioe) {
    // // ignore
    // }
    // return;
    // }
    //
    // // 注册读就绪关注
    // this.poller.registerRead(key);
    // }

    // /**
    // * 网络读完成，进行应用层处理
    // *
    // * @param packet
    // */
    // private void onReadComplete(DataPacket packet) {
    // chain.onReadComplete(packet);
    // }

    // /**
    // * 网络写就绪，进行应用层处理
    // *
    // * @param packet
    // */
    // private void onWriteReady(DataPacket packet) {
    // chain.onWriteReady(packet);
    // }

    /**
     * 获得SelectionKey对象关联的IOSession对象
     */
    public static IOSession getSession(SelectionKey key) {
        return (IOSession) key.attachment();
    }
}