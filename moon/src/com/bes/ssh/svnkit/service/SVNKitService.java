package com.bes.ssh.svnkit.service;

/**
 * SVN工具服务接口
 */
public interface SVNKitService {

	/**
	 * 扫描subversion版本库信息，将提交信息与提交操作的变化信息进行统计并存储
	 * 
	 * @param param
	 *            连接SVN版本库的连接参数
	 * @param start
	 *            扫描操作的起始版本
	 * @param end
	 *            扫描操作的结束版本
	 * @throws Exception
	 */
	void scan(RepoParameter param, long start, long end) throws Exception;

	/**
	 * 扫描subversion版本库信息
	 * 
	 * @param param
	 *            连接SVN版本库的连接参数
	 * @throws Exception
	 */
	void scan(RepoParameter param) throws Exception;

}
