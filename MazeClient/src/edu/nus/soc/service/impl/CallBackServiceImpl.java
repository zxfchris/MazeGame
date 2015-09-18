package edu.nus.soc.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import edu.nus.soc.client.Controller;
import edu.nus.soc.model.Maze;
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
	public void startGame(Integer playerId, Maze maze) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Game started, now you can move!");
		
		Util.printMaze(playerId, maze);
		Controller.setMaze(maze);
		Controller.setGameStarted(true);
	}

}
