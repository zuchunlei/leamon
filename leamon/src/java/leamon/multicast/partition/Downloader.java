package leamon.multicast.partition;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.CountDownLatch;

import leamon.multicast.threadpool.ThreadPool;

/**
 * ·ÖÇøÏÂÔØ
 * 
 * 1024 ------- 138211 10240 ------- 279479 4096 ------- 94404
 */
public class Downloader {

    private static final int DEFAULT_BUFFER_SIZE = 4096;// 4K
    private static final int DEFAULT_TASK_BUFFER = 1024 * 1000 * 100;// 100M

    public void download(String srcFilePath, String destFilePath) {
        try {
            RandomAccessFile src = new RandomAccessFile(srcFilePath, "r");
            long length = src.length();
            src.close();

            RandomAccessFile dest = new RandomAccessFile(destFilePath, "rw");
            dest.setLength(length);
            dest.close();

            int part = (int) (length / DEFAULT_TASK_BUFFER);// 100M
            part = part == 0 ? 1 : part;

            CountDownLatch latch = new CountDownLatch(part);

            ThreadPool pool = new ThreadPool();
            pool.init();
            pool.startup();

            for (int i = 0; i < part; i++) {
                long index = i * DEFAULT_TASK_BUFFER;
                long end = 0;
                if (i != part - 1) {
                    end = (i + 1) * DEFAULT_TASK_BUFFER - 1;
                } else {
                    end = length - 1;
                }
                DownLoadTask task = new DownLoadTask(index, end, new File(srcFilePath), new File(destFilePath), latch);

                pool.addTask(task);
            }

            try {
                latch.await();
                System.out.println("copy file is already finished!");
            } catch (InterruptedException e) {
                // ignore
            }

            pool.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class DownLoadTask implements Runnable {

        private long start;
        private long end;
        private File src;
        private File dest;
        private CountDownLatch latch;

        public DownLoadTask(long start, long end, File src, File dest, CountDownLatch latch) {
            this.start = start;
            this.end = end;
            this.src = src;
            this.dest = dest;
            this.latch = latch;
        }

        @Override
        public void run() {
            RandomAccessFile s = null;
            RandomAccessFile d = null;
            try {
                s = new RandomAccessFile(src, "r");
                d = new RandomAccessFile(dest, "rw");

                s.seek(start);
                d.seek(start);

                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                int len = 0;
                int size = 0;
                while (end - d.getFilePointer() > 0) {
                    if (end - d.getFilePointer() > DEFAULT_BUFFER_SIZE) {
                        size = DEFAULT_BUFFER_SIZE;
                    } else {
                        size = (int) (end - d.getFilePointer());
                    }

                    if ((len = s.read(buffer, 0, size)) != -1) {
                        d.write(buffer, 0, len);
                    }
                }

            } catch (IOException e) {
                // ignore
            } finally {
                try {
                    if (s != null) {
                        s.close();
                    }
                    if (d != null) {
                        d.close();
                    }
                } catch (IOException e) {
                    // ignore
                }
            }

            System.out.println(Thread.currentThread().getName() + ": already finished!");
            latch.countDown();
        }
    }

}
