package leamon.multicast.partition;

import leamon.multicast.threadpool.Future;
import leamon.multicast.threadpool.ThreadPool;

import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {

    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool();
        pool.init();
        pool.startup();

        Future future = pool.submitTask(() -> {
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello world";
        });

        future.addListener((v, e) -> System.out.println(v));
    }
}
