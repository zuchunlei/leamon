package com.bes.ssh.svnkit.dao.impl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.bes.ssh.svnkit.dao.EntryDao;
import com.bes.ssh.svnkit.entry.ChangeEntry;

/**
 * ChangeEntry�����ݷ��ʶ���ʵ��
 */
public class ChangeEntryDaoImpl extends JdbcDaoSupport implements
		EntryDao<ChangeEntry> {

	/**
	 * ���ChangeEntry����
	 * 
	 * @param entry
	 */
	public void addEntry(ChangeEntry entry) {
		StringBuffer sql = new StringBuffer();

		getJdbcTemplate().execute(sql.toString());
	}

}
