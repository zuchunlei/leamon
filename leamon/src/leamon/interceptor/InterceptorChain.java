package leamon.interceptor;

/**
 * ���������ӿ�
 */
public interface InterceptorChain {

	void invoke(MessagePacket packet);

}
