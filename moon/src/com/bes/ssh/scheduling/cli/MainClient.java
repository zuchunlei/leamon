package com.bes.ssh.scheduling.cli;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClient {

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext(
				"application-context-schedule-quartz.xml");
	}

}
