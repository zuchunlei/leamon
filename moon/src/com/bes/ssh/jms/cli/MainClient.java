package com.bes.ssh.jms.cli;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bes.ssh.jms.receiver.MessageReceiver;
import com.bes.ssh.jms.sender.MessageSender;

public class MainClient {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"application-context-jms.xml");

		MessageSender sender = (MessageSender) context.getBean("messageSender");

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("name", "zuchunlei");

		sender.sendMessage(message);

		MessageReceiver receiver = (MessageReceiver) context
				.getBean("messageReceiver");

		for (Map.Entry<String, Object> entry : receiver.receiveMessage()
				.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}

	}
}
