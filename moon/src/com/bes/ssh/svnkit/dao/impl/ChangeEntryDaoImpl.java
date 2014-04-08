package com.bes.ssh.svnkit.dao.impl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.bes.ssh.svnkit.dao.EntryDao;
import com.bes.ssh.svnkit.entry.ChangeEntry;

/**
 * ChangeEntry的数据访问对象实现
 */
public class ChangeEntryDaoImpl extends JdbcDaoSupport implements
		EntryDao<ChangeEntry> {

	/**
	 * 添加ChangeEntry对象
	 * 
	 * @param entry
	 */
	public void addEntry(ChangeEntry entry) {
		StringBuffer sql = new StringBuffer();

		getJdbcTemplate().execute(sql.toString());
	}

}
