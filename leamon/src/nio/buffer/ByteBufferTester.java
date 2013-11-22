package nio.buffer;

import java.nio.ByteBuffer;

/**
 * ByteBuffer�����л�������������ݵķ���������getInt��getDouble�ȡ�
 * ������ByteBuffer�;�����DataOutput����������
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
