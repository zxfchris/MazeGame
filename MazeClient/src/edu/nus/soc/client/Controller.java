package edu.nus.soc.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Movement;
import edu.nus.soc.model.Player;
import edu.nus.soc.model.Position;
import edu.nus.soc.service.CallBackService;
import edu.nus.soc.service.PlayerService;
import edu.nus.soc.service.impl.CallBackServiceImpl;

public class Controller {
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
	
	private static boolean isMovable(Position pos, Movement m, int size) {
		if (size <= 0) {
			return false;
		}
		boolean flag = true;
		switch (m) {
		case S:
			if (pos.getY()==size) {
				flag = false;
			}
			break;
		case N:
			if (pos.getY()==1) {
				flag = false;
			}
			break;
		case E:
			if (pos.getX()==size) {
				flag = false;
			}
			break;
		case W:
			if (pos.getX()==1) {
				flag = false;
			}
			break;
		case NOMOVE:
			break;
		default:
			flag = false;
			break;
		}
		return flag;
	}
	
	public Maze move(Movement m) throws RemoteException {
		if (player == null ) {
			System.out.println("The game hasn't started, please wait...");
			return null;
		} else if (!(isMovable(player.getPos(), m, Maze.get().getSize()))){
			System.out.println("Your movement is illegal. Please choose another movement.");
			return Maze.get();
		}
		return service.move(player, m);
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

}
