package com.bes.common.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 网络回显服务
 */
public class EchoServer {
	private static final int DEFAULT_PORT = 10001;
	private static final int DEFAULT_ACCEPT_NUM = 50;

	private ServerSocket servSock;
	private volatile boolean start;

	public EchoServer(int port, int backlog) {
		try {
			this.servSock = new ServerSocket(port, backlog);
			this.start = true;
		} catch (IOException e) {
			throw new RuntimeException("服务初始化时失败!");
		}
	}

	public EchoServer() {
		this(DEFAULT_PORT, DEFAULT_ACCEPT_NUM);
	}

	public void handleConnect() {
		checkConnect();
		try {
			while (start) {
				Socket sock = servSock.accept();
				new Thread(new ConnectHandler(sock)).start();
			}
		} catch (IOException e) {
			throw new RuntimeException("网络I/O时失败!");
		} finally {
			if (servSock != null) {
				try {
					servSock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void shutDown() {
		start = false;
	}

	private void checkConnect() {

	}

}

class ConnectHandler implements Runnable {
	private Socket conn;

	public ConnectHandler(Socket socket) {
		this.conn = socket;
	}

	@Override
	public void run() {
		// 进行IO操作的相关代码
	}
}