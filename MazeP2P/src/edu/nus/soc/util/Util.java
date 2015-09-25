package edu.nus.soc.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Movement;
import edu.nus.soc.model.Node;
import edu.nus.soc.model.Player;
import edu.nus.soc.model.Position;

public class Util {
	public static int defaultPort = 8888;
	public static int allocatePort = defaultPort;
	public static String defaultIp = "127.0.0.1";
	
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
		Map<Position, Integer> playerMap = getPlayerMap(players);

		for (int j=0; j<maze.getSize(); j++) {
			for (int i=0; i<maze.getSize(); i++) {
				Position pos = new Position(i, j);
				if (mazeMap.containsKey(pos)) {
					System.out.print("T"+mazeMap.get(pos)+"\t");
				} else if(playerMap.containsKey(pos)) {
					System.out.print("P"+players.get(pos).getId()+"\t");
				} else {
					System.out.print("*\t");
				}
			}
			System.out.println();
		}
	}
	
	private static void printPlayer(Player player) {
		System.out.println("Player " + player.getId() + " is in position("
				+ player.getPos().getX() + "," + player.getPos().getY() + ")"
						+ "collected " + player.getTreasureNum() + " treasures.");
	}
	
	public static void printMaze(Integer key, Maze maze) {
		if (maze == null) {
			return;
		}
		
		Map<Position, Integer> 	mazeMap = maze.getTreasureMap();
		Map<Integer, Player> 	players = maze.getPlayers();
		Player					player	= players.get(key);
		Map<Position, Integer> 	playerMap = getPlayerMap(players);
		
		System.out.println("---------------------------------------");
		printPlayer(player);
		System.out.println("---------------------------------------");
		for (int j=0; j<maze.getSize(); j++) {
			for (int i=0; i<maze.getSize(); i++) {
				Position pos = new Position(i, j);
				
				if(playerMap.containsKey(pos)) {
					if (player.getPos().equals(pos)) {
						if (mazeMap.containsKey(pos)) {
							//TODO bug report
							System.out.print("[P"+playerMap.get(pos)+"]T"+mazeMap.get(pos)+"\t");
						} else {
							System.out.print("[P"+playerMap.get(pos)+"]\t");
						}
					} else {
						if (mazeMap.containsKey(pos)) {
							System.out.print("P"+playerMap.get(pos)+"T"+mazeMap.get(pos)+"\t");
						} else {
							System.out.print("P"+playerMap.get(pos)+"\t");
						}
					}
				} else if (mazeMap.containsKey(pos)) {
					System.out.print("T"+mazeMap.get(pos)+"\t");
				} else {
					System.out.print("*\t");
				}
			}
			System.out.println();
		}
		System.out.println("---------------------------------------");
	}
	
	private static Map<Position, Integer> getPlayerMap(Map<Integer, Player> players) {
		Map<Position, Integer> 	playerMap = new HashMap<Position, Integer>();
		
		for (int playerId : players.keySet()) {
			Player p = players.get(playerId);
			Position playerPos = p.getPos();
//			if (playerMap.containsKey(playerPos)) {
//				int cellPlayerNum = playerMap.get(playerPos);
//				playerMap.put(playerPos, cellPlayerNum + 1);
//			} else {
//				playerMap.put(playerPos, 1);
//			}
			playerMap.put(playerPos, playerId);
		}
		return playerMap;
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
			System.out.println("player " + playerIdx + " got " + maze.getPlayers().get(playerIdx).getTreasureNum() + "treasures.");
			if (maze.getPlayers().get(playerIdx).getTreasureNum() > maxTreasureNum) {
				winnerId = playerIdx;
				maxTreasureNum = maze.getPlayers().get(playerIdx).getTreasureNum();
			}
		}
		if (winnerId == playerId 
				|| maze.getPlayers().get(playerId).getTreasureNum() == maze.getPlayers().get(winnerId).getTreasureNum()) {
			return true;
		}
		
		return false;
	}
	
	public static String getRMIStringByIpPort(String ip, int port) {
		return "rmi://"+ ip + ":" + port + "/playerService";
	}
	
	public static String getRMIStringByNode(Node node) {
		if (null == node) {
			return null;
		} else {
			return "rmi://"+ node.getIp() + ":" + node.getPort() + "/playerService";
		}
	}
}
