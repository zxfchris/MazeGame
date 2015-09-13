package edu.nus.soc.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Player;
import edu.nus.soc.model.Position;
import edu.nus.soc.service.GameService;
import edu.nus.soc.service.impl.GameServiceImpl;

public class Server {
	
	public static void main(String[] args){
		try {
			GameService gameService = new GameServiceImpl();
			LocateRegistry.createRegistry(8888);
			Naming.rebind("rmi://127.0.0.1:8888/GameService", gameService);
			System.out.println("Server starts!");
			Maze maze = gameService.initGame();
			printMaze(maze);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void printMaze(Maze maze) {
		Map<Position, Integer> 	mazeMap = maze.getTreasureMap();
		Map<Integer, Player> 	players = maze.getPlayers();

		for (int j=0; j<maze.getSize(); j++) {
			for (int i=0; i<maze.getSize(); i++) {
				Position pos = new Position(i, j);
				if (mazeMap.containsKey(pos)) {
					System.out.print("M"+mazeMap.get(pos)+"\t");
				} else if(players.containsKey(pos)) {
					System.out.print("P"+players.get(pos).getId()+"\t");
				} else {
					System.out.print("X\t");
				}
			}
			System.out.println();
		}
	}
}
