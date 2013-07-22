package com.bes.common.svn.kit.db;

import java.io.Serializable;

public class FileDiff implements Serializable {
	private static final long serialVersionUID = 8047684480755503271L;

	private String path;
	private String op;
	private String type;
	private String copiedPath;
	private long copiedRevision;
	private int line;
	private long revision;
	private String log;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCopiedPath() {
		return copiedPath;
	}

	public void setCopiedPath(String copiedPath) {
		this.copiedPath = copiedPath;
	}

	public long getCopiedRevision() {
		return copiedRevision;
	}

	public void setCopiedRevision(long copiedRevision) {
		this.copiedRevision = copiedRevision;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public long getRevision() {
		return revision;
	}

	public void setRevision(long revision) {
		this.revision = revision;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}
}
