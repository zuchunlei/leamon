package nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import network.NioTester;

public class FileChannelTester {

	public static void main(String[] args) throws Exception {

		RandomAccessFile file = new RandomAccessFile("C:/1.txt", "rw");
		FileChannel channel = file.getChannel();

		ByteBuffer buffer = ByteBuffer.allocate(100);
		for (int i = 0; i < buffer.capacity(); i++) {
			buffer.put((byte) i);
		}

		buffer.flip();
		channel.write(buffer);

		channel.close();
		file.close();
	}

}
