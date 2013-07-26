package com.bes.common.corba;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

public class HelloServer {

	public static void main(String[] args) {
		try {
			ORB orb = ORB.init(args, null);// ��ʼ��ORB
			System.out.println("ORB init... ");
			// ����һ������ʵ��,����ORB����ע��
			HelloImpl helloImpl = new HelloImpl();
			orb.connect(helloImpl);
			System.out.println("��HelloImplע�ᵽorb");
			// ��ȡһ������������
			org.omg.CORBA.Object objRef = orb
					.resolve_initial_references("NameService");
			NamingContext ncRef = NamingContextHelper.narrow(objRef);

			// �������еĶ�������
			NameComponent nc = new NameComponent("hello", "");
			NameComponent[] path = { nc };
			ncRef.rebind(path, helloImpl);

			Object sync = new Object();
			synchronized (sync) {
				sync.wait();
				System.out.println("�ȴ�Corba�ͻ��˵ĵ���");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
