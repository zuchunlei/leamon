package nio.reactor.general.io;

import nio.reactor.general.data.DataPacket;

/**
 * IO���ݴ�����
 */
public interface IOHandler {

    /**
     * Ӧ�ò����ݴ���
     * 
     * @param packet
     * @return
     */
    DataPacket handle(DataPacket packet);

}
