package edu.nus.soc.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Random;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Movement;
import edu.nus.soc.model.Player;
import edu.nus.soc.model.Position;
import edu.nus.soc.service.PlayerService;

public class PlayerServiceImpl extends UnicastRemoteObject implements PlayerService{

	public PlayerServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Player joinGame() throws RemoteException{
		int currentId = Maze.get().getCurrentId();
		
		Player player = new Player(currentId);
		Map<Integer, Player> players = Maze.get().getPlayers();
		
		Position playerPos = Util.getRandomPos(Maze.get().getSize());
		player.setPos(playerPos);
		
		players.put(currentId, player);
		Maze.get().setCurrentId(currentId + 1);
		
		currentId = Maze.get().getCurrentId();
		//for debug
		System.out.println("currentId:"+currentId);
		System.out.println("Recieved client's request to join into a game.");
		
		return player;
	}

	@Override
	public boolean quitGame(int playerId) throws RemoteException{
		Map<Integer, Player> players = Maze.get().getPlayers();
		players.remove(playerId);
		Maze.get().setPlayers(players);
		System.out.println("Player "+ playerId +" quited the game");
		return true;
	}

	@Override
	public Maze move(Player player, Movement m) throws RemoteException{
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
		return Maze.get();
	}
	
	private static void collectTreasures(Player player) {
		Position currentPos = player.getPos();
		Maze maze = Maze.get();
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
