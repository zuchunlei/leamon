package leamon.concurrent.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Java对象隐式锁monitor与J.U.C包显示锁Lock的性能对比
 */
public class LockAndMonitorComparer {

    private static long countForLock = 0;

    private static long countForMonitor = 0;

    private Object monitor;

    private Lock lock;

    public LockAndMonitorComparer() {
        this.monitor = new Object();
        this.lock = new ReentrantLock();
    }

    public void incByMonitor() {
        synchronized (monitor) {
            countForMonitor++;
        }
    }

    public void incByLock() {
        try {
            lock.lock();
            countForLock++;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final LockAndMonitorComparer lam = new LockAndMonitorComparer();

        List<Thread> list = new ArrayList<Thread>();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    // lam.incByLock();
                    lam.incByMonitor();
                }
            }
        };

        for (int i = 0; i < 1000; i++) {
            list.add(new Thread(task));
        }
        long start = System.currentTimeMillis();

        for (Thread t : list) {
            t.start();
        }

        for (Thread t : list) {
            try {
                t.join();
            } catch (InterruptedException e) { // ignore
            }
        }
        // 7391(lock) 52032(monitor) 100个线程
        // 7719(lock) 59000(monitor) 100个线程
        // 66812(lock) 598531(monitor) 1000个线程
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(countForLock + ":" + countForMonitor);
    }
}
