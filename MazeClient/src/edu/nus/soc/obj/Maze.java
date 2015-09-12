package edu.nus.soc.obj;

import java.util.List;

public class Maze {
	private List<Treasure> treasures;
	private List<Player> players;
	private int size;
	private int originalTNum;
	private static int currentPlayerId = 1;
	private static int treasureNum;
	public Maze(int size, int originalTNum) {
		
	}

	public int getTreasureNum() {
		return treasureNum;
	}

	public void setTreasureNum(int treasureNum) {
		this.treasureNum = treasureNum;
	}
	
	private int getAvailableTreasures() {
		int currentNum = treasures.size();
		for (Treasure t : treasures) {
			
		}
		return currentNum;
	}
}
