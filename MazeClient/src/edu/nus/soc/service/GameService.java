package edu.nus.soc.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Movement;
import edu.nus.soc.model.Player;
import edu.nus.soc.model.Position;

public interface GameService extends Remote{
	Maze initGame() throws RemoteException;     //initiate maze size and treasures
	void startGame() throws RemoteException;    //indicate the start of the game
	//boolean joinGame(CallBackService callback) throws RemoteException;  //client joins game
	Player joinGame() throws RemoteException;
	boolean quitGame(int playerId) throws RemoteException;  //client quit game
	//Position move(Position pos, Movement m) throws RemoteException;
	Maze move(Player player, Movement m) throws RemoteException;
}
