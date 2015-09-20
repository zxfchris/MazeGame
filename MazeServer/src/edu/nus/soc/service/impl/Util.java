package edu.nus.soc.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Player;
import edu.nus.soc.model.Position;

public class Util {
	
	public static Position getRandomPos(int size) {
		Random	rand = new Random();
		int x = rand.nextInt(size);
		int y = rand.nextInt(size);
		Position pos = new Position(x, y);
		return pos;
	}

	public static void printMaze(Maze maze) {
		Map<Position, Integer> 	mazeMap = maze.getTreasureMap();
		Map<Integer, Player> 	players = maze.getPlayers();

		for (int j=0; j<maze.getSize(); j++) {
			for (int i=0; i<maze.getSize(); i++) {
				Position pos = new Position(i, j);
				if (mazeMap.containsKey(pos)) {
					System.out.print("T"+mazeMap.get(pos)+"\t");
				} else if(players.containsKey(pos)) {
					System.out.print("P"+players.get(pos).getId()+"\t");
				} else {
					System.out.print("X\t");
				}
			}
			System.out.println();
		}
	}

	/**
	 * initiate treasures randomly
	 * @param originalTNum
	 * @param size
	 * @return
	 */
	public static Map<Position, Integer> randomTreasures(int originalTNum, int size) {
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
	
}
