package com.bes.ssh.jms.receiver.impl;

import java.util.Map;

import org.springframework.jms.core.support.JmsGatewaySupport;

import com.bes.ssh.jms.receiver.MessageReceiver;

public class MessageReceiverImpl extends JmsGatewaySupport implements
		MessageReceiver {

	@SuppressWarnings("unchecked")
	public Map<String, Object> receiveMessage() {
		return (Map<String, Object>) getJmsTemplate().receiveAndConvert();
	}

}
