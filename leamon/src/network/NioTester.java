package network;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class NioTester {

	public static void main(String[] args) {
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
				service.execute(new Task(iter.next()));
			}
		}
	}
}

class Task implements Runnable {
	private SelectionKey key;

	public Task(SelectionKey key) {
		this.key = key;
	}

	public void run() {
		SelectableChannel channel = key.channel();
		System.out.println(channel);
	}
}