package leamon.multicast;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * �������д������
 */
public class ReentrantReadWriteLockTester {

	/*
	 * ���̲߳�����д����writeLock��readLock��Ȼ���Ի����巽ʽ�����š� �����������¹���
	 * ���ĳһ�߳�ִ����readLock.lock()���������߳�ִ��readLock.lock()��������writeLock.lock()������
	 * ���ĳһ�߳�ִ����writeLock.lock()���������߳�ִ��readLock.lock()������writeLock.lock()Ҳ������
	 */
	public static void main(String[] args) {
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
		ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
		ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

		for (int i = 0; i < 3; i++) {
			new Thread(new ReadTask(readLock), "read thread " + i).start();
		}

		for (int i = 0; i < 3; i++) {
			new Thread(new WriteTask(writeLock), "write thread " + i).start();
		}
	}
}

/**
 * ������
 */
class ReadTask implements Runnable {
	private ReentrantReadWriteLock.ReadLock readLock;

	public ReadTask(ReentrantReadWriteLock.ReadLock readLock) {
		this.readLock = readLock;
	}

	/**
	 * ���readLock��lock��handle��unlock
	 */
	@Override
	public void run() {
		readLock.lock();
		try {
			System.out.println(Thread.currentThread().getName()
					+ ": read lock in !");
		} finally {
			readLock.unlock();
		}
	}
}

/**
 * д����
 */
class WriteTask implements Runnable {
	private ReentrantReadWriteLock.WriteLock writeLock;

	public WriteTask(ReentrantReadWriteLock.WriteLock writeLock) {
		this.writeLock = writeLock;
	}

	/**
	 * ���writeLock��lock��handle��unlock
	 */
	@Override
	public void run() {
		writeLock.lock();
		try {
			System.out.println(Thread.currentThread().getName()
					+ ": write lock in !");
		} finally {
			writeLock.unlock();
		}
	}
}