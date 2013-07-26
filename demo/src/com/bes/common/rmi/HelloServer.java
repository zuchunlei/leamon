package com.bes.common.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class HelloServer {

	public static void main(String[] args) {
		try {
			// ����Զ�̽ӿ�HelloInterface ���� ���ڰ��ڷ�����ע����� �ýӿ���HelloInterfaceImpl()��ʵ��
			Hello hInterface = new HelloImpl();
			int port = 6666; // ����һ���˿ں�
			// ����һ�����ܶ��ض��˿ڵ��õ�Զ�̶���ע��� ע�������Ҫ�ӿ�һ��ָ���Ķ˿ں�
			LocateRegistry.createRegistry(port);
			// ���� ������Զ�̵�ַ URL��ʽ
			String address = "rmi://192.168.40.6:" + port + "/hello";
			// ��Զ�̵�ַ�ͽӿڶ���
			Naming.bind(address, hInterface);
			// address = "rmi://192.168.0.100:" + port + "/biz";
			// Naming.bind(address,biz);

			// ��������ɹ� �򵯳�������Ϣ
			System.out.println(">>>�����������ɹ�");
			System.out.println(">>>�������ͻ��˽������ӷ���");
		} catch (MalformedURLException e1) {
			System.out.println("��ַ���ִ���!");
			e1.printStackTrace();
		} catch (AlreadyBoundException e2) {
			System.out.println("�ظ�����ͬһ��Զ�̶���!");
			e2.printStackTrace();
		} catch (RemoteException e) {
			System.out.println("����Զ�̶�����ִ���!");
			e.printStackTrace();
		}
	}
}
