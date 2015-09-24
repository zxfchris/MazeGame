package edu.nus.soc.service.controller;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Node;
import edu.nus.soc.model.Peer;
import edu.nus.soc.model.Player;
import edu.nus.soc.model.Position;
import edu.nus.soc.service.CallBackService;
import edu.nus.soc.service.PlayerService;
import edu.nus.soc.service.impl.PlayerServiceImpl;
import edu.nus.soc.util.Util;

public class ServerController {
	private static ServerController controller = null;
	private static Object lock = new Object();
	
	private static Timer timer = new Timer(true);
	private static Maze maze = Maze.get();
	private static Map<Integer,CallBackService> callbackMap = new HashMap<Integer, CallBackService>();
	private static PlayerService playerService = null;
	
	private ServerController() {
		
	}
	
	public static ServerController getController() {
		if(controller == null) {
			synchronized (lock) {  
                if ( controller == null )  
                {  
                    return controller = new ServerController();  
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
			//for debug, print joined notes
			Peer.get().printNodeList();
		}
		
	};
	private static void notifyClients() {
		
		if(!Maze.get().isGameStarted() ) {
			for (Integer key : callbackMap.keySet()) {
				System.out.println("callback key: " + key);
				try {
					if (key == 1) {
						callbackMap.get(key).notifySelectedAsServer(maze, Peer.get());
					}
					callbackMap.get(key).notifyGameStart(key, maze, Peer.get());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			Maze.get().setGameStarted(true);
		}
		ClientController.updatePlayerService();
	}
	
	
	public Maze initGame(int size, int originalTNum){
		maze.setSize(size);
		maze.setOriginalTNum(originalTNum);
		System.out.println("Maze size and treasure num are initiated!");
		
		Map<Position, Integer> treasureMap = Util.randomTreasures(originalTNum, size);
		maze.setTreasureMap(treasureMap);
		maze.setTreasureNum(originalTNum);
		
		return maze;
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
			timer.schedule(startGame, 1000 * 20);
		}
		Map<Integer, Player> players = maze.getPlayers();

		Position playerPos = Util.getRandomPos(maze.getSize());
		player.setPos(playerPos);
		
		players.put(currentId, player);
		
		/**
		 * store client callback in hash map.
		 */
		addCallbackService(currentId, callbackService);
		
		String ipaddr = null;
		try {
			ipaddr = RemoteServer.getClientHost();
		} catch (ServerNotActiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int port = Util.allocatePort ++;
		Node node = new Node(ipaddr, port, player.getId());
		Peer.get().getNodeList().add(node);
		System.out.println("nodeListSize:" + Peer.get().getNodeList().size());
		System.out.println("added node: ip = "+ Peer.get().getNodeByPlayerId(player.getId()).getIp()+
				" port = "+Peer.get().getNodeByPlayerId(player.getId()).getPort());
		
		maze.setCurrentId(currentId + 1);
		currentId = maze.getCurrentId();
		//for debug
		System.out.println("currentId:"+currentId);
		System.out.println("Recieved client's request to join into a game.");
		
		return player;
	}
	
	
	
	public static void RegistRMIService() {
		if (null == playerService) {
			try {
				playerService = new PlayerServiceImpl();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			LocateRegistry.createRegistry(Peer.get().getLocalNode().getPort());
			Naming.rebind(Util.getRMIStringByIpPort(ip, Peer.get().getLocalNode().getPort()), playerService);
			System.out.println("RMI service registered, ip - " + Peer.get().getLocalNode().getIp() + 
					", port - "+ Peer.get().getLocalNode().getPort());
		} catch (UnknownHostException | RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	//called by secondary server
	public static boolean levelUpToPrimaryServer() {
		Peer.get().getNodeList().remove(0);	//delete previous primary server
		Peer.get().setSecondaryServer(false);
		Peer.get().setPrimaryServer(true);
		return true;
	}

	public void removeCallbackService(int playerId) {
		callbackMap.remove(playerId);
	}
	
	public static void addCallbackService(int playerId, CallBackService callbackService) {
		callbackMap.put(playerId, callbackService);
	}

}
