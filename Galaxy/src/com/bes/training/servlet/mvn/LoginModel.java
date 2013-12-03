package com.bes.training.servlet.mvn;

public class LoginModel {
	private String name;
	private int id;

	public LoginModel(String name, String id) {
		this.name = name;
		this.id = Integer.parseInt(id);
	}

	public boolean verifyLogin() {
		if (name == null || id == 0) {
			throw new RuntimeException("信息不能为空");
		}
		if (((name.equals("zhaowei")) && (id == 123))
				|| ((name.equals("zuchunlei")) && (id == 123))) {
			return true;
		}
		return false;
	}
}
