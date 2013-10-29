package io;

import java.io.FileInputStream;
import java.nio.channels.FileChannel;

public class ChannelTester {

	public static void main(String[] args) throws Exception {
		FileInputStream in = new FileInputStream("/root/1.txt");
		FileChannel channel = in.getChannel();

		channel.close();// channel关闭，相关的流也自动关闭
		in.close();
	}

}
