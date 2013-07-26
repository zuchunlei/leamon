package com.bes.common.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class HelloServer {

	public static void main(String[] args) {
		try {
			// 定义远程接口HelloInterface 对象 用于绑定在服务器注册表上 该接口由HelloInterfaceImpl()类实现
			Hello hInterface = new HelloImpl();
			int port = 6666; // 定义一个端口号
			// 创建一个接受对特定端口调用的远程对象注册表 注册表上需要接口一个指定的端口号
			LocateRegistry.createRegistry(port);
			// 定义 服务器远程地址 URL格式
			String address = "rmi://192.168.40.6:" + port + "/hello";
			// 绑定远程地址和接口对象
			Naming.bind(address, hInterface);
			// address = "rmi://192.168.0.100:" + port + "/biz";
			// Naming.bind(address,biz);

			// 如果启动成功 则弹出如下信息
			System.out.println(">>>服务器启动成功");
			System.out.println(">>>请启动客户端进行连接访问");
		} catch (MalformedURLException e1) {
			System.out.println("地址出现错误!");
			e1.printStackTrace();
		} catch (AlreadyBoundException e2) {
			System.out.println("重复绑定了同一个远程对象!");
			e2.printStackTrace();
		} catch (RemoteException e) {
			System.out.println("创建远程对象出现错误!");
			e.printStackTrace();
		}
	}
}
