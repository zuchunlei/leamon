package leamon.multicast;

/**
 * 测试Object.notify()与Object.notifyAll()的区别
 */
public class NotifyTester {

    private Object lock = new Object();// Object Monitor

    public void wakeup() {
        synchronized (lock) {
            lock.notifyAll();
            // lock.notify();
        }
    }

    class Task implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    // ignore
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        NotifyTester tester = new NotifyTester();

        for (int i = 0; i < 3; i++) {
            new Thread(tester.new Task(), "tester thread " + i).start();
        }

        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            // ignore
        }

        tester.wakeup();
    }

}
