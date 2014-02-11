package com.bes.ssh.exam.dao.impl;

import javax.sql.DataSource;

import com.bes.ssh.exam.dao.Dao;

/**
 * POJO形式的数据访问对象实现
 */
public class DaoImpl implements Dao {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void add(String name, String password) {
		// operate db via the dataSource object
		System.out.println(dataSource);
	}
}
