package edu.nus.soc.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import edu.nus.soc.service.CallBackService;

public class GamePlayer extends UnicastRemoteObject implements CallBackService{
	private int playerId;
	
	public GamePlayer(int playerId) throws RemoteException {
		super(playerId);
		this.playerId = playerId;
	}

	@Override
	public void startGame() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
