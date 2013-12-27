package leamon.multicast;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁公平性测试分析
 */
public class ReentrantLockTester {
	/*
	 * 公平锁为非公平锁对cs影响很大。公平锁效率底下，存在大量的上下文切换。
	 */
	private ReentrantLock lock = new ReentrantLock(true);// 公平锁

	public void test(long time) {
		try {
			lock.lock();

			String name = Thread.currentThread().getName();
			System.out.println("线程：" + name + " 进入时间为 " + time);

			// int number = new Random().nextInt(Integer.MAX_VALUE);
			// while (number > 0) {
			// number--;
			// }
			// 以上代码cpu的us使用率为100，cs很少。
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		ReentrantLockTester t = new ReentrantLockTester();
		for (int i = 0; i < 19; i++) {
			new Thread(new TestJob(t), "T" + i).start();
		}
	}
}

class TestJob implements Runnable {
	private ReentrantLockTester tester;

	public TestJob(ReentrantLockTester tester) {
		this.tester = tester;
	}

	@Override
	public void run() {
		while (true) {
			tester.test(System.currentTimeMillis());
		}
	}
}
