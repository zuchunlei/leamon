package com.bes.common.svn.kit;

import java.io.Serializable;

public class LogMessage implements Serializable {
	private static final long serialVersionUID = -1075904923133441621L;

	private long id;
	private String log;
	private String author;
	private String date;
	private long revision;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getId() {
		return id;
	}

	public String getLog() {
		return log;
	}

	public String getAuthor() {
		return author;
	}

	public String getDate() {
		return date;
	}

	public long getRevision() {
		return revision;
	}

	public LogMessage(String log, String author, String date, long revision) {
		this.log = log;
		this.author = author;
		this.date = date;
		this.revision = revision;
	}

	public LogMessage() {
	}
}
