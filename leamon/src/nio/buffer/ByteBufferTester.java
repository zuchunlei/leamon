package nio.buffer;

import java.nio.ByteBuffer;

/**
 * ByteBuffer�����л�������������ݵķ���������getInt��getDouble�ȡ�
 * ������ByteBuffer�;�����DataOutput/DataInput����������
 */
public class ByteBufferTester {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((byte) 1);// 0x1
        buffer.put((byte) 2);// 0x2
        buffer.put((byte) 3);// 0x3
        buffer.put((byte) 4);// 0x4

        // ��ֵ 1��2��3��4��16����Ϊ 0x1 0x2 0x3 0x4
        // �ַ�'1','2','3','4'��16����Ϊ0x31��0x32��0x33��0x34

        buffer.flip();

        // һ���ֽ�����ʾ256
        // 1*256^3+2*256^2+3*256^1+4*256^0 ����^����������
        int result = buffer.getInt();

        System.out.println(result);
    }
}
