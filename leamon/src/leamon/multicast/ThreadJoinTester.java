package leamon.multicast;

public class ThreadJoinTester {

	public static void main(String[] args) {

		Thread general = new GeneralThread();
		Thread main = new MainThread(general);
		general.start();
		main.start();

	}

	/**
	 * ���̣߳������Ժ�ȴ��ڲ��߳�t�˳���Ȼ���˳���
	 */
	static class MainThread extends Thread {
		private Thread t;

		public MainThread(Thread t) {
			this.setName("main thread");
			this.t = t;
		}

		public void run() {
			try {
				Thread.sleep(10 * 1000);
				t.interrupt();
				t.join();
			} catch (InterruptedException e) {
				// ignore
			}
		}
	}

	/**
	 * �̣߳�����ʱ�ڸ��̶߳������wait�ȴ�
	 */
	static class AssistThread extends Thread {

		public void run() {
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					// ignore
				}
			}
		}
	}

	/**
	 * �̶߳��������Ժ��˳���
	 */
	static class GeneralThread extends Thread {

		public GeneralThread() {
			this.setName("general thread");
		}

		@Override
		public void run() {
			try {
				Thread.sleep(6 * 1000);
			} catch (InterruptedException e) {
				// ignore
			}
		}
	}

}
