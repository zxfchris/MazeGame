package edu.nus.soc.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import edu.nus.soc.service.GameService;
import edu.nus.soc.service.impl.GameServiceImpl;

public class Server {
	
	public static void main(String[] args){
		try {
			GameService gameService = new GameServiceImpl();
			LocateRegistry.createRegistry(8888);
			Naming.rebind("rmi://127.0.0.1:8888/GameService", gameService);
			System.out.println("Server starts!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
