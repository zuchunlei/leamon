package nio.reactor.general.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import nio.reactor.general.io.IOListener;

/**
 * NIO Reactor模式的Echo Server实现
 */
public class Server {

    private String host;
    private int port;

    private AtomicInteger incr;// 计数器
    private volatile boolean running;
    // private List<IOFilter> filters;// IO过滤器集合
    // private IOFilterChain chain;// 过滤链对象
    // private IOHandler handler;// 处理器对象

    private IOListener listener;// IO处理器，供外部扩展
    private Poller[] pollers;// 轮询对象，处理SocketChannel的I/O事件
    private Acceptor acceptor;// 监听对象，处理ServerSocketChannel的accept事件

    // private AtomicBoolean accept;// 连接请求被接收的标识

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
        this.incr = new AtomicInteger(Integer.MAX_VALUE);
        // this.filters = new ArrayList<IOFilter>();

        // this.accept = new AtomicBoolean();
        this.pollers = new Poller[Runtime.getRuntime().availableProcessors() + 1];// Poller的个数为当前可用CPU+1
        for (int i = 0; i < pollers.length; i++) {
            pollers[i] = new Poller();
        }
        this.acceptor = new Acceptor();
    }

    // public void setHandler(IOHandler handler) {
    // this.handler = handler;
    // }

    public void setListener(IOListener listener) {
        this.listener = listener;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    // public IOFilterChain getIOFilterChain() {
    // if (chain == null) {
    // chain = new Node();
    // }
    // return chain;
    // }

    // public void addIOFilter(IOFilter filter) {
    // filters.add(filter);
    // }

    public boolean start() {
        running = true;
        // 启动Poller
        for (int i = 0; i < pollers.length; i++) {
            String name = "Poller Thread - " + i;
            new Thread(pollers[i], name).start();
        }
        // 启动Acceptor
        new Thread(this.acceptor, "Acceptor Thread").start();

        return running;
    }

    public void shutdown() {
        running = false;
        acceptor.wakeup();
    }

    /**
     * 接收网络连接的接收器
     */
    class Acceptor implements Runnable {

        Selector selector;// 选择器

        public void wakeup() {
            if (selector != null) {
                selector.wakeup();
            }
        }

        public void run() {
            try {
                selector = Selector.open();
                ServerSocketChannel servSockChannel = ServerSocketChannel.open();
                servSockChannel.configureBlocking(false);
                servSockChannel.socket().bind(new InetSocketAddress(host, port), 200);
                servSockChannel.register(selector, SelectionKey.OP_ACCEPT);
                while (running) {
                    int selected = selector.select();
                    if (selected > 0) {

                        Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                        while (it.hasNext()) {
                            SelectionKey key = it.next();
                            // System.out.println("连接来了");
                            it.remove();// 必须显示的删除
                            if (!key.isValid()) {
                                key.cancel();
                                key.channel().close();
                                break;
                            }

                            // OP_ACCEPT就绪事件应该由Acceptor来消费，accept()返回的SocketChannel交与Poller来处理。
                            SocketChannel channel = accept(key);
                            // 策略选择一个Poller将该key注册到Poller上。
                            // int offset = Math.abs(incr.decrementAndGet()
                            // % pollers.length);

                            pollers[Math.abs(incr.decrementAndGet() % pollers.length)].registerChannel(channel);

                            // while (!accept.compareAndSet(true, false))
                            // ;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 由Acceptor来处理OP_ACCEPT就绪事件
         */
        SocketChannel accept(SelectionKey key) throws IOException {
            SocketChannel channel = null;
            if (key.isAcceptable()) {
                ServerSocketChannel servSockChannel = (ServerSocketChannel) key.channel();
                channel = servSockChannel.accept();
            }
            return channel;
        }
    }

    /**
     * IO轮询对象，处理SocketChannel的I/O事件。
     */
    class Poller implements Runnable {
        // private ConcurrentLinkedQueue<SelectionKey> channels;//
        // 等待注册到Poller中的SelectionKey

        // 等待注册到Poller中的SocketChannel，该Channel由ServerSocketChannel.accept()返回，与Selector没有注册关系
        // private ConcurrentLinkedQueue<SocketChannel> channels;

        // private ConcurrentLinkedQueue<SelectionKey> readkeys;// 读就绪选择键集合
        // private ConcurrentLinkedQueue<SelectionKey> writekeys;// 写就绪选择键集合

        // 去掉读写数据池，原来基于SelectionKey作为Key来保存读写的数据Map，改为使用SelectionKey附件的方式传递。
        // private ConcurrentHashMap<SelectionKey, ByteBuffer> datapools;//
        // 读写通道的数据池

        private ConcurrentLinkedQueue<Runnable> events;// 兴趣事件注册队列，统一注册方式。

        private AtomicBoolean wakeup;// 唤醒标识，原子类，排他。
        private Selector selector;
        // private Executor executor;// 具体IO执行的线程池

        // private BlockingQueue<Runnable> ioevents;// IO读写事件

        public Poller() {
            // this.channels = new ConcurrentLinkedQueue<SelectionKey>();
            // this.channels = new ConcurrentLinkedQueue<SocketChannel>();
            //
            // this.readkeys = new ConcurrentLinkedQueue<SelectionKey>();
            // this.writekeys = new ConcurrentLinkedQueue<SelectionKey>();
            this.events = new ConcurrentLinkedQueue<Runnable>();
            // this.datapools = new ConcurrentHashMap<SelectionKey,
            // ByteBuffer>();

            this.wakeup = new AtomicBoolean();// 默认为false

            try {
                this.selector = Selector.open();
            } catch (IOException e) {
            }
            // this.executor = Executors.newCachedThreadPool();
            // this.ioevents = new LinkedBlockingQueue<Runnable>();

            // handlIoEvent();// 处理IO读写事件
        }

        public Selector getSelector() {
            return selector;
        }

        // public Executor getExecutor() {
        // return executor;
        // }

        public void run() {
            // handlIoEvent();// 启动IO读写线程

            while (running) {
                try {
                    int selected = selector.select(1000);// 选择等待1s
                    // interRegisterEvent();// 统一事件注册方法
                    if (selected > 0) {
                        processEvents(selector.selectedKeys());
                        // Iterator<SelectionKey> it =
                        // selector.selectedKeys().iterator();
                        // while (it.hasNext()) {
                        // SelectionKey key = it.next();
                        // it.remove();
                        //
                        // IOSession session = (IOSession) key.attachment();
                        // if(session.isReadable()){
                        // listener.read(session);
                        // }
                        // // if (key.isValid() && key.isWritable()) {//
                        // // 处理写就绪事件
                        // // // 取消兴趣写
                        // // // unRegisterWrite(key);
                        // // // IOWriteWork work = new IOWriteWork(this,
                        // // // key);
                        // // // executor.execute(work);
                        // //
                        // // IOSession session = (IOSession) key.attachment();
                        // // // session.writeData();
                        // // if (session != null) {
                        // // // addWriteEvent(session);
                        // // IOWriteWork work = new IOWriteWork(session);
                        // // executor.execute(work);
                        // // }
                        // // }
                        // // if (key.isValid() && key.isReadable()) {//
                        // // 处理读就绪事件
                        // // // 创建IOSession对象，该对象内部封装了具体的IO读写逻辑，以及业务处理的逻辑。
                        // // IOSession session = (IOSession) key.attachment();
                        // //
                        // // // if (session == null) {
                        // // // session = new IOSession(key, this);
                        // // // session.setChain(getIOFilterChain());//
                        // // // 设置业务处理链
                        // // // key.attach(session);
                        // // // }
                        // //
                        // // // 取消兴趣读，读数据，业务处理，注册兴趣写。
                        // // // unRegisterRead(key);// 取消所有兴趣关注点
                        // //
                        // // IOReadWork work = new IOReadWork(session);
                        // // executor.execute(work);
                        // //
                        // // // session对象具体处理数据读写的逻辑
                        // // // session.readData();
                        // // // addReadEvent(session);
                        // // }
                        // }
                    }
                    interRegisterEvent();// 统一事件注册方法

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void processEvents(final Collection<SelectionKey> selectedKeys) {
            for (SelectionKey key : selectedKeys) {
                processEvent(key);
            }
            selectedKeys.clear();
        }

        private void processEvent(final SelectionKey key) {
            IOSession session = (IOSession) key.attachment();
            try {
                if (session.isReadable()) {
                    listener.read(session);
                } else if (session.isWritable()) {
                    listener.write(session);
                }
            } catch (Exception e) {
                // log exception msg
                listener.detach(session);
                session.close();
            }
        }

        /**
         * 内部处理IO读写事件
         */
        // void handlIoEvent() {
        // Runnable runner = new Runnable() {
        // @Override
        // public void run() {
        // while (running) {
        // Runnable command = ioevents.poll();// 获取IO task
        // if (command != null) {
        // executor.execute(command);
        // }
        // }
        // }
        // };
        // String name = Thread.currentThread().getName()
        // + " [io events executor]";
        // new Thread(runner, name).start();
        // }

        /**
         * 内部统一注册信道的事件
         */
        void interRegisterEvent() {
            // 注册信道的接收事件
            // SocketChannel channel = channels.poll();
            // while (channel != null) {
            // try {
            // // if (key.isValid() && key.isAcceptable()) {
            // // ServerSocketChannel servSockChannel =
            // // (ServerSocketChannel) key
            // // .channel();
            // // SocketChannel channel = servSockChannel.accept();
            // // // while (!accept.compareAndSet(false, true))
            // // // ;
            //
            // channel.configureBlocking(false);// 必须将信道配置为非阻塞模式
            // channel.register(selector, SelectionKey.OP_READ);
            // // } else {
            // // key.cancel();
            // // }
            // } catch (IOException e) {
            // }
            // channel = channels.poll();
            // }
            //
            // // 注册信道的读就绪事件
            // SelectionKey key = readkeys.poll();
            // while (key != null) {
            // try {
            // if (!key.isValid()) {
            // key.cancel();
            // key.channel().close();
            // }
            // // SocketChannel channel = (SocketChannel) key.channel();
            // // SelectionKey sk = channel.register(selector,
            // // key.interestOps() | SelectionKey.OP_READ);
            // // System.out.println(sk.attachment() != null);
            // key.interestOps(key.interestOps() | SelectionKey.OP_READ);
            // } catch (IOException e) {
            // }
            // key = readkeys.poll();
            // }
            // // 注册信道的写就绪事件
            // key = writekeys.poll();
            // while (key != null) {
            // try {
            // if (!key.isValid()) {
            // key.cancel();
            // key.channel().close();
            // }
            // // 以Channel的方式重新注册，产生的Key是否对附件有影响？
            // // SocketChannel channel = (SocketChannel) key.channel();
            // // SelectionKey sk = channel.register(selector,
            // // key.interestOps() | SelectionKey.OP_WRITE);
            // // System.out.println(sk.attachment() != null);
            // key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
            // // System.out.println(key.attachment() != null);
            // } catch (IOException e) {
            // }
            // key = writekeys.poll();
            // }

            // 采用统一的注册事件的方式来处理
            for (Iterator<Runnable> it = events.iterator(); it.hasNext();) {
                Runnable task = it.next();
                it.remove();// 显式删除注册事件
                task.run();
            }

            // 重置唤醒标识
            wakeup.set(false);
        }

        // void registerChannel(SelectionKey key) {
        // channels.offer(key);
        // if (wakeup.compareAndSet(false, true)) {
        // selector.wakeup();
        // }
        // }
        /**
         * 注册信道的接收事件
         * 
         * @param channel
         *            等待注册的SocketChannel
         */
        void registerChannel(SocketChannel channel) {
            // channels.offer(channel);
            final SocketChannel sc = channel;
            final Poller poller = this;
            events.add(new Runnable() {
                @Override
                public void run() {
                    try {
                        sc.configureBlocking(false);
                        sc.register(selector, 0);// 不关注任何事件
                        IOSession session = new IOSession(sc, poller);
                        listener.attach(session);
                        // session.setChain(getIOFilterChain());// 设置业务处理链
                        // sc.register(selector, SelectionKey.OP_READ,
                        // session);// 将IOSession与SocketChannel进行关联，注册到Poller中
                    } catch (IOException e) {
                        // ignore
                    }
                }
            });
            if (wakeup.compareAndSet(false, true)) {
                selector.wakeup();
            }
        }

        /**
         * 注册信道的读就绪事件
         * 
         * @param key
         */
        // void registerRead(SelectionKey key) {
        // // readkeys.offer(key);
        // final SelectionKey sk = key;
        // events.add(new Runnable() {
        // @Override
        // public void run() {
        // try {
        // if (!sk.isValid()) {
        // sk.cancel();
        // sk.channel().close();
        // }
        // sk.interestOps(sk.interestOps() | SelectionKey.OP_READ);
        // } catch (IOException e) {
        // // ignore
        // }
        // }
        // });
        // if (wakeup.compareAndSet(false, true)) {
        // selector.wakeup();
        // }
        // }

        /**
         * 注册信道的写就绪事件
         * 
         * @param key
         */
        // void registerWrite(SelectionKey key) {
        // // writekeys.offer(key);
        // final SelectionKey sk = key;
        // events.add(new Runnable() {
        // @Override
        // public void run() {
        // try {
        // if (!sk.isValid()) {
        // sk.cancel();
        // sk.channel().close();
        // }
        // sk.interestOps(sk.interestOps() | SelectionKey.OP_WRITE);
        // } catch (IOException e) {
        // // ignore
        // }
        // }
        // });
        // if (wakeup.compareAndSet(false, true)) {
        // selector.wakeup();
        // }
        // }

        /**
         * 取消兴趣读
         * 
         * @param key
         */
        // void unRegisterRead(SelectionKey key) {
        // // key.interestOps(key.interestOps() ^ SelectionKey.OP_READ);
        // key.interestOps(key.interestOps() & ~SelectionKey.OP_READ);
        // }

        /**
         * 取消兴趣写
         * 
         * @param key
         */
        // void unRegisterWrite(SelectionKey key) {
        // // key.interestOps(key.interestOps() ^ SelectionKey.OP_WRITE);
        // key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
        // }

        /**
         * 读写数据的缓冲区
         * 
         * @param key
         * @param buffer
         */
        // void putData(SelectionKey key, ByteBuffer buffer) {
        // datapools.put(key, buffer);
        // }

        /**
         * 通过Key来获取数据
         * 
         * @param key
         * @return
         */
        // ByteBuffer getData(SelectionKey key) {
        // return datapools.get(key);
        // }

        /**
         * 处理IO Read事件
         * 
         * @param session
         */
        // void addReadEvent(final IOSession session) {
        // if (!session.isReading()) {
        // Runnable command = new Runnable() {
        // @Override
        // public void run() {
        // session.readData();
        // }
        // };
        // ioevents.add(command);
        // }
        // }

        /**
         * 处理IO Write事件
         * 
         * @param session
         */
        // void addWriteEvent(final IOSession session) {
        // Runnable command = new Runnable() {
        // @Override
        // public void run() {
        // session.writeData();
        // }
        // };
        // ioevents.add(command);
        // }
    }

    /**
     * 过滤器链的默认实现
     */
    // class Node implements IOFilterChain {
    //
    // private IOFilter filter;
    // private IOFilterChain next;
    //
    // Node() {
    // this(0);
    // }
    //
    // private Node(int index) {
    // if (index < filters.size()) {
    // filter = filters.get(index);
    // next = new Node(++index);
    // }
    // }
    //
    // @Override
    // public void onReadComplete(DataPacket packet) {
    // if (filter != null) {
    // filter.onReadComplete(packet, next);
    // return;
    // }
    // handler.handle(packet);
    // }
    //
    // @Override
    // public DataPacket onWriteReady(DataPacket packet) {
    // if (filter != null) {
    // packet = filter.onWriteReady(packet, next);
    // return packet;
    // }
    // return packet;
    // }
    // }
}
