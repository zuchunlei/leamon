package nio.reactor.general.handler;

import nio.reactor.general.data.DataPacket;
import nio.reactor.general.io.IOHandler;

public class EchoHandler implements IOHandler {

    @Override
    public DataPacket handle(DataPacket packet) {
        return packet;
    }
}
