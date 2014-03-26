package com.bes.ssh.svnkit.entry;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * SVN�ύ��ע����Ϣ
 */
public class CommentEntry implements Serializable {

	private static final long serialVersionUID = 9192863485961400168L;

	private long id;// ��¼������
	private String message;// �ύ������ע������
	private String author;// �ύ����������
	private Timestamp date;// �ύ����������
	private long revision;// �ύ�����İ汾��
	private String root;// ��ǰ�汾��ĸ�·��

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
