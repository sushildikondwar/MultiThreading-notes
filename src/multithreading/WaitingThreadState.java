package multithreading;

class NotifyWaitRelease {
	
	//	this method invocation will make thread to wait for a signal
	public synchronized void waitingMethod() {
		try {
			System.out.println("Thread: " + Thread.currentThread().getName() + " --> going in waiting phase till other thread notifies it");
			wait();
			System.out.println("Thread: " + Thread.currentThread().getName() + " --> got notified");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void notifyWaitingThread() {
		System.out.println("Thread: " + Thread.currentThread().getName() + " --> notifying other thread");
		notify();
		System.out.println("Thread: " + Thread.currentThread().getName() + " --> notification sent");
	}
	
}

class MakeWaitThread extends Thread {
	NotifyWaitRelease nwr;
	public MakeWaitThread(NotifyWaitRelease nwr) {
		super("t1");
		this.nwr = nwr;
	}
	
	@Override
	public void run() {
		this.nwr.waitingMethod();
	}
}

class NotifyThread extends Thread {
	NotifyWaitRelease nwr;
	public NotifyThread(NotifyWaitRelease nwr) {
		super("t2");
		this.nwr = nwr;
	}
	
	@Override
	public void run() {
		this.nwr.notifyWaitingThread();
	}
}

public class WaitingThreadState {
	
	public static void main(String[] str) {
		NotifyWaitRelease nwr = new NotifyWaitRelease();
		
		Thread t1 = new MakeWaitThread(nwr);	//	thread responsible to wait
		t1.start();
		
		try {
			Thread.sleep(1000);	//	pausing main thread and giving time to t1 to get into execution and acquire wait
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("current state of t1: " + t1.getState());
		
		Thread t2 = new NotifyThread(nwr);	//	thread responsible to notify waiting thread to proceed
		t2.start();
	}
	

}
