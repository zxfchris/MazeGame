package edu.nus.soc.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Movement;
import edu.nus.soc.model.Player;

public interface PlayerService extends Remote{
	Player joinGame() throws RemoteException;  //client requests to join the game
	
	boolean quitGame(int playerId) throws RemoteException;  //client quit game
	
	Maze move(Player player, Movement m) throws RemoteException;
}
