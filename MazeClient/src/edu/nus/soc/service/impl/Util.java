package edu.nus.soc.service.impl;

import java.util.HashMap;
import java.util.Map;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Movement;
import edu.nus.soc.model.Player;
import edu.nus.soc.model.Position;

public class Util {
	
	private static void printPlayer(Player player) {
		System.out.println("Player " + player.getId() + " is in position("
				+ player.getPos().getX() + "," + player.getPos().getY() + ")"
						+ "collected " + player.getTreasureNum() + " treasures.");
	}
	
	public static void printMaze(Integer key, Maze maze) {
		Map<Position, Integer> 	mazeMap = maze.getTreasureMap();
		Map<Integer, Player> 	players = maze.getPlayers();
		Map<Position, Integer> 	playerMap = new HashMap<Position, Integer>();
		Player					player	= players.get(key);
		
		for (int playerId : players.keySet()) {
			Player p = players.get(playerId);
			Position playerPos = p.getPos();
			if (playerMap.containsKey(playerPos)) {
				int cellPlayerNum = playerMap.get(playerPos);
				playerMap.put(playerPos, cellPlayerNum++);
			} else {
				playerMap.put(playerPos, 1);
			}
		}

		printPlayer(player);
		for (int j=0; j<maze.getSize(); j++) {
			for (int i=0; i<maze.getSize(); i++) {
				Position pos = new Position(i, j);
				if (mazeMap.containsKey(pos)) {
					System.out.print("T"+mazeMap.get(pos)+"\t");
				} else if(playerMap.containsKey(pos)) {
					if (players.get(key).getPos().equals(pos)) {
						System.out.print("[P"+playerMap.get(pos)+"]\t");
					} else {
						System.out.print("P"+playerMap.get(pos)+"\t");
					}
				} else {
					System.out.print("X\t");
				}
			}
			System.out.println();
		}
	}
	
	public static boolean isMovable(Position pos, Movement m, int size) {
		if (size <= 0) {
			return false;
		}
		boolean flag = true;
		switch (m) {
		case S:
			if (pos.getY() == size - 1) {
				flag = false;
			}
			break;
		case N:
			if (pos.getY() == 0) {
				flag = false;
			}
			break;
		case E:
			if (pos.getX() == size - 1) {
				flag = false;
			}
			break;
		case W:
			if (pos.getX() == 0) {
				flag = false;
			}
			break;
		case NOMOVE:
			break;
		default:
			flag = false;
			break;
		}
		return flag;
	}
	
	public static boolean isWinner(Integer playerId, Maze maze) {
		if (0 == maze.getPlayers().size()) {
			return false;
		}
		int maxTreasureNum = 0;
		int	winnerId = 0;
		for (Integer playerIdx : maze.getPlayers().keySet()) {
			if (maze.getPlayers().get(playerIdx).getTreasureNum() > maxTreasureNum)
			{
				winnerId = playerIdx;
			}
		}
		if (winnerId == playerId 
				|| maze.getPlayers().get(playerId).getTreasureNum() == maze.getPlayers().get(winnerId).getTreasureNum()) {
			return true;
		}
		return false;
	}
}
