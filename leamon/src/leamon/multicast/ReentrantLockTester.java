package leamon.multicast;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ����������ƽ�Բ��Է���
 */
public class ReentrantLockTester {
	/*
	 * ��ƽ��Ϊ�ǹ�ƽ����csӰ��ܴ󡣹�ƽ��Ч�ʵ��£����ڴ������������л���
	 */
	private ReentrantLock lock = new ReentrantLock(true);// ��ƽ��

	public void test(long time) {
		try {
			lock.lock();

			String name = Thread.currentThread().getName();
			System.out.println("�̣߳�" + name + " ����ʱ��Ϊ " + time);

			// int number = new Random().nextInt(Integer.MAX_VALUE);
			// while (number > 0) {
			// number--;
			// }
			// ���ϴ���cpu��usʹ����Ϊ100��cs���١�
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
