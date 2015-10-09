package leamon.interceptor;

/**
 * 拦截器适配对象，继承该对象，则无法改变拦截器链的调用次序。
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
