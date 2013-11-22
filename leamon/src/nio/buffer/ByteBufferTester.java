package nio.buffer;

import java.nio.ByteBuffer;

/**
 * ByteBuffer类中有获得其他类型数据的方法，比如getInt，getDouble等。
 * 这样，ByteBuffer就具有了DataOutput流的能力。
 * 
 */
public class ByteBufferTester {

	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put((byte) 1);
		buffer.put((byte) 2);
		buffer.put((byte) 3);
		buffer.put((byte) 4);

		buffer.flip();

		int result = buffer.getInt();

		System.out.println(result);
	}

}
