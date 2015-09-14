package edu.nus.soc.service;

import edu.nus.soc.model.Maze;

public interface GameService {
	Maze initGame();     //initiate maze size and treasures
	
	void startGame();    //indicate the start of the game
	
}
