package com.bes.ssh.jms.receiver;

import java.util.Map;

public interface MessageReceiver {

	public Map<String, Object> receiveMessage();

}
