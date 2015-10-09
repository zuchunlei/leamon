package leamon.concurrent.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Java������ʽ��monitor��J.U.C����ʾ��Lock�����ܶԱ�
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
        // 7391(lock) 52032(monitor) 100���߳�
        // 7719(lock) 59000(monitor) 100���߳�
        // 66812(lock) 598531(monitor) 1000���߳�
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(countForLock + ":" + countForMonitor);
    }
}
