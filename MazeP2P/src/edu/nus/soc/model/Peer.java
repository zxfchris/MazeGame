package edu.nus.soc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Peer implements Serializable{
	
	private static Peer _instance = null;
	private static Object lock = new Object();
	
	private Node  			localNode = new Node();
	//private Node  			primaryServer = new Node(Util.defaultIp,Util.defaultPort);
	//private Node  			secondaryServer;
	private boolean			isPrimaryServer;
	private boolean			isSecondaryServer;
	private List<Node>		nodeList;	//nodes are stored as list, the first node in the list
										//act as primary server, and the second node in the list
										//act as secondary server. when a node become inactive, it
										//will be removed from the list dynamically.
	//private Map<Integer,Node> nodeMap = new HashMap<Integer,Node>();

	public Peer () {
		nodeList = new ArrayList<Node>();
		isPrimaryServer = false;
		isSecondaryServer = false;
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
	
	public Node getLocalNode() {
		return localNode;
	}

	public void setLocalNode(Node localNode) {
		this.localNode = localNode;
	}
	
	public void printNodeList() {
		if (nodeList.size() > 0) {
			System.out.println("primary server is: " + nodeList.get(0).getIp() + ":" + nodeList.get(0).getPort());
		}
		if (nodeList.size() > 1) {
			System.out.println("secondary server is: " + nodeList.get(1).getIp() + ":" + nodeList.get(1).getPort());
		}
		
		System.out.println("----------------------------------");
		for (int index = 0; index < nodeList.size(); index++) {
			System.out.println("No. " + index + " node, ipaddr:" + nodeList.get(index).getIp() + 
					" port:" + nodeList.get(index).getPort());
			System.out.println("----------------------------------");
		}
	}

	public boolean isPrimaryServer() {
		return isPrimaryServer;
	}

	public void setPrimaryServer(boolean isPrimaryServer) {
		this.isPrimaryServer = isPrimaryServer;
	}

	public boolean isSecondaryServer() {
		return isSecondaryServer;
	}

	public void setSecondaryServer(boolean isSecondaryServer) {
		this.isSecondaryServer = isSecondaryServer;
	}
	
	public List<Node> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}
	
	public Node getNodeByPlayerId(Integer playerId) {
		for (int index = 0; index < nodeList.size(); index ++) {
			if (nodeList.get(index).getPlayerId().equals(playerId)) {
				return nodeList.get(index);
			}
		}
		return null;
	}
}
