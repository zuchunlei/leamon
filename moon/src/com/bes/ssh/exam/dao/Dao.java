package com.bes.ssh.exam.dao;

import java.util.List;

/**
 * ���ݼ��ɲ�ӿ�
 */
public interface Dao {

	public void add(String name, String password);

	public List<? extends Object> get();

}
