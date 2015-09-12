package edu.nus.soc.logic;

import edu.nus.soc.obj.Maze;
import edu.nus.soc.obj.Movement;
import edu.nus.soc.obj.Position;

public interface Game {
	Maze initGame();     //initiate maze size and treasures
	void startGame();    //indicate the start of the game
	boolean joinGame(CallBack callback);  //client joins game
	boolean quitGame();  //client quit game
	Position move(Position pos, Movement m);
}
