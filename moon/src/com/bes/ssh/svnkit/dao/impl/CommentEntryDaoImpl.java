package com.bes.ssh.svnkit.dao.impl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.bes.ssh.svnkit.dao.EntryDao;
import com.bes.ssh.svnkit.entry.CommentEntry;

/**
 * CommentEntry�����ݷ��ʶ���ʵ��
 */
public class CommentEntryDaoImpl extends JdbcDaoSupport implements
		EntryDao<CommentEntry> {

	/**
	 * ���CommentEntry����
	 * 
	 * @param entry
	 */
	public void addEntry(CommentEntry entry) {
		StringBuffer sql = new StringBuffer();

		getJdbcTemplate().execute(sql.toString());
	}

}
