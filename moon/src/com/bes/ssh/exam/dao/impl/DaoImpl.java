package com.bes.ssh.exam.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.bes.ssh.exam.dao.Dao;

/**
 * POJO形式的数据访问对象实现
 */
public class DaoImpl extends JdbcDaoSupport implements Dao {

	public void add(String name, String password) {
		// operate db via the dataSource object
		String sql = "insert into person(name,password) values ('" + name
				+ "','" + password + "')";
		getJdbcTemplate().execute(sql);
	}

	@SuppressWarnings("unchecked")
	public List<? extends Object> get() {
		String sql = "select * from person";
		return getJdbcTemplate().queryForList(sql);
	}

}
