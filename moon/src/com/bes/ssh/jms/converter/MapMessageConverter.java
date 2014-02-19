package com.bes.ssh.jms.converter;

import java.util.Enumeration;
import java.util.HashMap;
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

	@SuppressWarnings("unchecked")
	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {

		Map<String, Object> message = (Map<String, Object>) object;

		MapMessage mapMessgae = session.createMapMessage();

		for (Map.Entry<String, Object> entry : message.entrySet()) {
			mapMessgae.setObject(entry.getKey(), entry.getValue());
		}

		return mapMessgae;
	}

	@SuppressWarnings("rawtypes")
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {

		MapMessage mapMessage = (MapMessage) message;

		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration en = mapMessage.getMapNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			map.put(key, mapMessage.getObject(key));
		}

		return map;
	}

}
