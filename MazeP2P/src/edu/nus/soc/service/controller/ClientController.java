package edu.nus.soc.service.controller;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Movement;
import edu.nus.soc.model.Node;
import edu.nus.soc.model.Peer;
import edu.nus.soc.model.Player;
import edu.nus.soc.service.CallBackService;
import edu.nus.soc.service.PlayerService;
import edu.nus.soc.service.impl.CallBackServiceImpl;
import edu.nus.soc.util.Util;

public class ClientController {
	private static Maze				maze = Maze.get();
	private static Peer				peer = Peer.get();
	private static Player			player;
	private static PlayerService	primaryService = null;
	private static PlayerService	secondaryService = null;
	
	private static Node  			primaryServer;
	private static Node  			secondaryServer;

	private static CallBackService	callbackService;	//callback service to be called by game server
	private static boolean			gameStarted = false;
	
	/*public static PlayerService getService() {
		return service;
	}

	public static void setService(PlayerService service) {
		ClientController.service = service;
	}*/
	
	public static PlayerService getPrimaryService() {
		return primaryService;
	}

	public static void setPrimaryService(PlayerService primaryService) {
		ClientController.primaryService = primaryService;
	}
	
	public static PlayerService getSecondaryService() {
		return secondaryService;
	}

	public static void setSecondaryService(PlayerService secondaryService) {
		ClientController.secondaryService = secondaryService;
	}

	public static boolean isGameStarted() {
		return gameStarted;
	}

	public static void setGameStarted(boolean gameStarted) {
		ClientController.gameStarted = gameStarted;
	}

	public ClientController() throws RemoteException, MalformedURLException, NotBoundException {
		callbackService = new CallBackServiceImpl();
		setPrimaryService((PlayerService) Naming.lookup(Util.getRMIStringByIpPort(Util.defaultIp, Util.defaultPort)));
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
		maze = primaryService.move(player.getId(), move);
		
		return maze;
	}
	
	public Player joinGame() throws RemoteException {
		return primaryService.joinGame(callbackService);
	}
	
	public boolean quitGame() throws RemoteException {
		if (null == player) {
			System.out.println("The game hasn't started, you cannot quit at this time...");
			return false;
		}
		return primaryService.quitGame(player.getId());
	}
	
	public static boolean detectServerAllive(int serverIdx) throws RemoteException {
		if (0 == serverIdx) {
			return primaryService.detectServerAlive();
		} else if (1 == serverIdx) {
			return secondaryService.detectServerAlive();
		} else {
			System.out.println("server index invalid.");
			return false;
		}
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
		ClientController.maze = maze;
	}

	public static Peer getPeer() {
		return peer;
	}

	public static void setPeer(Peer peer) {
		ClientController.peer = peer;
	}

}
