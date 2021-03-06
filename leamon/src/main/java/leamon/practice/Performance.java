package leamon.practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 性能相关
 */
public class Performance {

    public static void main(String[] args) {
        new Thread(new MemoryTask(), "memory task").start();
        new Thread(new CalculateTask(), "calculate task").start();
        new Thread(new IOTask(), "io task").start();
        new Thread(new CopyTask(), "copy task").start();
    }

    /**
     * 内存分配任务
     */
    static class MemoryTask implements Runnable {
        @Override
        public void run() {
            List<Object> list = new ArrayList<Object>();
            int count = 0;

            while (true) {
                list.add(new Object());
                count++;

                if (count % 10000 == 0) {
                    list.clear();
                }
            }
        }
    }

    /**
     * 计算任务
     */
    static class CalculateTask implements Runnable {
        @Override
        public void run() {
            long value = 100000;
            while (true) {
                new Random(value).nextLong();
            }
        }
    }

    /**
     * IO读写任务
     */
    static class IOTask implements Runnable {
        @Override
        public void run() {
            try {
                File file = new File("IOTask.txt");
                FileOutputStream in = new FileOutputStream(file);
                int i = 0;
                while (i < Integer.MAX_VALUE) {
                    byte[] data = String.valueOf(i++).getBytes();
                    in.write(data);
                }
                in.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    /**
     * Copy任务
     */
    static class CopyTask implements Runnable {
        @Override
        public void run() {
            try {
                FileInputStream in = new FileInputStream("src.txt");
                FileOutputStream out = new FileOutputStream("dest.txt");
                byte[] buffer = new byte[2048];
                int len = 0;

                while ((len = in.read(buffer)) != 0) {
                    out.write(buffer, 0, len);
                }

                in.close();
                out.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }

}
