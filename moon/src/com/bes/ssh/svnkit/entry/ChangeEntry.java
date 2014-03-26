package com.bes.ssh.svnkit.entry;

import java.io.Serializable;

/**
 * SVN提交的影响变化信息
 */
public class ChangeEntry implements Serializable {

	private static final long serialVersionUID = 9160872839950688494L;

	private long id;// 记录的主键
	private String path;// 当前变化实体的路径
	private String op;// 当前变化实体的操作
	private String copiedPath;// 如果当前变化实体操作为copied，则该域为当前变化实体的前一版本路径
	private long copiedResivison;// 同copiedPath，该域为当前变化实体的前一版本号
	private int addLines;// 当前变化实体的增加行数
	private int deleteLines;// 当前变化实体的减少行数
	private long revision;// 当前提交的版本
	private String root;// 当前SVN版本库的根路径
	private String kind;// 当前变化实体的类型，file/fold

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
