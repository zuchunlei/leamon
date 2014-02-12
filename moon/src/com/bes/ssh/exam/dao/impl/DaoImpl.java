package com.bes.ssh.exam.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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

	public void add(String name, String password)  {
		// operate db via the dataSource object
		String sql = "insert into person(name,password) values ('" + name
				+ "','" + password + "')";

		try {
			Connection conn = dataSource.getConnection();
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
