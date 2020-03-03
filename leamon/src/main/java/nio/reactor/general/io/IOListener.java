package nio.reactor.general.io;

import nio.reactor.general.server.IOSession;

/**
 * IO�Ĵ������ӿڣ�ʵ�ָýӿڵĶ���ʵ������Ҫ����IO�����е�״̬����ֻ��Ϊ���߷���ʹ�á�<br/>
 * �����IO�����е�״̬��Ϣ����IOSession����ά����
 */
public interface IOListener {

    /**
     * session�ĳ�ʼ��������ʹ��setAttribute��������IO��������ص���Դ�����͵���ByteBuffer��
     * 
     * @param session
     */
    void attach(IOSession session);

    /**
     * �����������ݴ��ں�̬���û�̬����
     * 
     * @param session
     */
    void read(IOSession session);

    /**
     * д���������ݴ��û�̬���ں�̬����
     * 
     * @param session
     */
    void write(IOSession session);

    /**
     * ע��session��Դ��
     * 
     * @param session
     */
    void detach(IOSession session);

}
