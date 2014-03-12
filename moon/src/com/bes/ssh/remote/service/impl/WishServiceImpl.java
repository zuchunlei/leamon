package com.bes.ssh.remote.service.impl;

import com.bes.ssh.remote.service.WishService;

public class WishServiceImpl implements WishService {

	private String wish = "best wish too you";

	public void setWish(String wish) {
		this.wish = wish;
	}

	public String wish(String target) {
		return "hello,[" + target + "]," + wish;
	}

}
