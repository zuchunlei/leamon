package com.bes.ssh.svnkit.dao;

/**
 * ���ݷ��ʶ���
 * 
 * @param <T>
 */
public interface EntryDao<T> {

	/**
	 * ���Entry���������ݿ�
	 * 
	 * @param entry
	 */
	void addEntry(T entry);

}
