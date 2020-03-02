package leamon.interceptor;

/**
 * À¹½ØÆ÷½Ó¿Ú
 */
public interface Interceptor {

    void invoke(final MessagePacket packet, InterceptorChain chian);

}
