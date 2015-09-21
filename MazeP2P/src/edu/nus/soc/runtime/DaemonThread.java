package edu.nus.soc.runtime;

import java.rmi.RemoteException;

import edu.nus.soc.model.Peer;
import edu.nus.soc.service.PlayerService;
import edu.nus.soc.service.controller.ClientController;

/**
 * daemon thread is used for checking and updating server info
 * @author zhengxifeng
 *
 */
public class DaemonThread implements Runnable {
	
	Thread t;
	PlayerService	primaryService;
	PlayerService	secondaryService;
	
	DaemonThread() {
		t = new Thread(this, "Daemon Thread");
		System.out.println("Daemon Thread:" + t);
		t.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			System.out.println("thread running..." + t);
			try {
				if (Peer.isPrimaryServer()) {
					//if RMI service is not running, register RMI service
					//TODO
					if (null == ClientController.getSecondaryService()) {
						//select a secondary server
						System.out.println();
					} else
						try {
							if (false == ClientController.detectServerAllive(1)) {
								//select a new secondary server
							}
						} catch (RemoteException e) {
							// remote secondary server is crashed, select a new secondary server
							
						}
				} 
				
				else if (Peer.isSecondaryServer()) {
					//if RMI service is not running, register RMI service
					//TODO
					if (null == ClientController.getPrimaryService()) {
						//select a primary server
					} else
						try {
							if (false == ClientController.detectServerAllive(0)) {
								//select a new primary server
							}
						} catch (RemoteException e) {
							// remote primary server is crashed, select a new primary server
						}
					
				} 
				
				else { //this peer is a normal peer, detect server status
					try {
						if (false == ClientController.detectServerAllive(0)) {
							
						}
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try {
						if (false == ClientController.detectServerAllive(1)) {
							
						}
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
