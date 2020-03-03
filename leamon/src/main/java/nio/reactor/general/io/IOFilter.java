package nio.reactor.general.io;

import nio.reactor.general.data.DataPacket;

/**
 * IOFilter�ӿ�
 */
public interface IOFilter {

    /**
     * �������ݶ���ɣ��ں�̬----->�û�̬ ����ɣ�
     * 
     * @param packet
     * @param chain
     */
    void onReadComplete(DataPacket packet, IOFilterChain chain);

    /**
     * Ӧ������д�������û�̬----->�ں�̬ ��������
     * 
     * @param packet
     * @param chain
     * @return
     */
    DataPacket onWriteReady(DataPacket packet, IOFilterChain chain);
}
