package nio.reactor.general.server;

/**
 * 具体IO事件处理的任务对象，由外部线程池调用。
 */
public class IOWriteWork implements Runnable {
    // private Server.Poller poller;
    // private SelectionKey key;
    //
    // public IOWriteWork(Poller poller, SelectionKey key) {
    // this.poller = poller;
    // this.key = key;
    // }

    private IOSession session;

    public IOWriteWork(IOSession session) {
        this.session = session;
    }

    // 取消兴趣写，写数据到信道，注册兴趣读。
    public void run() {
        // SocketChannel channel = (SocketChannel) key.channel();
        // try {
        // // unRegisterWrite();
        // // ByteBuffer buffer = poller.getData(key);
        // ByteBuffer buffer = (ByteBuffer) key.attachment();// 以附件的方式获取读写缓冲区
        // buffer.flip();
        // channel.write(buffer);
        // poller.registerRead(key);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        session.writeData();
    }
}
