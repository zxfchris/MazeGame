package edu.nus.soc.model;

import java.net.InetSocketAddress;

public class Node {

	private String ip = null;
	private int port = 0;
	
	public Node(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
	}
	public Node() {
		// TODO Auto-generated constructor stub
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public static String rmiAddressConstruct(int ip, int port) {
		return null;
	}
	
	public static String ipToString() {
		return null;
	}	
	
	public boolean isMeaningfulAddr() {
		if (ip.equals("") || 0 == port) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + port;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (port != other.port)
			return false;
		return true;
	}
}
