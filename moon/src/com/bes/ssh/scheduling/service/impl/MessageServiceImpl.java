package com.bes.ssh.scheduling.service.impl;

import com.bes.ssh.scheduling.service.MessageService;

/**
 * ��Ϣ����ʵ�֣���ָ����Ŀ�귢����Ϣ
 */
public class MessageServiceImpl implements MessageService {

	private String message;
	private String target;

	public void setMessage(String message) {
		this.message = message;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void sendMessage() {
		System.out.println("take [" + target + "] send message :" + message);
	}

}
