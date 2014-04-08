package com.bes.ssh.svnkit.service.impl;

import com.bes.ssh.svnkit.dao.EntryDao;
import com.bes.ssh.svnkit.entry.ChangeEntry;
import com.bes.ssh.svnkit.entry.CommentEntry;
import com.bes.ssh.svnkit.service.RepoParameter;
import com.bes.ssh.svnkit.service.SvnKitService;

/**
 * SvnKitService服务实现类
 */
public class SvnKitServiceImpl implements SvnKitService {

	private EntryDao<CommentEntry> commentDao;

	private EntryDao<ChangeEntry> changeDao;

	public void setCommentDao(EntryDao<CommentEntry> commentDao) {
		this.commentDao = commentDao;
	}

	public void setChangeDao(EntryDao<ChangeEntry> changeDao) {
		this.changeDao = changeDao;
	}

	public void scan(RepoParameter param, long start, long end)
			throws Exception {
	}

	public void scan(RepoParameter param) throws Exception {

	}

}
