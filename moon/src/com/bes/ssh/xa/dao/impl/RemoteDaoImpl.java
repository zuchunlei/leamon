package com.bes.ssh.xa.dao.impl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.bes.ssh.xa.dao.Dao;

public class RemoteDaoImpl extends JdbcDaoSupport implements Dao {

	public void add(String name, String password) {
		String sql = "insert into p(n,p1) values ('" + name + "','" + password
				+ "')";
		getJdbcTemplate().execute(sql);
	}

}
