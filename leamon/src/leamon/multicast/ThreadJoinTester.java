package leamon.multicast;

public class ThreadJoinTester {

	public static void main(String[] args) {

		Thread assist = new AssistThread();
		Thread main = new MainThread(assist);
		assist.start();
		main.start();

	}

	/**
	 * ���̣߳������Ժ�ȴ��ڲ��߳�t�˳���Ȼ���˳���
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
}
