package edu.nus.soc.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Movement;
import edu.nus.soc.model.Peer;
import edu.nus.soc.model.Player;
import edu.nus.soc.model.Position;
import edu.nus.soc.service.CallBackService;
import edu.nus.soc.service.PlayerService;
import edu.nus.soc.service.controller.ClientController;
import edu.nus.soc.service.controller.ServerController;

public class PlayerServiceImpl extends UnicastRemoteObject implements PlayerService{

	private final ServerController serverController = ServerController.getController();
	
	private static Maze maze = Maze.get();
	
	public PlayerServiceImpl() throws RemoteException {
		super();
	}

	private static void collectTreasures(Player player) {
		
		Position currentPos = player.getPos();
		Map<Position, Integer> treasureMap = maze.getTreasureMap();
		
		if (treasureMap.containsKey(currentPos)) {			//collect treasure
			int cellTreasure = treasureMap.get(currentPos);
			int treasureNum = cellTreasure + player.getTreasureNum();
			player.setTreasureNum(treasureNum);
			
			System.out.println("Player "+ player.getId() +"collected "+
					cellTreasure +" treasures");
			Maze.get().setTreasureNum(Maze.get().getTreasureNum() - cellTreasure);
			
			treasureMap.put(currentPos, 0);
			maze.setTreasureMap(treasureMap);
		}
		
		Map<Integer, Player> players = maze.getPlayers();
		players.put(player.getId(), player);
		maze.setPlayers(players);
	}
	
	@Override
	public Player joinGame(CallBackService client) throws RemoteException{
		if (true == Maze.get().isGameStarted()) {
			return null;
		}

		Player player = serverController.handleJoinRequest(client);
		//create new Node in nodeMap
		String ipaddr = null;
		try {
			ipaddr = RemoteServer.getClientHost();
		} catch (ServerNotActiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return player;
	}

	@Override
	public boolean quitGame(int playerId) throws RemoteException{
		if (false == Maze.get().isGameStarted()) {
			return false;
		}
		
		Map<Integer, Player> players = maze.getPlayers();
		players.remove(playerId);
		maze.setPlayers(players);
		
		//when a player quits game actively
		//the server does not hold its callback service
		serverController.removeCallbackService(playerId);
		System.out.println("Player "+ playerId +" quited the game");
		
		return true;
	}

	@Override
	public Maze move(Integer playerId, Movement m, Peer peer) throws RemoteException{
		if (false == Maze.get().isGameStarted()) {
			return null;
		}
		Player player = Maze.get().getPlayers().get(playerId);
		Position currentPos = player.getPos();
		int x = currentPos.getX();
		int y = currentPos.getY();
		
		switch (m) {
		case N:
			currentPos.setY(y-1);
			break;
		case S:
			currentPos.setY(y+1);
			break;
		case E:
			currentPos.setX(x+1);
			break;
		case W:
			currentPos.setX(x-1);
			break;
		case NOMOVE:
			break;
		}
		player.setPos(currentPos);
		collectTreasures(player);
		
		
		//as primary server, we should synchronize maze info to secondary server
		if (Peer.get().isPrimaryServer()) {
			System.out.println("######I am the primary server, synchronize secondary server.");
			ClientController.getSecondaryService().synchronizeMaze(Maze.get());
		}
		
		//as secondary server, when movement is requested, the primary server is down at this time.
		//we should level up secondary server as primary server and select another secondary server.
		if (Peer.get().isSecondaryServer()) {
			System.out.println("######I am the secondary server, primary server has crashed, level"
					+ "up myself and select another secondary server.");
			ServerController.levelUpToPrimaryServer();
			ClientController.updatePlayerService();
		}
		
		for (int index = 0; index < Peer.get().getNodeList().size(); index ++) {
			peer.getNodeList().add(Peer.get().getNodeList().get(index));
		}
		//peer.setNodeList(Peer.get().getNodeList());
		System.out.println("((((((((((((((((()))))))))))))))))");
		Peer.get().printNodeList();
		System.out.println("((((((((((((((((()))))))))))))))))");
		peer.printNodeList();
		System.out.println("Player " + player.getId() + " moved!");
		maze.peer.setNodeList(Peer.get().getNodeList());
		return maze;
	}

	@Override
	public Peer updatePeerInfo() throws RemoteException {
		// TODO Auto-generated method stub
		return Peer.get();
	}

	@Override
	public void synchronizeMaze(Maze maze) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("<secondary>maze info synchronized from primary server.");
		Maze.get().setMaze(maze);
	}

}
