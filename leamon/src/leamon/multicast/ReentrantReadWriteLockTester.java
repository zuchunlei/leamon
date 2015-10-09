package leamon.multicast;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 可重入读写锁测试
 */
public class ReentrantReadWriteLockTester {

    /*
     * 多线程操作读写锁，writeLock与readLock依然是以互斥体方式存在着。 其中满足以下规则：
     * 如果某一线程执行了readLock.lock()，则其它线程执行readLock.lock()不阻塞，writeLock.lock()阻塞。
     * 如果某一线程执行了writeLock.lock()，则其它线程执行readLock.lock()阻塞，writeLock.lock()也阻塞。
     */
    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

        new Thread(new RWTask(readLock, writeLock), "read/write thread ").start();

        for (int i = 0; i < 3; i++) {
            new Thread(new ReadTask(readLock), "read thread " + i).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(new WriteTask(writeLock), "write thread " + i).start();
        }
    }
}

/**
 * 读写任务
 */
class RWTask implements Runnable {
    private ReentrantReadWriteLock.ReadLock readLock;
    private ReentrantReadWriteLock.WriteLock writeLock;

    public RWTask(ReentrantReadWriteLock.ReadLock readLock, ReentrantReadWriteLock.WriteLock writeLock) {
        this.readLock = readLock;
        this.writeLock = writeLock;
    }

    /*
     * 当前线程上下文中，对读写锁的可重入规则：
     * 如果当前线程进行了writeLock.lock()，则可以执行readLock.lock()/writeLock.lock()（读写锁可重入），
     * 如果当前线程进行了readLock.lock()，则可以执行readLock.lock()（读锁可重入），
     * 不可以执行writeLock.lock()（写锁不可以重入）。
     */
    @Override
    public void run() {
        writeLock.lock();
        try {
            readLock.lock();
            // handle data
            readLock.unlock();
        } finally {
            writeLock.unlock();
        }
    }
}

/**
 * 读任务
 */
class ReadTask implements Runnable {
    private ReentrantReadWriteLock.ReadLock readLock;

    public ReadTask(ReentrantReadWriteLock.ReadLock readLock) {
        this.readLock = readLock;
    }

    /**
     * 获得readLock，lock，handle，unlock
     */
    @Override
    public void run() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ": read lock in !");
        } finally {
            readLock.unlock();
        }
    }
}

/**
 * 写任务
 */
class WriteTask implements Runnable {
    private ReentrantReadWriteLock.WriteLock writeLock;

    public WriteTask(ReentrantReadWriteLock.WriteLock writeLock) {
        this.writeLock = writeLock;
    }

    /**
     * 获得writeLock，lock，handle，unlock
     */
    @Override
    public void run() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ": write lock in !");
        } finally {
            writeLock.unlock();
        }
    }
}