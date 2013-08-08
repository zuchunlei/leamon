package com.bes.common.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 网络回显服务
 */
public class EchoServer {
	private static final int DEFAULT_PORT = 10001;
	private static final int DEFAULT_ACCEPT_NUM = 50;

	private ServerSocket servSock;
	private volatile boolean start;
	private BlockingQueue<Socket> blockQueue = new LinkedBlockingQueue<Socket>();

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

	public void handleAccept() {
		checkConnect();
		try {
			while (start) {
				Socket sock = servSock.accept();
				blockQueue.put(sock);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
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
	
	public void waitShutDown(){
		try {
			while (start) {
				Thread.sleep(1000 * 3);
			}
		} catch (Exception e) {
		}
	}

	private void doStart(){
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Socket socket = EchoServer.this.blockQueue.poll();
				EchoServer.this.handleSocket(socket);
			}
		};
		Thread thread = new Thread(runnable);
		thread.setDaemon(true);
		thread.start();
	}
	
	public void handleSocket(Socket sock){
		
	}
	
	public static void main(String[] args) {
		final EchoServer server = new EchoServer();
		new Thread(new Runnable() {
			@Override
			public void run() {
				server.handleAccept();
			}
		}).start();
		server.waitShutDown();
	}
}
