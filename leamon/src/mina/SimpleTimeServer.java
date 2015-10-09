package mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class SimpleTimeServer {

    public static void main(String[] args) throws IOException {
        // ����һ�������������տͻ�������
        IoAcceptor acceptor = new NioSocketAcceptor();

        // Don't know how to handle message of type 'java.lang.String'. Are you
        // missing a protocol encoder?
        // �����������ʹ�÷��������Դ���string���͵���Ϣ
        acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

        // ���÷������˵Ĵ������
        acceptor.setHandler(new SimpleTimeServerHandler());
        acceptor.bind(new InetSocketAddress("localhost", 60001));
    }

}
