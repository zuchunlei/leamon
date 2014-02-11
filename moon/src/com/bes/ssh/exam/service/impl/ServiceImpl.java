package com.bes.ssh.exam.service.impl;

import com.bes.ssh.exam.dao.Dao;
import com.bes.ssh.exam.service.Service;

/**
 * POJO形式的业务实现类
 */
public class ServiceImpl implements Service {

	private Dao dao;

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public void add(String name, String password) {
		dao.add(name, password);
	}
}
