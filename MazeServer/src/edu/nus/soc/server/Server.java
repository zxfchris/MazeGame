package edu.nus.soc.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

import edu.nus.soc.model.Maze;
import edu.nus.soc.service.PlayerService;
import edu.nus.soc.service.impl.GameController;
import edu.nus.soc.service.impl.PlayerServiceImpl;
import edu.nus.soc.service.impl.Util;

public class Server {
	
	public static void main(String[] args){
		try {
			PlayerService playerService = new PlayerServiceImpl();
			final GameController gameController = GameController.getController();
			LocateRegistry.createRegistry(8888);
			Naming.rebind("rmi://127.0.0.1:8888/playerService", playerService);
			System.out.println("Server starts!");
			int size, originalTNum;
			if (args.length < 2) {
				System.out.println("Please input your maze size N:");
				Scanner scanner = new Scanner(System.in);
				size = scanner.nextInt();
				System.out.println("Please input your original treasure num M:");
				originalTNum = scanner.nextInt();
			} else {
				size = Integer.valueOf(args[0]);
				originalTNum = Integer.valueOf(args[1]);
			}
			Maze maze = gameController.initGame(size, originalTNum);
			Util.printMaze(maze);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
