package leamon;

public class ShutdownHookTester {

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					System.out.println(i);
				}
			}
		});
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000 * 10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.exit(0);
			}
		}.start();

		try {
			Thread.sleep(1000 * 60 * 60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
