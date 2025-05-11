package multithreading;

class BlockedThreadClass {
	public synchronized void infiniteLoopProcessing() {
		while (true) {
			
		}
	}
}

class NewThreadAccessingBlockedThreadInstance extends Thread {
	private BlockedThreadClass btc;
	
	public NewThreadAccessingBlockedThreadInstance(BlockedThreadClass btc) {
		this.btc = btc;
	}
	
	@Override
	public void run() {
		this.btc.infiniteLoopProcessing();
	}
}

public class BlockedThreadState {
	
	public static void main(String[] str) {
		BlockedThreadClass btc = new BlockedThreadClass();
		
		//	takes instance and access synchronized method running infinite loop
		Thread t1 = new NewThreadAccessingBlockedThreadInstance(btc);
		t1.start();
		System.out.println("State of first thread: " + t1.getState());
		
		//	giving time to get t1 in running state
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//	new thread operates on same instance and tries to access a method which is being already operated by the t1 thread
		Thread t2 = new NewThreadAccessingBlockedThreadInstance(btc);
		t2.start();
		//	letting thread-2 to get into the execution phase
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//	t2 is waiting for t1 to complete its operation on synchronized method
		System.out.println("State of second thread: " + t2.getState());
		
	}

}
