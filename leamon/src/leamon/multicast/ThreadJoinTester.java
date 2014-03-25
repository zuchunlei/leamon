package leamon.multicast;

public class ThreadJoinTester {

	public static void main(String[] args) {

		Thread assist = new AssistThread();
		Thread main = new MainThread(assist);
		assist.start();
		main.start();

	}

	/**
	 * 主线程，启动以后等待内部线程t退出，然后退出。
	 */
	static class MainThread extends Thread {
		private Thread t;

		public MainThread(Thread t) {
			this.t = t;
		}

		public void run() {
			try {
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
}
