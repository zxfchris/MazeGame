package edu.nus.soc.obj;

import java.io.Serializable;
import java.util.List;

public class Maze implements Serializable{
	private List<Treasure> treasures;
	private List<Player> players;
	private int size;
	private int originalTNum;
	private int treasureNum;
	
	public Maze(int size, int originalTNum) {
		
	}

	public int getTreasureNum() {
		return treasureNum;
	}

	public void setTreasureNum(int treasureNum) {
		this.treasureNum = treasureNum;
	}
	
	
}
