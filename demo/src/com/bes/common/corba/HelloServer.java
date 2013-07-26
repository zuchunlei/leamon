package com.bes.common.corba;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

public class HelloServer {

	public static void main(String[] args) {
		try {
			ORB orb = ORB.init(args, null);// 初始化ORB
			System.out.println("ORB init... ");
			// 创建一个对象实例,并向ORB进行注册
			HelloImpl helloImpl = new HelloImpl();
			orb.connect(helloImpl);
			System.out.println("将HelloImpl注册到orb");
			// 获取一个命名上下文
			org.omg.CORBA.Object objRef = orb
					.resolve_initial_references("NameService");
			NamingContext ncRef = NamingContextHelper.narrow(objRef);

			// 绑定命名中的对象引用
			NameComponent nc = new NameComponent("hello", "");
			NameComponent[] path = { nc };
			ncRef.rebind(path, helloImpl);

			Object sync = new Object();
			synchronized (sync) {
				sync.wait();
				System.out.println("等待Corba客户端的调用");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
