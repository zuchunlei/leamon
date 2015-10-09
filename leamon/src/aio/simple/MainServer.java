package aio.simple;

import aio.simple.echo.EchoServer;

public class MainServer {

    public static void main(String[] args) {
        EchoServer server = new EchoServer(12345);
        server.listen();
    }
}
