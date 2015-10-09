package leamon.multicast.threadpool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 线程池组件
 */
public class ThreadPool {

    private static final int DEFAULT_INIT_COUNT = 5;
    private static final int DEFAULT_MAX_COUNR = 15;
    private static final long DEFAULT_TUNE_INTERVAL = 1000;

    private int initCount;
    private int maxCount;
    private long tuneInterval;

    private volatile boolean stop;
    private TaskQueue queue;
    private List<Worker> pool;
    private Thread tune;

    public ThreadPool(int initCount, int maxCount, long tuneInterval) {
        this.initCount = initCount;
        this.maxCount = maxCount;
        this.tuneInterval = tuneInterval;
    }

    public ThreadPool() {
        this(DEFAULT_INIT_COUNT, DEFAULT_MAX_COUNR, DEFAULT_TUNE_INTERVAL);
    }

    public void init() {
        queue = new TaskQueue();
        pool = new ArrayList<Worker>(maxCount);
        tune = new Thread() {
            @Override
            public void run() {
                while (!stop) {
                    int size = pool.size();
                    if (size <= maxCount && queue.size() > 2 * size) {
                        Worker worker = new Worker();
                        pool.add(worker);
                        worker.start();
                    }

                    if (size > initCount && queue.size() * 2 < size) {
                        for (int i = size - initCount; i > 0; i--) {
                            Worker worker = pool.remove(i);
                            worker.finish();
                        }
                    }

                    try {
                        Thread.sleep(tuneInterval);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                }
            }
        };

        for (int i = 0; i < initCount; i++) {
            pool.add(new Worker());
        }

    }

    public void startup() {
        for (Worker worker : pool) {
            worker.start();
        }
        tune.start();
    }

    public void shutdown() {
        stop = true;
        for (Worker worker : pool) {
            worker.finish();
        }
    }

    public void addTask(Runnable task) {
        queue.addTask(task);
    }

    class Worker extends Thread {
        private volatile boolean finished;

        @Override
        public void run() {
            while (!finished) {
                try {
                    Runnable task = queue.getTask();
                    task.run();
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        }

        public void finish() {
            finished = true;
            interrupt();
        }

    }

    static class TaskQueue {
        private Object lock = new Object();
        private Queue<Runnable> q = new LinkedList<Runnable>();

        void addTask(Runnable task) {
            synchronized (lock) {
                q.add(task);
                lock.notify();
            }
        }

        Runnable getTask() throws InterruptedException {
            synchronized (lock) {
                while (q.isEmpty()) {
                    lock.wait();
                }
                return q.poll();
            }
        }

        int size() {
            return q.size();
        }

    }

}
