package com.bes.interview.net;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class TransportClient {

	public static void main(String[] args) throws Exception {
		Socket sock = new Socket("192.168.40.6", 6000);
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(sock.getOutputStream());
			os.writeObject(new User(1,"×æ´ºÀ×"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
			if (sock != null) {
				sock.close();
			}
		}
	}
}
