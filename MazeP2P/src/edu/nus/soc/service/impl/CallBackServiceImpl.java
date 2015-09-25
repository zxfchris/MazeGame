package edu.nus.soc.service.impl;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import edu.nus.soc.util.Util;
import edu.nus.soc.model.Maze;
import edu.nus.soc.model.Peer;
import edu.nus.soc.service.CallBackService;
import edu.nus.soc.service.controller.ClientController;
import edu.nus.soc.service.controller.ServerController;

public class CallBackServiceImpl extends UnicastRemoteObject implements CallBackService{

	public CallBackServiceImpl() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void notifyGameStart(Integer playerId, Maze maze, Peer peer) throws RemoteException {
		System.out.println("Game started, now you can move!");
		
		Util.printMaze(playerId, maze);
		
		Maze.get().setMaze(maze);
		Maze.get().setGameStarted(true);
		
		peer.printNodeList();
		//set up RMI service
		Peer.get().setNodeList(peer.getNodeList());
		Peer.get().setLocalNode(peer.getNodeByPlayerId(playerId));
		System.out.println("playerId:" + playerId + "nodeListSize:" + Peer.get().getNodeList().size());
		if (null == Peer.get().getNodeByPlayerId(playerId)) {
			System.err.println("cannot find player node.");
		} else {
			if (Peer.get().getLocalNode().getPort() != Util.defaultPort) {
				ServerController.RegistRMIService();
			}
		}
		ClientController.updatePlayerService();
	}


	@Override
	public void notifySelectedAsServer(Maze maze, Peer peer) throws RemoteException {
		System.out.println("this peer is selected as secondary server.");
		//peer should register RMI service dynamically
		Maze.get().setMaze(maze);
		Peer.get().setNodeList(peer.getNodeList());
		Peer.get().setPrimaryServer(false);
		Peer.get().setSecondaryServer(true);
		ClientController.updatePlayerService();
	}

}
