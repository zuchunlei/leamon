package leamon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Tester {
    private static ThreadLocal<Boolean> locals = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return new Boolean(false);
        }
    };
    private List<Worker> workerList;
    private AtomicInteger counter = new AtomicInteger();

    public Tester() {
        workerList = new ArrayList<Worker>();
    }

    public static void main(String[] args) throws Exception {
        // Tester t = new Tester();
        // t.start();
        // Thread.sleep(1000 * 30);
        // // t.stop();
        //
        // System.out.println("lalal!");
        // t.get();

        // Map<Integer, Object> map = new HashMap<Integer, Object>();
        // map.put(1, new Object());
        // map.put(2, new Object());
        // map.put(3, new Object());
        // map.put(4, new Object());
        //
        // for (Map.Entry<Integer, Object> entry : map.entrySet()) {
        // int key = entry.getKey();
        // map.remove(key);
        // }
        Tester t = new Tester();
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
            t.some(sum);
        }
    }

    public void some(int num) {
        System.out.println(num);
    }

    public void biz() {
        synchronized (this) {
            try {
                System.out.println(locals.get().booleanValue());
                locals.set(new Boolean(true));
                this.counter.incrementAndGet();
                this.wait();
            } catch (InterruptedException e) {
                // do noting
            }
            this.counter.decrementAndGet();
            System.out.println("working");
        }
    }

    public void start() {
        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker(this);
            worker.start();
            workerList.add(worker);
        }
    }

    public void stop() {
        for (Worker worker : workerList) {
            worker.finish();
            worker.interrupt();
        }
    }

    public void get() {
        System.out.println(this.counter.get());
    }
}

class Worker extends Thread {
    private Tester lock;
    private volatile boolean stop = false;

    public Worker(Tester lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        while (!stop) {
            lock.biz();
        }
    }

    public void finish() {
        stop = true;
    }
}