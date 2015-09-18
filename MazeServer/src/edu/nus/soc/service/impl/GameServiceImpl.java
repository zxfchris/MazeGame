package edu.nus.soc.service.impl;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Player;
import edu.nus.soc.model.Position;
import edu.nus.soc.service.GameService;

public class GameServiceImpl implements GameService{

	private Scanner scanner;

	public GameServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public Maze initGame(){
		System.out.println("Please input your maze size N:");
		scanner = new Scanner(System.in);
		int size = scanner.nextInt();
		System.out.println("Please input your original treasure num M:");
		int originalTNum = scanner.nextInt();
		Maze maze = Maze.get();
		maze.setSize(size);
		maze.setOriginalTNum(originalTNum);
		maze.setTreasureNum(originalTNum);
		System.out.println("Maze size and treasure num are initiated!");
		
		Map<Position, Integer> treasureMap = Util.randomTreasures(originalTNum, size);
		maze.setTreasureMap(treasureMap);
		
		maze.setPlayers(new HashMap<Integer, Player>());
		
		return maze;
	}
	
	@Override
	public void startGame(){
		// TODO Auto-generated method stub
		Maze maze = Maze.get();
		
	}

}
