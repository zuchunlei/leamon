package com.bes.ssh.exam.dao.impl;

import javax.sql.DataSource;

import com.bes.ssh.exam.dao.Dao;

/**
 * POJO��ʽ�����ݷ��ʶ���ʵ��
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
