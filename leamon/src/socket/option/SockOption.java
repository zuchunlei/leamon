package socket.option;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * ����Socket��ѡ������
 * 
 * ServerSocket��receive buffer ����size�Ժ�����ͨ��ServerSocket accept���ص�Socket����ļ̳���ServerSocket��receiver buffer��С��
 */
public class SockOption {

	public static void main(String[] args) throws Exception {
		ServerSocket servSock = new ServerSocket(5000);
		servSock.setReceiveBufferSize(4096);
		Socket sock = servSock.accept();
		System.out.println(sock.getReceiveBufferSize());
		
		sock.setReceiveBufferSize(8192);
		System.out.println(sock.getReceiveBufferSize());
	}

}
