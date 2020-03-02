package leamon;

public class ThreadTester {

    private int state = 1;
    private Object lock = new Object();

    private void lockWaiting() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                // DO NOTING
            }
        }
    }

    private void lockNotify() {
        synchronized (lock) {
            lock.notify();
        }
    }

    public void biz() {
        if (1 == state) {
            switch (state) {
            case 1:
                lockWaiting();
                System.out.println("true");

            case 0:
                lockNotify();
                System.out.println("false");
            }
        }

    }

    public void start() {
        for (int i = 0; i < 10; i++) {
            Thread t = new WorkerUnit(this);
            t.start();
        }
    }

    public static void main(String[] args) {
        ThreadTester tester = new ThreadTester();
        tester.start();
        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        tester.state = 0;
        tester.lockNotify();

        System.out.println("finish");
        try {
            Thread.sleep(1000 * 200000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class WorkerUnit extends Thread {

    private ThreadTester t;

    public WorkerUnit(ThreadTester t) {
        this.t = t;
    }

    @Override
    public void run() {
        t.biz();
    }

}
