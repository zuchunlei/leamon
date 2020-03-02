package leamon.interceptor.impl;

import leamon.interceptor.Interceptor;
import leamon.interceptor.InterceptorChain;
import leamon.interceptor.MessagePacket;

/**
 * 容量限制
 */
public class CapacityInterceptor implements Interceptor {

    /**
     * 容量限制处理，如果当前数据包的body字节数不满足容量限制规则，则在本地缓存。<br/>
     * 知道达到限制规则以后，在整体处理。
     * 
     * @param packet
     * @param chian
     */
    @Override
    public void invoke(MessagePacket packet, InterceptorChain chian) {
        // 伪代码
        int capacity = 1024 * 1024;// 1M
        if (capacity - packet.getBody().length > 100) {// 限制阈值
            // 缓存处理
            return;
        }
        chian.invoke(packet);
    }
}
