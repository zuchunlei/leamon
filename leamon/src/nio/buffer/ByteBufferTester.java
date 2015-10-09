package nio.buffer;

import java.nio.ByteBuffer;

/**
 * ByteBuffer类中有获得其他类型数据的方法，比如getInt，getDouble等。
 * 这样，ByteBuffer就具有了DataOutput/DataInput流的能力。
 */
public class ByteBufferTester {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((byte) 1);// 0x1
        buffer.put((byte) 2);// 0x2
        buffer.put((byte) 3);// 0x3
        buffer.put((byte) 4);// 0x4

        // 数值 1，2，3，4的16进制为 0x1 0x2 0x3 0x4
        // 字符'1','2','3','4'的16进制为0x31，0x32，0x33，0x34

        buffer.flip();

        // 一个字节最多表示256
        // 1*256^3+2*256^2+3*256^1+4*256^0 其中^代表幂运算
        int result = buffer.getInt();

        System.out.println(result);
    }
}
