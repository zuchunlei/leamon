package leamon.io;

public class DefaultIoHandler implements IoHandler {

	@Override
	public void handle() {
		System.out.println("this is a handler invoke!");
	}
}
