package nio.reactor.general.io;

import nio.reactor.general.data.DataPacket;

/**
 * IO���ݹ�����
 */
public interface IOFilterChain {

    /**
     * �������ݶ���ɣ��ں�̬----->�û�̬ ����ɣ�
     * 
     * @param packet
     */
    void onReadComplete(DataPacket packet);

    /**
     * Ӧ������д�������û�̬----->�ں�̬ ��������
     * 
     * @param packet
     * @return
     */
    DataPacket onWriteReady(DataPacket packet);

}
