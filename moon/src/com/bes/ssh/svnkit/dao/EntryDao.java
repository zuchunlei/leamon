package com.bes.ssh.svnkit.dao;

/**
 * 数据访问对象
 * 
 * @param <T>
 */
public interface EntryDao<T> {

	/**
	 * 添加Entry对象至数据库
	 * 
	 * @param entry
	 */
	void addEntry(T entry);

}
