package com.bes.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
	
	public String sayHello(String message) throws RemoteException;

}
