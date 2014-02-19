package com.bes.ssh.jms.sender.impl;

import java.util.Map;

import org.springframework.jms.core.JmsTemplate;

import com.bes.ssh.jms.sender.MessageSender;

/**
 * JMS 消息发送者
 */
public class MessageSenderImpl implements MessageSender {

	// JMS模板
	private JmsTemplate jmsTemplate;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void sendMessage(final Map<String, Object> message) {
		jmsTemplate.convertAndSend(message);
	}
}
