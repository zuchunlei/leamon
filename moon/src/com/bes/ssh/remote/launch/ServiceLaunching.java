package com.bes.ssh.remote.launch;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Զ�̷���������
 */
public class ServiceLaunching {

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext(
				"application-context-remote-exporter.xml");
	}

}
