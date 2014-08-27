package com.bes.ssh.xa.jotm.dao.impl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.bes.ssh.xa.jotm.dao.Dao;

public class LocalDaoImpl extends JdbcDaoSupport implements Dao {

	public void add(String name, String password) {
		String sql = "insert into person(name,password) values ('" + name
				+ "','" + password + "')";
		getJdbcTemplate().execute(sql);
	}

}
