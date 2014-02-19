package com.bes.ssh.jms.listener;

import java.util.Map;

public class MapMessageListener {

	public void handle(Map<String, Object> message) {
		
		for (Map.Entry<String, Object> entry : message.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}

	}

}
