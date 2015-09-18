package edu.nus.soc.service.impl;

import java.util.HashMap;
import java.util.Map;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Player;
import edu.nus.soc.model.Position;

public class Util {

	public static void printMaze(Maze maze) {
		if (maze == null) {
			return;
		}
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
					System.out.print("T"+mazeMap.get(pos)+"\t");
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
