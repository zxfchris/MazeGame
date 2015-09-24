package edu.nus.soc.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Peer;

public interface CallBackService extends Remote{
	void notifyGameStart(Integer playerId, Maze maze, Peer peer) throws RemoteException;
	//void detectAlive() throws RemoteException;	//used by server to examine whether client is still alive
	void notifySelectedAsServer(Maze maze, Peer peer) throws RemoteException;
}
