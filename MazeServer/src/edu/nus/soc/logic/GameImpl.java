package edu.nus.soc.logic;

import edu.nus.soc.obj.Maze;
import edu.nus.soc.obj.Movement;
import edu.nus.soc.obj.Position;

public class GameImpl implements Game{

	@Override
	public Maze initGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean joinGame(CallBack callback) {
		// TODO Auto-generated method stub
		System.out.println("Recieved client's request to join into a game.");
		return false;
	}

	@Override
	public boolean quitGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Position move(Position pos, Movement m) {
		// TODO Auto-generated method stub
		return null;
	}

}
