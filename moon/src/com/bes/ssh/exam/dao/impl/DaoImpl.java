package com.bes.ssh.exam.dao.impl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.bes.ssh.exam.dao.Dao;

/**
 * POJO��ʽ�����ݷ��ʶ���ʵ��
 */
public class DaoImpl extends JdbcDaoSupport implements Dao {

	public void add(String name, String password) {
		// operate db via the dataSource object
		String sql = "insert into person(name,password) values ('" + name
				+ "','" + password + "')";
		getJdbcTemplate().execute(sql);
	}
}
