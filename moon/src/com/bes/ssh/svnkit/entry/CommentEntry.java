package com.bes.ssh.svnkit.entry;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * SVN提交的注释信息
 */
public class CommentEntry implements Serializable {

	private static final long serialVersionUID = 9192863485961400168L;

	private long id;// 记录的主键
	private String message;// 提交操作的注释内容
	private String author;// 提交操作的作者
	private Timestamp date;// 提交操作的日期
	private long revision;// 提交操作的版本号
	private String root;// 当前版本库的根路径

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public long getRevision() {
		return revision;
	}

	public void setRevision(long revision) {
		this.revision = revision;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

}
