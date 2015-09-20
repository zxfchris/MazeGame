package edu.nus.soc.model;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Map;

import edu.nus.soc.service.PlayerService;
import edu.nus.soc.service.impl.PlayerServiceImpl;
import edu.nus.soc.util.Util;

public class Peer {
	
	private static Node  localNode = new Node();
	private static Node  primaryServer;
	private static Node  secondaryServer;
	
	private static PlayerService playerService;
	
	private Map<Integer,Node> nodeMap = null;
	
	public Peer () {
		try {
			playerService = new PlayerServiceImpl();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Node getLocalNode() {
		return localNode;
	}

	public static void setLocalNode(Node localNode) {
		Peer.localNode = localNode;
	}

	public static Node getPrimaryServer() {
		return primaryServer;
	}

	public static void setPrimaryServer(Node primaryServer) {
		Peer.primaryServer = primaryServer;
	}

	public static Node getSecondaryServer() {
		return secondaryServer;
	}

	public static void setSecondaryServer(Node secondaryServer) {
		Peer.secondaryServer = secondaryServer;
	}

	public Map<Integer,Node> getNodeMap() {
		return nodeMap;
	}

	public void setNodeMap(Map<Integer,Node> nodeMap) {
		this.nodeMap = nodeMap;
	}
	
	public static boolean isPrimaryServer() {
		if (false == localNode.isMeaningfulAddr()) {
			return false;
		} else if (localNode.equals(primaryServer)){
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isSecondaryServer() {
		if (false == localNode.isMeaningfulAddr()) {
			return false;
		} else if (localNode.equals(secondaryServer)){
			return true;
		} else {
			return false;
		}
	}
	
	public void RegistRMIService() {
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			LocateRegistry.createRegistry(localNode.getPort());
			Naming.rebind(Util.getRMIStringByIpPort(ip, localNode.getPort()), playerService);
		} catch (UnknownHostException | RemoteException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
