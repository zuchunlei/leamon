package leamon.interceptor;

/**
 * ������������󣬼̳иö������޷��ı����������ĵ��ô���
 */
public class InterceptorAdapter implements Interceptor {

    @Override
    public final void invoke(MessagePacket packet, InterceptorChain chian) {
        doInvoke(packet);
        chian.invoke(packet);
    }

    /**
     * override by subclass
     */
    protected void doInvoke(MessagePacket packet) {
    }
}
