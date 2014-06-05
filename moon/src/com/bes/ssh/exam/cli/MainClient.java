package com.bes.ssh.exam.cli;

import java.util.List;

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
		final Service service = (Service) context.getBean("service");

		new Thread(new Runnable() {
			public void run() {
				// ����ҵ�񷽷������ҵ�񷽷�ִ�����׳�����ʱ�쳣��������ع���
				service.add("zuchunlei", "zuchunlei");
			}
		}, "Add Thread").start();

		new Thread(new Runnable() {
			public void run() {
				// ����ҵ�񷽷������ҵ�񷽷�ִ�����׳�����ʱ�쳣��������ع���
				List<? extends Object> list = service.get();
				System.out.println(list.size());
			}
		}, "Get Thread").start();
	}
}
