package com.bes.ssh.exam.cli;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bes.ssh.exam.service.Service;

/**
 * ������
 */
public class MainClient {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"application-context-exam.xml");

		// ���ҵ����󣬸ö���ΪSpring����װ����Ĵ�������ڲ�֯����Ӧ�÷��������ʩ���磺����ȡ�
		Service service = (Service) context.getBean("service");

		// ����ҵ�񷽷������ҵ�񷽷�ִ�����׳�����ʱ�쳣��������ع���
		service.add("zcl", "zcl");
	}
}
