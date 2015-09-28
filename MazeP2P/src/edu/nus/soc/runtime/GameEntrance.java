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
		if (args.length < 1) {
			System.out.println("Sorry, your input format is wrong.");
			System.exit(0);
		} else {
			if (args[0].equals("-s")) {
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
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (args[0].equals("-c")) {
				//System.out.println("");
			} else {
				System.out.println("Sorry, your input format is wrong.");
				System.exit(0);
			}
		}

		try {
			controller = new ClientController();
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			//e.printStackTrace();
			System.out.println("program execution failure occures, perhaps the "
					+ "remote service has not established yet, please have another try later.");
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

				Position currentPos = player.getPos();
				maze = controller.move(move);
				if (null == maze) {
					System.out.println("Error, move failed.");
				} else {
					controller.update(maze);
					if (player.getPos().equals(currentPos) && move != Movement.NOMOVE) {
						System.out.println("Sorry the cell is occupied by another player, please try again.");
					}
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
