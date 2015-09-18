package edu.nus.soc.model;

import java.io.Serializable;
import java.util.Map;

public class Maze implements Serializable{
	private static Maze _instance = null;
	private static Object lock = new Object();
	
	private int currentId = 0;	
	private Map<Position, Integer> treasureMap;
	private Map<Integer, Player> players;
	private int size;
	private int originalTNum;
	private int treasureNum;
	
	private Maze() {
		
	}
	
	public static Maze get() {
		if(_instance == null) {
			synchronized (lock) {  
                if ( _instance == null )  
                {  
                    return _instance = new Maze();  
                }  
            }  
		}
		return _instance;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getOriginalTNum() {
		return originalTNum;
	}

	public void setOriginalTNum(int originalTNum) {
		this.originalTNum = originalTNum;
	}

	public int getTreasureNum() {
		return treasureNum;
	}

	public void setTreasureNum(int treasureNum) {
		this.treasureNum = treasureNum;
	}

	public Map<Position, Integer> getTreasureMap() {
		return treasureMap;
	}

	public void setTreasureMap(Map<Position, Integer> treasureMap) {
		this.treasureMap = treasureMap;
	}

	public int getCurrentId() {
		return currentId;
	}

	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}

	public Map<Integer, Player> getPlayers() {
		return players;
	}

	public void setPlayers(Map<Integer, Player> players) {
		this.players = players;
	}

}
