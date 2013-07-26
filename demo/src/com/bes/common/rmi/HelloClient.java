package com.bes.common.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class HelloClient {

	public static void main(String[] args) {
		// 定义一个端口号 该端口号必须与服务器的端口号相同
		int port = 6666;
		// 同样定义一个远程地址 该地址为服务器的远程地址 所以 与服务器的地址是一样的
		String address = "rmi://192.168.40.6:" + port + "/hello";
		// 在RMI注册表上需找 对象为HelloInterface的地址 即服务器地址
		try {
			Hello hInterface = (Hello) Naming.lookup(address);
			// 一旦客户端找到该服务器地址 则 进行连接
			System.out.println("<<<客户端访问成功!");
			// 客户端 Client 调用 远程接口里的 sayHello 方法 并打印出来
			System.out.println(hInterface.sayHello("SoFlash"));
			
		} catch (MalformedURLException e) {
			System.out.println("错误的地址!");
			e.printStackTrace();
		} catch (RemoteException e) {
			System.out.println("创建远程对象出错!");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println("未绑定的远程对象!");
			e.printStackTrace();
		}
	}
}
