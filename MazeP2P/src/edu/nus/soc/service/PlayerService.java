package edu.nus.soc.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Movement;
import edu.nus.soc.model.Peer;
import edu.nus.soc.model.Player;

public interface PlayerService extends Remote{
	Player joinGame(CallBackService client) throws RemoteException;  //client requests to join the game
	
	boolean quitGame(int playerId) throws RemoteException;  //client quit game
	
	Maze move(Integer playerId, Movement m, Peer peer) throws RemoteException;
	
	//following methods are used for updating peer info
	Peer updatePeerInfo() throws RemoteException;  //client would like to update peer info intensively.
	
	void synchronizeMaze(Maze maze) throws RemoteException; //used between primary server and secondary server.
}
