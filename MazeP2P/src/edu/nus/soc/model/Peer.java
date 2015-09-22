package edu.nus.soc.model;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

import edu.nus.soc.service.PlayerService;
import edu.nus.soc.service.impl.PlayerServiceImpl;
import edu.nus.soc.util.Util;

public class Peer implements Serializable{
	
	private static Peer _instance = null;
	private static Object lock = new Object();
	
	private static Node  			localNode = new Node();
	private static Node  			primaryServer = new Node(Util.defaultIp,Util.defaultPort);
	private static Node  			secondaryServer;
	
	private static Map<Integer,Node> nodeMap = new HashMap<Integer,Node>();
	
	public Peer () {
		
	}
	
	public static Peer get() {
		if(_instance == null) {
			synchronized (lock) {  
                if ( _instance == null )  
                {  
                    return _instance = new Peer();  
                }  
            }  
		}
		return _instance;
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
	
	public static void addNodeToNodeMap(Integer nodeId, Node node) {
		nodeMap.put(nodeId, node);
	}
	
	public static void delNodeFromNodeMap(Integer nodeId) {
		nodeMap.remove(nodeId);
	}
	
	public static int allocateNewPort() {
		return Util.allocatePort ++;
	}
	
	public static void printNodeMap() {
		System.out.println("----------------------------------");
		for (Integer key : nodeMap.keySet()) {
			System.out.println("no." + key + "node, ipaddr:" + nodeMap.get(key).getIp() + 
					" port:" + nodeMap.get(key).getPort());
			System.out.println("----------------------------------");
		}
	}
	
	public Node getNodeById(Integer playerId) {
		return nodeMap.get(playerId);
	}
	
	public boolean detectNodeAllive(Node node) {
		return false;
	}
	
	public Node selectServer() {
		for (Integer key : nodeMap.keySet()) {
			if (false == nodeMap.get(key).equals(primaryServer)) {
				
			}
		}
		return null;
	}
}
