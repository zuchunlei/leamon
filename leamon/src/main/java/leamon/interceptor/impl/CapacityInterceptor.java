package leamon.interceptor.impl;

import leamon.interceptor.Interceptor;
import leamon.interceptor.InterceptorChain;
import leamon.interceptor.MessagePacket;

/**
 * ��������
 */
public class CapacityInterceptor implements Interceptor {

    /**
     * �������ƴ��������ǰ���ݰ���body�ֽ����������������ƹ������ڱ��ػ��档<br/>
     * ֪���ﵽ���ƹ����Ժ������崦��
     * 
     * @param packet
     * @param chian
     */
    @Override
    public void invoke(MessagePacket packet, InterceptorChain chian) {
        // α����
        int capacity = 1024 * 1024;// 1M
        if (capacity - packet.getBody().length > 100) {// ������ֵ
            // ���洦��
            return;
        }
        chian.invoke(packet);
    }
}
