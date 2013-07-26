package com.bes.common.corba;

import test._HelloImplBase;

public class HelloImpl extends _HelloImplBase {
	private static final long serialVersionUID = 6659718914623348121L;

	@Override
	public String sayHello(String message) {
		System.out.println("It`s servers,client is invoke!");
		System.out.println("hello" + message);
		return message;
	}
}
