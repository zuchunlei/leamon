package com.bes.ssh.svnkit.service;

import java.io.Serializable;

/**
 * 连接SVN Repository的参数信息的封装
 */
public class RepoParameter implements Serializable {

	private static final long serialVersionUID = 254460885794086022L;

	private String url;

	private String username;

	private String password;

	public RepoParameter() {
	}

	public RepoParameter(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
