package io;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelTester {

    public static void main(String[] args) throws Exception {

        FileInputStream fis = new FileInputStream("D:/nio????????.txt");
        FileChannel channel = fis.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(100);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        int length = channel.write(buffer);
        System.out.println(length);

        channel.close();
        fis.close();
    }

}
