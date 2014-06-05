package com.bes.ssh.exam.service;

import java.util.List;

/**
 * 应用服务层业务接口
 */
public interface Service {

	public void add(String name, String password);

	public List<? extends Object> get();

}
