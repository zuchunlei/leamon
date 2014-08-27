package com.bes.ssh.xa.cli;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bes.ssh.xa.service.Service;

public class MainClient {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"application-context-xa.xml");

		Service service = (Service) context.getBean("service");

		service.add("zuchunlei", "zuchunlei");

	}
}
