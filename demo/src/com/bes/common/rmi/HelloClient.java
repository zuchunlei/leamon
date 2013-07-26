package com.bes.common.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class HelloClient {

	public static void main(String[] args) {
		// ����һ���˿ں� �ö˿ںű�����������Ķ˿ں���ͬ
		int port = 6666;
		// ͬ������һ��Զ�̵�ַ �õ�ַΪ��������Զ�̵�ַ ���� ��������ĵ�ַ��һ����
		String address = "rmi://192.168.40.6:" + port + "/hello";
		// ��RMIע��������� ����ΪHelloInterface�ĵ�ַ ����������ַ
		try {
			Hello hInterface = (Hello) Naming.lookup(address);
			// һ���ͻ����ҵ��÷�������ַ �� ��������
			System.out.println("<<<�ͻ��˷��ʳɹ�!");
			// �ͻ��� Client ���� Զ�̽ӿ���� sayHello ���� ����ӡ����
			System.out.println(hInterface.sayHello("SoFlash"));
			
		} catch (MalformedURLException e) {
			System.out.println("����ĵ�ַ!");
			e.printStackTrace();
		} catch (RemoteException e) {
			System.out.println("����Զ�̶������!");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println("δ�󶨵�Զ�̶���!");
			e.printStackTrace();
		}
	}
}
