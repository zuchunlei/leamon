package com.bes.common.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ������Է���
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
			throw new RuntimeException("�����ʼ��ʱʧ��!");
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
			throw new RuntimeException("����I/Oʱʧ��!");
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
		// ����IO��������ش���
	}
}