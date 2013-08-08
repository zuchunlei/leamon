package com.bes.common.net;

import java.io.PrintWriter;
import java.net.Socket;

public class MyClient {

	public void run()throws Exception{
		Socket sock = new Socket("192.168.40.6",6789);
//		BufferedReader brc = new BufferedReader(new InputStreamReader(System.in));
//		String str = brc.readLine();
//		brc.close();
		
		byte[] buffer = new byte[9002];
		for(int i=0;i<9000;i++){
			buffer[i] = 'k';
		}
		buffer[9000]='\n';
		buffer[9001]='\r';
		
		PrintWriter pw = new PrintWriter(sock.getOutputStream(),true);
		String str = new String(buffer);
		pw.print(str);
		pw.flush();
		
		
		pw.close();
	}
	
	public static void main(String[] args) throws Exception {
		new MyClient().run();
	}
	
}
