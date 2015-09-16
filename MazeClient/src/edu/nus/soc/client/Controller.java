package edu.nus.soc.client;

import edu.nus.soc.model.Movement;
import edu.nus.soc.model.Player;
import edu.nus.soc.model.Position;

public class Controller {
	private Player player;
	
	public Controller() {
		
	}
	
	private static boolean isMovable(Position pos, Movement m, int size) {
		if (size <= 0) {
			return false;
		}
		boolean flag = true;
		switch (m) {
		case S:
			if (pos.getY()==size) {
				flag = false;
			}
			break;
		case N:
			if (pos.getY()==1) {
				flag = false;
			}
			break;
		case E:
			if (pos.getX()==size) {
				flag = false;
			}
			break;
		case W:
			if (pos.getX()==1) {
				flag = false;
			}
			break;
		case NOMOVE:
			break;
		}
		return flag;
	}
	
	public void move(Movement m) {
		if (this.player == null) {
			System.out.println("The game hasn't started, please wait...");
			return;
		}
		
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
