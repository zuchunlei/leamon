package leamon.io;

public class IoClient {

	public static void main(String[] args) {
		IoService service = new BasicIoService(new DefaultIoHandler());
		for (int i = 0; i < 12; i++) {
			service.addFilter(new DefaultIoFilter());
		}

		service.read();
	}
}
