package com.bes.common.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements Hello {

	protected HelloImpl() throws RemoteException {
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String sayHello(String message) throws RemoteException {
		System.out.println("welcome "+message);
		return message;
	}

}
