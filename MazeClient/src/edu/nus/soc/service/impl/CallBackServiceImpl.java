package edu.nus.soc.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import edu.nus.soc.service.CallBackService;

public class CallBackServiceImpl extends UnicastRemoteObject implements CallBackService{

	public CallBackServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void startGame() throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Client callback method startGame executed..");
	}

}
