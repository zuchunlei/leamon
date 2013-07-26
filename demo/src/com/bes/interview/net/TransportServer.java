package com.bes.interview.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TransportServer {
	private static final int DEFAULT_PORT = 6000;

	private ServerSocket servSock;

	public TransportServer() {
		this(DEFAULT_PORT, 50);
	}

	public TransportServer(int port, int backlog) {
		try {
			this.servSock = new ServerSocket(port, backlog);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handle() {
		try {
			while (true) {
				Socket sock = servSock.accept();
				new Thread(new ConnectHandler(sock)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new TransportServer().handle();
	}
}

class ConnectHandler implements Runnable {
	private Socket connect;

	public ConnectHandler(Socket socket) {
		this.connect = socket;
	}

	@Override
	public void run() {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(connect.getInputStream());
			Object obj = in.readObject();
			System.out.println(obj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
