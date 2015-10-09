package mina;

import java.text.SimpleDateFormat;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * 事件回显服务
 * 
 */
public class SimpleTimeServerHandler extends IoHandlerAdapter {

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        // 服务器端打印出请求的来源
        System.out.println("Request From:" + session.getRemoteAddress());
        // 返给客户端的消息内容：服务器系统当前时间
        session.write("Server Time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
    }

}
