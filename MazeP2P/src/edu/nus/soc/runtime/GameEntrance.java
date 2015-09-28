package edu.nus.soc.runtime;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Movement;
import edu.nus.soc.model.Peer;
import edu.nus.soc.model.Player;
import edu.nus.soc.model.Position;
import edu.nus.soc.service.controller.ClientController;
import edu.nus.soc.service.controller.ServerController;
import edu.nus.soc.util.Util;

public class GameEntrance {
	private static Peer	peer = Peer.get();
	private static Maze	maze = null;
	private static Player	player = null;
	private static Scanner scanner  = new Scanner(System.in);
	private static ClientController controller = null;
	final static ServerController serverController = ServerController.getController();
	
	public static void main(String[] args) {
		System.out.println("Peer running...");

		try {
			controller = new ClientController();
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// bootstrap system.
			System.out.println("this peer act as a server initially.");
			peer.getLocalNode().setIp(Util.defaultIp);
			peer.getLocalNode().setPort(Util.defaultPort);
			peer.setPrimaryServer(true);
			peer.setSecondaryServer(false);
			serverController.RegistRMIService();
			try {
				System.out.println("Server starts!");
				int size, originalTNum;
				if (args.length < 3) {
					System.out.println("Please input your maze size N:");
					Scanner scanner = new Scanner(System.in);
					size = scanner.nextInt();
					System.out.println("Please input your original treasure num M:");
					originalTNum = scanner.nextInt();
				} else {
					size = Integer.valueOf(args[0]);
					originalTNum = Integer.valueOf(args[1]);
				}
				Maze maze = serverController.initGame(size, originalTNum);
				Util.printMaze(maze);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				controller = new ClientController();
			} catch (RemoteException | MalformedURLException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		player = controller.joinGame();
		
		if (null == player) {
			System.out.println("Sorry the game has started, please wait for next round.");
			System.exit(0);
		}
		else {
			System.out.println("You have join game successfully, player id:" + player.getId()
					+ " is allocated by system\n Please wait other players to join in...");
			//set player to controller
			controller.setPlayer(player);
		}
		
		while (true) {
			if (true == Maze.get().isGameStarted()) {
				System.out.println("==============================================");
				System.out.println("Please input your choice (S, E, N, W, NOMOVE) "
						+ "or type 'Q' to quit the game:\n->");
				String str = scanner.nextLine();
				if (str.equals("Q")) {
					System.out.println("Player would like to quit game.");
					// quit game.
					controller.quitGame();

					System.exit(0);
				}
				System.out.println("\n\n");
				
				Movement move = Movement.getMovementByString(str);

				maze = controller.move(move);
				if (null == maze) {
					System.out.println("Error, move failed.");
				} else {
					controller.update(maze);
					Util.printMaze(player.getId(), maze);
					System.out.println("remaining treasures in maze: " + maze.getTreasureNum());
					if (0 == maze.getTreasureNum()) {
						if (true == Util.isWinner(player.getId(), maze)) {
							System.out.println("******************************************************\n"
									+ 		   "**        Game over. Congratulations, you win!      **\n"
									+ 		   "******************************************************\n");
						} else {
							System.out.println("******************************************************\n"
									+ 		   "**            Game over. Sorry, you lose.           **\n"
									+ 		   "******************************************************\n");
						}
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
