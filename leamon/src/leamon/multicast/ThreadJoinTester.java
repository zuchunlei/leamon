package leamon.multicast;

public class ThreadJoinTester {

	public static void main(String[] args) {

		Thread general = new GeneralThread();
		Thread main = new MainThread(general);
		general.start();
		main.start();

	}

	/**
	 * 主线程，启动以后等待内部线程t退出，然后退出。
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
	 * 线程，启动时在该线程对象进行wait等待
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
	 * 线程对象，启动以后退出。
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
