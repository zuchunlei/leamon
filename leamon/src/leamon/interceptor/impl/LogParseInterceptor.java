package leamon.interceptor.impl;

import leamon.interceptor.InterceptorAdapter;
import leamon.interceptor.MessagePacket;

/**
 * 日志解析
 */
public class LogParseInterceptor extends InterceptorAdapter {

    /**
     * 对数据包进行日志解析处理
     * 
     * @param packet
     */
    @Override
    protected void doInvoke(MessagePacket packet) {
    }
}
