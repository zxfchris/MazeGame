package edu.nus.soc.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Movement;
import edu.nus.soc.model.Player;
import edu.nus.soc.service.CallBackService;
import edu.nus.soc.service.PlayerService;
import edu.nus.soc.service.impl.CallBackServiceImpl;
import edu.nus.soc.service.impl.Util;

public class Controller {
	private static Maze				maze = Maze.get();
	private static Player			player;
	private static PlayerService	service;			//services provided by game server
	private static CallBackService	callbackService;	//callback service to be called by game server
	private static boolean			gameStarted = false;
	
	public static boolean isGameStarted() {
		return gameStarted;
	}

	public static void setGameStarted(boolean gameStarted) {
		Controller.gameStarted = gameStarted;
	}

	public Controller() throws RemoteException, MalformedURLException, NotBoundException {
		callbackService = new CallBackServiceImpl();
		service			= (PlayerService) Naming.lookup("rmi://127.0.0.1:8888/playerService");
	}
	
	public Maze move(Movement move) throws RemoteException {
		if (player == null ) {
			System.out.println("The game hasn't started, please wait...");
			return null;
		} else if (null == move) {
			System.out.println("Sorry, your input is invalid, please try again.");
			return maze;
		} else if (false == Util.isMovable(player.getPos(), move, maze.getSize()) ) {
			System.out.println("Sorry, you have reached the boundary, please try again.");
			return maze;
		} 
		maze = service.move(player.getId(), move);
		
		return maze;
	}
	
	public Player joinGame() throws RemoteException {
		return service.joinGame(callbackService);
	}
	
	public boolean quitGame() throws RemoteException {
		if (null == player) {
			System.out.println("The game hasn't started, you cannot quit at this time...");
			return false;
		}
		return service.quitGame(player.getId());
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void update(Maze maze) {
		player = maze.getPlayers().get(player.getId());
		setMaze(maze);
	}

	public static Maze getMaze() {
		return maze;
	}

	public static void setMaze(Maze maze) {
		Controller.maze = maze;
	}

}
