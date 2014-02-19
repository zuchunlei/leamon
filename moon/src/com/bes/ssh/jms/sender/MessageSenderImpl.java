package com.bes.ssh.jms.sender;

import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * JMS 消息发送者
 */
public class MessageSenderImpl implements MessageSender {

	// JMS目标
	private Destination destination;

	// JMS模板
	private JmsTemplate jmsTemplate;

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void sendMessage(final Map<String, Object> message) {
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessgae = session.createMapMessage();

				for (Map.Entry<String, Object> entry : message.entrySet()) {
					mapMessgae.setObject(entry.getKey(), entry.getValue());
				}

				return mapMessgae;
			}
		});
	}
}
