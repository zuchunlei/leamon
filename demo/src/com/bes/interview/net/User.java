package com.bes.interview.net;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -2976436720438694277L;

	private int id;
	private String name;

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "id:" + id + " name:" + name;
	}
}
