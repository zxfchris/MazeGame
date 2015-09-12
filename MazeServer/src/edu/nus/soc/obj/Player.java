package edu.nus.soc.obj;

import edu.nus.soc.logic.CallBack;

public class Player {
	private int id;
	private Position pos;
	private int treasureNum;
	public Player(int id) {
		this.id = id;
	}
	
	public int getTreasureNum() {
		return treasureNum;
	}
	public void setTreasureNum(int treasureNum) {
		this.treasureNum = treasureNum;
	}
	public Position getPos() {
		return pos;
	}
	public void setPos(Position pos) {
		this.pos = pos;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void notifiedGameStart(CallBack callback) {
		callback.startGame(this);
		System.out.println("I have been notified that game starts.");
	}
}
