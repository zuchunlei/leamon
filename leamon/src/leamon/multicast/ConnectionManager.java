package leamon.multicast;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ���̸߳��¾�̬���ݣ�ʹ�ö�д����֤���ݵ�ͬ���ԡ�
 */
public class ConnectionManager {

	private Random random;
	private LocateThread thread;
	private ReentrantReadWriteLock lock;

	private volatile boolean running;
	private volatile boolean needLocate;

	public ConnectionManager() {
		this.random = new Random();
		this.lock = new ReentrantReadWriteLock();
		this.running = true;
		this.needLocate = false;

		this.thread = new LocateThread();
		thread.start();
	}

	public void setNeedLocate(boolean needLocate) {
		this.needLocate = needLocate;
	}

	public void report() {
		// ����һ���������ִ���߳̽���sleep����
		int time = random.nextInt(1000 * 10);

		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// ignore
		}

		wakeup();
	}

	public void wakeup() {
		try {
			lock.readLock().lock();
			if (needLocate) {
				lock.readLock().unlock();
				try {
					lock.writeLock().lock();
					if (needLocate) {
						synchronized (thread) {
							thread.notify();
							try {
								thread.wait();
							} catch (InterruptedException e) {
								// ignore
							}
						}
					}
				} finally {
					lock.readLock().lock();
					lock.writeLock().unlock();
				}
			}
		} finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * �����������
	 */
	static class ReportTask implements Runnable {
		private ConnectionManager manager;

		public ReportTask(ConnectionManager manager) {
			this.manager = manager;
		}

		@Override
		public void run() {
			manager.report();
		}

	}

	/**
	 * �����ָ��̣߳���ǰ�߳���report��������
	 */
	class LocateThread extends Thread {

		@Override
		public void run() {
			while (running) {
				locateServer();
			}
		}

		synchronized void locateServer() {
			System.out.println("locate server sucess!");
			needLocate = false;

			this.notify();

			try {
				this.wait();
			} catch (InterruptedException e) {
				// ignore
			}
		}

	}

	/**
	 * ����������
	 */
	public static void main(String[] args) {
		ConnectionManager manager = new ConnectionManager();
		
		// �ȴ�locateServer�̵߳���ȷ������
		try {
			Thread.sleep(1000 * 10);
		} catch (InterruptedException e) {
		}

		manager.setNeedLocate(true);

		for (int i = 0; i < 10; i++) {
			new Thread(new ReportTask(manager), "Corba thread " + i).start();
		}

	}
}
