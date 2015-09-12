package edu.nus.soc.server;

import java.util.List;

import edu.nus.soc.logic.CallBack;
import edu.nus.soc.obj.Player;

public class Server implements CallBack{
	private List<Player> players;
	public Server(List<Player> players) {
		this.players = players;
	}
	/**
	 * The server indicates all players game start.
	 */
	public void callPlayer() {
        new Thread(new Runnable() {  
            @Override  
            public void run() {  
                for (Player p : players) {
                	p.notifiedGameStart(Server.this);
                }
            }  
        }).start();  
	}

	@Override
	public void startGame(Player player) {
		// TODO Auto-generated method stub
		System.out.println("Notify player +"+ player.getId()+" the game starts.");
	}
	
	public static void main(String[] args){
		System.out.println("Server running...");
	}
}
