package nio.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * NIO ×Ö·û¼¯²âÊÔ
 */
public class CharsetTester {

    public static void main(String[] args) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.clear();
        buffer.put("×æ´ºÀ×".getBytes());
        buffer.flip();

        Charset charset = Charset.forName("gbk");
        CharsetDecoder decoder = charset.newDecoder();
        CharBuffer charbuffer = decoder.decode(buffer);

        System.out.println(charbuffer.array());

    }
}
