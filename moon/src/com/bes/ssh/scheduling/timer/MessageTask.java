package com.bes.ssh.scheduling.timer;

import java.util.TimerTask;

import com.bes.ssh.scheduling.service.MessageService;

/**
 * ��ʱ������
 */
public class MessageTask extends TimerTask {

	private MessageService messageService;

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	public void run() {
		messageService.sendMessage();
	}

}
