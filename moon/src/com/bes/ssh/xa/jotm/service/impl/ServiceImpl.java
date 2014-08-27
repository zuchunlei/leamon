package com.bes.ssh.xa.jotm.service.impl;

import com.bes.ssh.xa.jotm.dao.Dao;
import com.bes.ssh.xa.jotm.service.Service;

public class ServiceImpl implements Service {

	private Dao localDao;

	private Dao remoteDao;

	public void setLocalDao(Dao localDao) {
		this.localDao = localDao;
	}

	public void setRemoteDao(Dao remoteDao) {
		this.remoteDao = remoteDao;
	}

	public void add(String name, String password) {
		localDao.add(name, password);
		remoteDao.add(name, password);
	}

}
