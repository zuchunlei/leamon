package nio.reactor.echo.main;

import nio.reactor.echo.server.Server;

/**
 * ·şÎñ²âÊÔÀà
 */
public class Main {

	public static void main(String[] args) {
		String host = "192.168.1.35";
		int port = 12345;

		Server server = new Server(host, port);
		server.start();
	}
}
