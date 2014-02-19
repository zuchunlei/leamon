package com.bes.ssh.jms.sender.impl;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

/**
 * MapMessage 信息转化器
 */
public class MapMessageConverter implements MessageConverter {

	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {
		
		@SuppressWarnings("unchecked")
		Map<String, Object> message = (Map<String, Object>) object;
		
		MapMessage mapMessgae = session.createMapMessage();

		for (Map.Entry<String, Object> entry : message.entrySet()) {
			mapMessgae.setObject(entry.getKey(), entry.getValue());
		}

		return mapMessgae;
	}

	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		return null;
	}

}
