package com.bes.common.svn.kit.test;

public class Annotate {

	private long id;
	
	private String log;
	
	private String author;
	
	private String date;

	private long revision ;
	
	
	public long getRevision() {
		return revision;
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
	
	public Annotate(){}

	public Annotate(String log, String author, String date,long num) {
		this.log = log;
		this.author = author;
		this.date = date;
		this.revision = num;
	}
	
	
}
