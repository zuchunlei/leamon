package com.bes.ssh.jms.sender.impl;

import java.util.Map;

import org.springframework.jms.core.JmsTemplate;

import com.bes.ssh.jms.sender.MessageSender;

/**
 * JMS ��Ϣ������
 */
public class MessageSenderImpl implements MessageSender {

	// JMSģ��
	private JmsTemplate jmsTemplate;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void sendMessage(final Map<String, Object> message) {
		jmsTemplate.convertAndSend(message);
	}
}
