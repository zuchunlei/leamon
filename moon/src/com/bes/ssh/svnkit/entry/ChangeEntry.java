package com.bes.ssh.svnkit.entry;

import java.io.Serializable;

/**
 * SVN�ύ��Ӱ��仯��Ϣ
 */
public class ChangeEntry implements Serializable {

	private static final long serialVersionUID = 9160872839950688494L;

	private long id;// ��¼������
	private String path;// ��ǰ�仯ʵ���·��
	private String op;// ��ǰ�仯ʵ��Ĳ���
	private String copiedPath;// �����ǰ�仯ʵ�����Ϊcopied�������Ϊ��ǰ�仯ʵ���ǰһ�汾·��
	private long copiedResivison;// ͬcopiedPath������Ϊ��ǰ�仯ʵ���ǰһ�汾��
	private int addLines;// ��ǰ�仯ʵ�����������
	private int deleteLines;// ��ǰ�仯ʵ��ļ�������
	private long revision;// ��ǰ�ύ�İ汾
	private String root;// ��ǰSVN�汾��ĸ�·��
	private String kind;// ��ǰ�仯ʵ������ͣ�file/fold

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getCopiedPath() {
		return copiedPath;
	}

	public void setCopiedPath(String copiedPath) {
		this.copiedPath = copiedPath;
	}

	public long getCopiedResivison() {
		return copiedResivison;
	}

	public void setCopiedResivison(long copiedResivison) {
		this.copiedResivison = copiedResivison;
	}

	public int getAddLines() {
		return addLines;
	}

	public void setAddLines(int addLines) {
		this.addLines = addLines;
	}

	public int getDeleteLines() {
		return deleteLines;
	}

	public void setDeleteLines(int deleteLines) {
		this.deleteLines = deleteLines;
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

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

}
