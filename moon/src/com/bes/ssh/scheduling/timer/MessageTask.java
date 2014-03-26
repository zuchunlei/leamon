package com.bes.ssh.scheduling.timer;

import java.util.TimerTask;

import com.bes.ssh.scheduling.service.MessageService;

/**
 * 定时器任务
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
