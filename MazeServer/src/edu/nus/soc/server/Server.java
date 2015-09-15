package edu.nus.soc.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import edu.nus.soc.model.Maze;
import edu.nus.soc.service.GameService;
import edu.nus.soc.service.PlayerService;
import edu.nus.soc.service.impl.GameServiceImpl;
import edu.nus.soc.service.impl.PlayerServiceImpl;
import edu.nus.soc.service.impl.Util;

public class Server {
	
	public static void main(String[] args){
		try {
			PlayerService playerService = new PlayerServiceImpl();
//			final GameService gameService = new GameServiceImpl();
			LocateRegistry.createRegistry(8888);
			Naming.rebind("rmi://127.0.0.1:8888/playerService", playerService);
			System.out.println("Server starts!");
//			Maze maze = gameService.initGame();
//			Util.printMaze(maze);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
