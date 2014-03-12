package com.bes.ssh.remote.cli;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bes.ssh.remote.service.WishService;

public class MainClient {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"application-context-remote-cli.xml");

		// ���ҵ����󣬸ö���ΪSpring����װ����Ĵ�������ڲ�֯��������ͨ�ŵ�ϸ����Ϣ
		WishService service = (WishService) context.getBean("wishRmiService");
		// ����ҵ��������ִ��rpc���ã���������io
		System.out.println(service.wish("zuchunlei"));
	}

}
