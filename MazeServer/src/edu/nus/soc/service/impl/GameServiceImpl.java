package edu.nus.soc.service.impl;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Position;
import edu.nus.soc.service.GameService;

public class GameServiceImpl implements GameService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Scanner scanner;

	public GameServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Maze initGame(){
		System.out.println("Please input your maze size N:");
		scanner = new Scanner(System.in);
		int size = scanner.nextInt();
		System.out.println("Please input your original treasure num M:");
		int originalTNum = scanner.nextInt();
		Maze maze = Maze.get();
		maze.init(size, originalTNum);
		System.out.println("Maze size and treasure num are initiated!");
		
		Map<Position, Integer> treasureMap = randomTreasures(originalTNum, size);
		maze.setTreasureMap(treasureMap);
		
		return maze;
	}
	
	/**
	 * initiate treasures randomly
	 * @param originalTNum
	 * @param size
	 * @return
	 */
	private static Map<Position, Integer> randomTreasures(int originalTNum, int size) {
		Map<Position, Integer> treasureMap = new HashMap<Position, Integer>();
		for (int i=0; i<originalTNum; i++) {
			Position pos = Util.getRandomPos(size);
			
			if(treasureMap.containsKey(pos)) {
				int cellTreasure = treasureMap.get(pos);
				treasureMap.put(pos, cellTreasure+1);
			} else {
				treasureMap.put(pos, 1);
			}
		}
		return treasureMap;
	}
	
	@Override
	public void startGame(){
		// TODO Auto-generated method stub
		
	}

}
