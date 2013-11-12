package socket.option;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 测试Socket的选项设置
 * 
 * ServerSocket的receive buffer 设置size以后，所有通过ServerSocket accept返回的Socket对象的继承了ServerSocket的receiver buffer大小。
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
