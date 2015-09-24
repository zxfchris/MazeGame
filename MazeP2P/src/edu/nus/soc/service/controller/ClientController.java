package edu.nus.soc.service.controller;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Movement;
import edu.nus.soc.model.Peer;
import edu.nus.soc.model.Player;
import edu.nus.soc.service.CallBackService;
import edu.nus.soc.service.PlayerService;
import edu.nus.soc.service.impl.CallBackServiceImpl;
import edu.nus.soc.util.Util;

public class ClientController {
	private static Maze				maze = Maze.get();
	private static Player			player;
	private static PlayerService	primaryService = null;
	private static PlayerService	secondaryService = null;

	private static CallBackService	callbackService;	//callback service to be called by game server
	
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

	public ClientController() throws RemoteException, MalformedURLException, NotBoundException {
		callbackService = new CallBackServiceImpl();
		setPrimaryService((PlayerService) Naming.lookup(Util.getRMIStringByIpPort(Util.defaultIp, Util.defaultPort)));
	}
	
	public Maze move(Movement move) {
		Peer peer = new Peer();
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
		
		System.out.println("111111111111111111");
		peer.printNodeList();
		System.out.println("222222222222222222");
		try {
			maze = primaryService.move(player.getId(), move, peer);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			try {
				maze = secondaryService.move(player.getId(), move, peer);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		Maze.get().setMaze(maze);
		System.out.println("333333333333333333");
		peer.printNodeList();
		System.out.println("444444444444444444");
		Peer.get().setNodeList(peer.getNodeList());
		//updatePlayerService();
		
		return maze;
	}
	
	public Player joinGame() {
		try {
			return primaryService.joinGame(callbackService);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean quitGame() {
		if (null == player) {
			System.out.println("The game hasn't started, you cannot quit at this time...");
			return false;
		}
		try {
			return primaryService.quitGame(player.getId());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public static void updatePlayerService() {
		try {
			setPrimaryService((PlayerService) Naming.lookup(Util.getRMIStringByNode(Peer.get().getNodeList().get(0))));
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			setSecondaryService((PlayerService) Naming.lookup(Util.getRMIStringByNode(Peer.get().getNodeList().get(1))));
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
