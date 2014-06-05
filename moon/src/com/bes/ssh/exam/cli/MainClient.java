package com.bes.ssh.exam.cli;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bes.ssh.exam.service.Service;

/**
 * 测试类
 */
public class MainClient {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"application-context-exam.xml");

		// 获得业务对象，该对象为Spring容器装配过的代理对象，内部织入了应用服务基础设施，如：事务等。
		final Service service = (Service) context.getBean("service");

		new Thread(new Runnable() {
			public void run() {
				// 调用业务方法，如果业务方法执行中抛出运行时异常，则事务回滚。
				service.add("zuchunlei", "zuchunlei");
			}
		}, "Add Thread").start();

		new Thread(new Runnable() {
			public void run() {
				// 调用业务方法，如果业务方法执行中抛出运行时异常，则事务回滚。
				List<? extends Object> list = service.get();
				System.out.println(list.size());
			}
		}, "Get Thread").start();
	}
}
