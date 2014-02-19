package com.bes.ssh.jms.listener;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class MapMessageListener implements MessageListener {

	public void onMessage(Message message) {
		try {
			MapMessage mapMessage = (MapMessage) message;

			Map<String, Object> map = new HashMap<String, Object>();
			@SuppressWarnings("rawtypes")
			Enumeration en = mapMessage.getMapNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				map.put(key, mapMessage.getObject(key));
			}

			handle(map);

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void handle(Map<String, Object> map) {
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}

}
