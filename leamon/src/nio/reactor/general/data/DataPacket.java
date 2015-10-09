package nio.reactor.general.data;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

/**
 * 应用层处理的数据包对象
 */
public class DataPacket {

    // 数据包地址。请求时，作为源地址；响应时，作为目的地址。
    private SocketAddress address;

    // 数据包内容
    private ByteBuffer content;

    public DataPacket(SocketAddress address, ByteBuffer content) {
        this.address = address;
        this.content = content;
    }

    public SocketAddress getAddress() {
        return this.address;
    }

    public ByteBuffer getContent() {
        return this.content;
    }
}
