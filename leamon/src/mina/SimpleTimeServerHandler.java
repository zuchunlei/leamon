package mina;

import java.text.SimpleDateFormat;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * �¼����Է���
 * 
 */
public class SimpleTimeServerHandler extends IoHandlerAdapter {

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        // �������˴�ӡ���������Դ
        System.out.println("Request From:" + session.getRemoteAddress());
        // �����ͻ��˵���Ϣ���ݣ�������ϵͳ��ǰʱ��
        session.write("Server Time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
    }

}
