package leamon.multicast;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 多线程更新竞态数据，使用读写锁保证数据的同步性。
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
		// 生成一个随机数，执行线程进行sleep操作
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
	 * 具体操作任务
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
	 * 健康恢复线程，当前线程由report操作驱动
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
	 * 测试主方法
	 */
	public static void main(String[] args) {
		ConnectionManager manager = new ConnectionManager();
		
		// 等待locateServer线程的正确启动。
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
