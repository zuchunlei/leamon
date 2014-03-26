package com.bes.ssh.scheduling.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bes.ssh.scheduling.service.MessageService;

/**
 * 定时器任务
 */
public class MessageJob extends QuartzJobBean {

	private MessageService messageService;

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		messageService.sendMessage();
	}

}
