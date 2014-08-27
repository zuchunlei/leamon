package com.bes.ssh.xa.jotm.cli;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bes.ssh.xa.jotm.service.Service;

public class MainClient {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"application-context-xa.xml");

		Service service = (Service) context.getBean("service");

		service.add("zuchunlei", "zuchunlei");

	}
}
