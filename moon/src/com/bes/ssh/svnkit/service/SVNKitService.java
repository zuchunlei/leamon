package com.bes.ssh.svnkit.service;

/**
 * SVN���߷���ӿ�
 */
public interface SVNKitService {

	/**
	 * ɨ��subversion�汾����Ϣ�����ύ��Ϣ���ύ�����ı仯��Ϣ����ͳ�Ʋ��洢
	 * 
	 * @param param
	 *            ����SVN�汾������Ӳ���
	 * @param start
	 *            ɨ���������ʼ�汾
	 * @param end
	 *            ɨ������Ľ����汾
	 * @throws Exception
	 */
	void scan(RepoParameter param, long start, long end) throws Exception;

	/**
	 * ɨ��subversion�汾����Ϣ
	 * 
	 * @param param
	 *            ����SVN�汾������Ӳ���
	 * @throws Exception
	 */
	void scan(RepoParameter param) throws Exception;

}
