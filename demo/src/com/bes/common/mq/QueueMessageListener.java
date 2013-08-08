package com.bes.common.mq;

import javax.jms.Message;
import javax.jms.MessageListener;

public class QueueMessageListener implements MessageListener {

	@Override
	public void onMessage(Message paramMessage) {
		System.out.println(paramMessage);
	}

}
