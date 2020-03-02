package nio.reactor.general.main;

import nio.reactor.general.server.Server;

/**
 * ·şÎñ²âÊÔÀà
 */
public class Main {

    public static void main(String[] args) {
        String host = "192.168.1.35";
        int port = 12345;

        Server server = new Server(host, port);
        // server.setHandler(new EchoHandler());
        server.start();
    }
}
