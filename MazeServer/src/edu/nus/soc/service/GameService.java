package edu.nus.soc.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Movement;
import edu.nus.soc.model.Player;

public interface GameService extends Remote{
	Maze initGame() throws RemoteException;     //initiate maze size and treasures
	
	void startGame() throws RemoteException;    //indicate the start of the game
	
	Player joinGame() throws RemoteException;  //client requests to join the game
	
	boolean quitGame(int playerId) throws RemoteException;  //client quit game
	
	Maze move(Player player, Movement m) throws RemoteException;
}
