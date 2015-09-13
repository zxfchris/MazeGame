package edu.nus.soc.model;

import java.io.Serializable;

public class Position implements Serializable{
	private static int x;
	private static int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public boolean equals(Object pos2) {
		if (pos2 == null) {
			return false;
		}
		if (pos2.getClass() != Position.class) {
			return false;
		}
		if (((Position) pos2).getX()==this.x && ((Position) pos2).getY()==this.y) {
			return true;
		}
		return false;
	}
}
