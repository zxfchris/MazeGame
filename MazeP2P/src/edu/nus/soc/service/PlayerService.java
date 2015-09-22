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
	
	Maze move(Integer playerId, Movement m) throws RemoteException;
	
	//following methods are used for updating peer info
	boolean detectServerAlive() throws RemoteException;	//used by client to examine whether server is still alive
	
	Peer updatePeerInfo() throws RemoteException;  //client would like to update peer info intensively.
	
	Maze synchronizeMaze() throws RemoteException; //used between primary server and secondary server.
}
