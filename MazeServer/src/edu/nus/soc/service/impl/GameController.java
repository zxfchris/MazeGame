package edu.nus.soc.service.impl;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Player;
import edu.nus.soc.model.Position;
import edu.nus.soc.service.CallBackService;

public class GameController {
	private static GameController controller = null;
	private static Object lock = new Object();
	
	private static boolean gameStarted = false;
	private static Timer timer = new Timer(true);
	private static Maze maze = Maze.get();
	private static Map<Integer,CallBackService> callbackMap = new HashMap<Integer, CallBackService>();
	
	private GameController() {
		
	}
	
	public static GameController getController() {
		if(controller == null) {
			synchronized (lock) {  
                if ( controller == null )  
                {  
                    return controller = new GameController();  
                }  
            }  
		}
		return controller;
	}
	
	public static TimerTask startGame = new TimerTask() {

		@Override
		public void run() {
			System.out.println("Timer function starts...");
			//execute all callback methods, notify clients game starts.
			notifyClients();
		}
		
	};

	public Maze initGame(int size, int originalTNum){
		maze.setSize(size);
		maze.setOriginalTNum(originalTNum);
		System.out.println("Maze size and treasure num are initiated!");
		
		Map<Position, Integer> treasureMap = Util.randomTreasures(originalTNum, size);
		maze.setTreasureMap(treasureMap);
		maze.setTreasureNum(originalTNum);
		
		return maze;
	}
	
	public boolean isGameStarted() {
		return gameStarted;
	}

	private static void setGameStarted(boolean gameStarted) {
		GameController.gameStarted = gameStarted;
	}
	
	public Player handleJoinRequest(CallBackService callbackService) {
		int currentId = maze.getCurrentId();
		Player player = new Player(currentId);
		
		/**
		 * first player join, start timer
		 * initiate game
		 */
		if (maze.getPlayers() == null ||
				0 == maze.getPlayers().size()) {
			//execute callback functions to notify clients the start of Game.
			timer.schedule(startGame, 1000 * 5);
		}
		Map<Integer, Player> players = maze.getPlayers();

		Position playerPos = Util.getRandomPos(maze.getSize());
		player.setPos(playerPos);
		
		players.put(currentId, player);
		
		/**
		 * store client callback in hash map.
		 */
//		callbackMap.put(currentId, callbackService);
		addCallbackService(currentId, callbackService);
		
		maze.setCurrentId(currentId + 1);
		currentId = maze.getCurrentId();
		//for debug
		System.out.println("currentId:"+currentId);
		System.out.println("Recieved client's request to join into a game.");
		
		return player;
	}
	
	private static void notifyClients() {
		
		if(!gameStarted ) {
			for (Integer key : callbackMap.keySet()) {
				System.out.println("callback key: " + key);
				try {
					callbackMap.get(key).notifyGameStart(key, maze);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			setGameStarted(true);
		}
		
	}

	public void removeCallbackService(int playerId) {
		callbackMap.remove(playerId);
	}
	
	public static void addCallbackService(int playerId, CallBackService callbackService) {
		callbackMap.put(playerId, callbackService);
	}


}
