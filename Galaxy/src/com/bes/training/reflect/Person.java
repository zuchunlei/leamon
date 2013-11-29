package com.bes.training.reflect;

public class Person {
	private int id;
	private String name;

	Person() {

	}

	Person(String name, int id) {
		this.id = id;
		this.name = name;
	}

	public String printInf() {
		return this.id + "---" + this.name;
	}
}
