package com.bes.ssh.svnkit.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bes.ssh.svnkit.service.RepoParameter;
import com.bes.ssh.svnkit.service.SvnKitService;

/**
 * SVN版本库的扫描任务
 */
public class SvnKitScanTask extends QuartzJobBean {

	private String url;
	private String username;
	private String password;

	private SvnKitService svnKitService;

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSvnKitService(SvnKitService svnKitService) {
		this.svnKitService = svnKitService;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			svnKitService.scan(new RepoParameter(url, username, password));
		} catch (Exception e) {
			// ignore
		}
	}

}
