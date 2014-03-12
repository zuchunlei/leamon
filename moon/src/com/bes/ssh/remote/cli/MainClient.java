package com.bes.ssh.remote.cli;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bes.ssh.remote.service.WishService;

public class MainClient {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"application-context-remote-cli.xml");

		// 获得业务对象，该对象为Spring容器装配过的代理对象，内部织入了网络通信的细节信息
		WishService service = (WishService) context.getBean("wishRmiService");
		// 调用业务代理对象，执行rpc调用，发起网络io
		System.out.println(service.wish("zuchunlei"));
	}

}
