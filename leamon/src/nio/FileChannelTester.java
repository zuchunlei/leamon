package nio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

public class FileChannelTester {

	public static void main(String[] args) throws Exception {
		String path = "D:/1/1.txt";
		File afile = new File(path);
		Map<String, File> map = new HashMap<String, File>();

		map.put(path, afile);

		File f = map.get(path);
		OutputStream os = new FileOutputStream(f);
		System.out.println(os);
		
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
