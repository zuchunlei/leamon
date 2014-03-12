package com.bes.ssh.remote.service;

/**
 * wish服务接口
 */
public interface WishService {

	/**
	 * 向目标对象发出祝福
	 * 
	 * @param target
	 *            目标对象
	 * @return
	 */
	String wish(String target);

}
