package com.bes.ssh.jms.sender;

import java.util.Map;

public interface MessageSender {

	public void sendMessage(Map<String,Object> message);
	
}
