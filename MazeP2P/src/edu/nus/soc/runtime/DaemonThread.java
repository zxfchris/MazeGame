package edu.nus.soc.runtime;

import edu.nus.soc.model.Peer;

/**
 * daemon thread is used for checking and updating server info
 * @author zhengxifeng
 *
 */
public class DaemonThread implements Runnable {
	
	Thread t;
	Peer   peer;
	
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
				if (Peer.isPrimaryServer() || Peer.isSecondaryServer()) {
					
				} else {
					
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
