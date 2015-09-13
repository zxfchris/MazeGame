package edu.nus.soc.client;

import java.rmi.Naming;
import java.util.HashMap;
import java.util.Map;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Player;
import edu.nus.soc.model.Position;
import edu.nus.soc.model.Movement;
import edu.nus.soc.service.GameService;

public class Client {
	private static Player player 	= null;
	private static Maze	  maze		= null;
	public static void main(String[] args) {
		
		try {
			GameService gameService = 
					(GameService) Naming.lookup("rmi://127.0.0.1:8888/GameService");
			player	= gameService.joinGame();
			System.out.println(player.getId());
			maze	= gameService.move(player, Movement.N);
			printMaze(maze);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private static void printMaze(Maze maze) {
		Map<Position, Integer> 	mazeMap = maze.getTreasureMap();
		Map<Integer, Player> 	players = maze.getPlayers();
		Map<Position, Integer> 	playerMap = new HashMap<Position, Integer>();
		
		for (int playerId : players.keySet()) {
			Player player = players.get(playerId);
			Position playerPos = player.getPos();
			if (playerMap.containsKey(playerPos)) {
				int cellPlayerNum = playerMap.get(playerPos);
				playerMap.put(playerPos, cellPlayerNum++);
			} else {
				playerMap.put(playerPos, 1);
			}
		}

		for (int j=0; j<maze.getSize(); j++) {
			for (int i=0; i<maze.getSize(); i++) {
				Position pos = new Position(i, j);
				if (mazeMap.containsKey(pos)) {
					System.out.print("M"+mazeMap.get(pos)+"\t");
				} else if(playerMap.containsKey(pos)) {
					System.out.print("P"+playerMap.get(pos)+"\t");
				} else {
					System.out.print("X\t");
				}
			}
			System.out.println();
		}
	}
}
