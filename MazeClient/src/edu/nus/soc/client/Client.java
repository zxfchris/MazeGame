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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			/**
			 * join game.
			 */
			player = controller.joinGame();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
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
						/**
						 * quit game.
						 */
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
				} else if (false == Util.isMovable(controller.getPlayer().getPos(), move, Controller.getMaze().getSize()) ) {
					System.out.println("Sorry, you have reached the boundary, please try again.");
				} else {
					try {
						/**
						 * move.
						 */
						maze = controller.move(move);
						
						controller.update(maze);
						Util.printMaze(player.getId(), maze);
						System.out.println("treasures in maze: " + Controller.getMaze().getTreasureNum());
						if (0 == Controller.getMaze().getTreasureNum())
						{
							if (Util.isWinner(player.getId(), maze)) {
								System.out.println("******************************************************\n"
										+ 		   "**        Game over. Congratulations, you win!      **\n"
										+ 		   "******************************************************\n");
							}
							else {
								System.out.println("******************************************************\n\n"
										+ 		   "**            Game over. Sorry, you lose.           **\n"
										+ 		   "******************************************************\n");
							}
						}
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				try {
					Thread.sleep(10);	//wait for 10 milliseconds and try again
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
