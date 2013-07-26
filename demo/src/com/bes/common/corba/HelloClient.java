package com.bes.common.corba;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

import test.Hello;
import test.HelloHelper;

public class HelloClient {

	public static void main(String[] args) {
		try {
			ORB orb = ORB.init(args, null);
			System.out.println("ORB init... ");
			// 获取一个命名上下文
			org.omg.CORBA.Object objRef = orb
					.resolve_initial_references("NameService");
			NamingContext ncRef = NamingContextHelper.narrow(objRef);
			NameComponent nc = new NameComponent("hello", "");
			NameComponent[] path = { nc };
			Hello hello = HelloHelper.narrow(ncRef.resolve(path));
			System.out.println("Corba 客户端开始调用Corba服务器上的sayHello方法");
			System.out.println("欢迎" + hello.sayHello("zcl"));
			System.out.println("调用完毕");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
