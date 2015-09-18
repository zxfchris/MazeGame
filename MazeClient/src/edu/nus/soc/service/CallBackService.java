package edu.nus.soc.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallBackService extends Remote{
	void notifyGameStart() throws RemoteException;
	
	void notifyGameEnd() throws RemoteException;
}
