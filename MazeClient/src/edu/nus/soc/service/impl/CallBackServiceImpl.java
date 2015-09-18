package edu.nus.soc.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import edu.nus.soc.client.Controller;
import edu.nus.soc.service.CallBackService;

public class CallBackServiceImpl extends UnicastRemoteObject implements CallBackService{

	public CallBackServiceImpl() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void notifyGameStart() throws RemoteException {
		System.out.println("Game started, now you can move!");
		Controller.setGameStarted(true);
	}

	@Override
	public void notifyGameEnd() throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Game over!");
	}

}
