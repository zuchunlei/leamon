package network;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class NioBufferTester {

	public static void main(String[] args) {
		String name = "�洺��";
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(100);
		byteBuffer.put(name.getBytes());
		
		CharBuffer charBuffer = CharBuffer.allocate(100);
		charBuffer.put(name.toCharArray());
	}

}
