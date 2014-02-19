package com.bes.ssh.jms.sender.impl;

import java.util.Map;

import org.springframework.jms.core.support.JmsGatewaySupport;

import com.bes.ssh.jms.sender.MessageSender;

/**
 * JMS ��Ϣ������
 */
public class MessageSenderImpl extends JmsGatewaySupport implements
		MessageSender {

	public void sendMessage(final Map<String, Object> message) {
		getJmsTemplate().convertAndSend(message);
	}
}
