package edu.nus.soc.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Movement;
import edu.nus.soc.model.Player;
import edu.nus.soc.model.Position;
import edu.nus.soc.service.CallBackService;
import edu.nus.soc.service.PlayerService;

public class PlayerServiceImpl extends UnicastRemoteObject implements PlayerService{
	private final GameController gameController = GameController.getController();
	private static Maze maze = Maze.get();
	
	public PlayerServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public Player joinGame(CallBackService client) throws RemoteException{
		if (true == gameController.isGameStarted()) {
			return null;
		}

		Player player = gameController.handleJoinRequest(client);
		return player;
	}

	@Override
	public boolean quitGame(int playerId) throws RemoteException{
		if (false == gameController.isGameStarted()) {
			return false;
		}
		Map<Integer, Player> players = maze.getPlayers();
		players.remove(playerId);
		maze.setPlayers(players);
		//when a player quits game actively
		//the server does not hold its callback service
		gameController.removeCallbackService(playerId);
		System.out.println("Player "+ playerId +" quited the game");
		
		return true;
	}

	@Override
	public Maze move(Player player, Movement m) throws RemoteException{
		if (false == gameController.isGameStarted()) {
			return null;
		}
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
		System.out.println("Player moved!");
		return maze;
	}
	
	private static void collectTreasures(Player player) {
		Position currentPos = player.getPos();
		Map<Position, Integer> treasureMap = maze.getTreasureMap();
		if (treasureMap.containsKey(currentPos)) {
			int cellTreasure = treasureMap.get(currentPos);
			int treasureNum = cellTreasure + player.getTreasureNum();
			player.setTreasureNum(treasureNum);
			System.out.println("Player "+ player.getId() +"collected "+
					cellTreasure +" treasures");
			treasureMap.put(currentPos, 0);
			maze.setTreasureMap(treasureMap);
		}
		Map<Integer, Player> players = maze.getPlayers();
		players.put(player.getId(), player);
		maze.setPlayers(players);
	}

}
