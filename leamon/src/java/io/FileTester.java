package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

//89M的数据
//
// buffer为1K
// io nio 5375,4375
//
// buffer为1M
// io nio 3719,1657
//---------------------------------------------------------------
// 3.46G
//
// buffer为1M
// io VS nio 178391，190750
//
// buffer为10M
// io VS nio 157281，171500

public class FileTester {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        nio();
        // io();
        System.out.println(System.currentTimeMillis() - start);// 5375,4375
    }

    public static void nio() throws Exception {
        FileInputStream in = new FileInputStream("F:/1.txt");
        FileChannel inChannel = in.getChannel();

        FileOutputStream out = new FileOutputStream("F:/3.txt");
        FileChannel outChannel = out.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(102400);

        while (inChannel.read(buffer) != -1) {
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }

        in.close();
        inChannel.close();
        out.close();
        outChannel.close();
    }

    public static void io() throws Exception {
        FileInputStream in = new FileInputStream("F:/1.txt");
        FileOutputStream out = new FileOutputStream("F:/2.txt");

        byte[] buffer = new byte[102400];
        int length = 0;
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }

        in.close();
        out.close();
    }
}
