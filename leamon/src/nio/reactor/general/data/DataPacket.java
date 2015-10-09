package nio.reactor.general.data;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

/**
 * Ӧ�ò㴦������ݰ�����
 */
public class DataPacket {

    // ���ݰ���ַ������ʱ����ΪԴ��ַ����Ӧʱ����ΪĿ�ĵ�ַ��
    private SocketAddress address;

    // ���ݰ�����
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
