package com.bes.common.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	
	public void run()throws Exception{
		BufferedReader br = null;
		ServerSocket servSocket = new ServerSocket(6789);
		
		
		Socket sock = servSocket.accept();
		
		br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		String str = br.readLine();
		
		System.out.println(str);
		
		
	}

	public static void main(String[] args) throws Exception {
		new MyServer().run();
	}
	
}
