package edu.nus.soc.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import edu.nus.soc.model.Maze;

public interface CallBackService extends Remote{
	void notifyGameStart(Integer playerId, Maze maze) throws RemoteException;
}
