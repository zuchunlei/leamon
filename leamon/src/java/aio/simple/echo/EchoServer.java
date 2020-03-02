package aio.simple.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 通过aio方式的echo server
 */
public class EchoServer {

    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void listen() {
        try {
            ExecutorService executor = Executors.newCachedThreadPool();// 初始化线程池
            AsynchronousChannelGroup group = AsynchronousChannelGroup.withCachedThreadPool(executor, 5);// 资源组，共享该组内资源（线程等）

            try (AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(group)) {
                server.bind(new InetSocketAddress(port));
                System.out.println("Echo listen on " + port);

                server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                    final ByteBuffer echoBuffer = ByteBuffer.allocate(1024);

                    public void completed(AsynchronousSocketChannel result, Object attachment) {
                        System.out.println("server accept new connection complet !!");
                        try {
                            echoBuffer.clear();
                            result.read(echoBuffer).get();// asynchronous
                                                          // read
                                                          // data，os
                                                          // finished

                            echoBuffer.flip();
                            // echo data
                            result.write(echoBuffer);// asynchronous
                                                     // write data
                            echoBuffer.flip();
                            // System.out.println("Echoed '" + new
                            // String(echoBuffer.array()) + "' to " +
                            // result);
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println(e.toString());
                        } finally {
                            try {
                                result.close();
                                server.accept(null, this);
                            } catch (Exception e) {
                                System.out.println(e.toString());
                            }
                        }
                        System.out.println("done...");
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        System.out.println("server failed: " + exc);
                    }
                });

                try {
                    // Wait for ever
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
