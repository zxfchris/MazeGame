package edu.nus.soc.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Movement;
import edu.nus.soc.model.Player;
import edu.nus.soc.model.Position;
import edu.nus.soc.service.GameService;

public class GameServiceImpl extends UnicastRemoteObject implements GameService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Maze initGame() throws RemoteException{
		System.out.println("Please input your maze size N:");
		Scanner scanner = new Scanner(System.in);
		int size = scanner.nextInt();
		System.out.println("Please input your original treasure num M:");
		int originalTNum = scanner.nextInt();
		Maze maze = Maze.get();
		maze.init(size, originalTNum);
		System.out.println("Maze size and treasure num are initiated!");
		
		Map<Position, Integer> treasureMap = randomTreasures(originalTNum, size);
		maze.setTreasureMap(treasureMap);
		
		return maze;
	}
	
	/**
	 * initiate treasures randomly
	 * @param originalTNum
	 * @param size
	 * @return
	 */
	private static Map<Position, Integer> randomTreasures(int originalTNum, int size) {
		Map<Position, Integer> treasureMap = new HashMap<Position, Integer>();
		for (int i=0; i<originalTNum; i++) {
			Position pos = getRandomPos(size);
			if(treasureMap.containsKey(pos)) {
				int cellTreasure = treasureMap.get(pos);
				treasureMap.put(pos, cellTreasure+1);
			} else {
				treasureMap.put(pos, 1);
			}
		}
		return treasureMap;
	}
	
	private static Position getRandomPos(int size) {
		int x = (int) Math.round(Math.random()* size + 1);
		int y = (int) Math.round(Math.random()* size + 1);
		Position pos = new Position(x, y);
		return pos;
	}

	@Override
	public void startGame() throws RemoteException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Player joinGame() throws RemoteException{
		int currentId = Maze.get().getCurrentId();
		Player player = new Player(currentId);
		Map<Integer, Player> players = Maze.get().getPlayers();
		players.put(currentId, player);
		Maze.get().setCurrentId(currentId);
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
	
	public static void collectTreasures(Player player) {
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
