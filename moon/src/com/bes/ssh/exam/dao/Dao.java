package com.bes.ssh.exam.dao;

import java.util.List;

/**
 * 数据集成层接口
 */
public interface Dao {

	public void add(String name, String password);

	public List<? extends Object> get();

}
