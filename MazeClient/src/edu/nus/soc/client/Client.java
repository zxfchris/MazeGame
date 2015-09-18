package edu.nus.soc.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Movement;
import edu.nus.soc.model.Player;
import edu.nus.soc.service.impl.Util;

public class Client {
	private static Player player 	= null;
	private static Maze	  maze		= null;
	private static Scanner scanner  = new Scanner(System.in);
	private static Controller controller = null;
	
	public static void main(String[] args) {
		try {
			controller = new Controller();
		} catch (RemoteException | MalformedURLException | NotBoundException e1) {
			e1.printStackTrace();
		}
		
		try {
			player = controller.joinGame();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		if (null == player) {
			System.out.println("Sorry the game has started, please wait for next round.");
		}
		else {
			System.out.println("You have join game successfully, player id:" + player.getId()
					+ " is allocated by system\n Please wait other players to join in...");
			//set player to controller
			controller.setPlayer(player);
		}
		
		while (true) {
			if (true == Controller.isGameStarted()) {
				System.out.println("Please input your choice (S, E, N, W, NOMOVE) "
						+ "or type 'Q' to quit the game:");
				String str = scanner.nextLine();
				if (str.equals("Q")) {
					System.out.println("Player would like to quit game.");
					try {
						controller.quitGame();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.exit(0);
				}
				Movement move = Movement.getMovementByString(str);
				if (null == move)
				{
					System.out.println("Sorry, your input is invalid, please try again.");
				} else {
					try {
						maze = controller.move(move);
						Util.printMaze(maze);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			} else {
				try {
					Thread.sleep(10);	//wait for 10 milliseconds and try again
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
