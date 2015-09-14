package edu.nus.soc.service.impl;

import java.util.Random;

import edu.nus.soc.model.Position;

public class Util {
	
	public static Position getRandomPos(int size) {
		Random	rand = new Random();
		int x = rand.nextInt(size);
		int y = rand.nextInt(size);
		Position pos = new Position(x, y);
		return pos;
	}

}
