package leamon.interceptor;

/**
 * �������ӿ�
 */
public interface Interceptor {

    void invoke(final MessagePacket packet, InterceptorChain chian);

}
