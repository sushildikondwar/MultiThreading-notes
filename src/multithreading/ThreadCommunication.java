package multithreading;

class Data {
}

class SharedResource {
	static final Object lock = new Object();
	static Data data;
	static boolean isDataAvailable;
}

/*
 * 1. Spurious Wakeups (Minor Vulnerability)
Threads can wake up from wait() even without notify() being called (rare but possible).

You've already handled it correctly by using while loops instead of if while waiting.
✅ So this is not a vulnerability in your case, but you must always use while for wait conditions, which you did.
 */

class Producer implements Runnable {
	@Override
	public void run() {
		int counter = 0;
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (SharedResource.lock) {

				while (SharedResource.isDataAvailable) { // loop will break once data is unavailable and wait for
															// consumer to consume and notify, once consumer notifies
															// waits for producer to produce, producer again produces
															// data and notifies consumer to release wait lock
					try {
						SharedResource.lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				SharedResource.data = new Data();
				System.out.println("Produced Data D" + (++counter));
				SharedResource.isDataAvailable = true;
				SharedResource.lock.notifyAll(); // notify consumers
			}
		}
	}
}

class Consumer implements Runnable {
	@Override
	public void run() {
		int counter = 0;
		while (true) {
			synchronized (SharedResource.lock) {
				while (!SharedResource.isDataAvailable) {
					try {
						SharedResource.lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				SharedResource.lock.notifyAll();
				System.out.println("Consumed Data D" + (++counter) + ": " + SharedResource.data);
				SharedResource.isDataAvailable = false;
			}
		}
	}
}

public class ThreadCommunication {
	public static void main(String[] args) {
		Thread producer = new Thread(new Producer());
		Thread consumer = new Thread(new Consumer());

		producer.start();
		consumer.start();
	}
}

/**
 * Synchronize Only What’s Necessary: Use synchronization only on the part of
 * the code that accesses shared resources. Avoid wrapping an entire method or
 * loop unnecessarily.
 * 
 * Solution: Focus synchronization on critical sections, not the entire method.
 * 
 * Avoid Deadlock: Be cautious when locking multiple resources. If multiple
 * threads are waiting on multiple locks, it can lead to a deadlock.
 * 
 * Solution: Ensure locks are acquired in the same order, or use a tryLock
 * approach.
 * 
 * Use wait()/notify() Inside the synchronized block: Ensure wait() and notify()
 * are used within synchronized blocks to avoid improper thread management.
 * 
 * Solution: Always call wait()/notify() inside synchronized(lock) blocks to
 * control the thread flow properly.
 * 
 * Don’t Hold Locks Too Long: Avoid long-running operations inside synchronized
 * blocks, which can lead to thread starvation.
 * 
 * Solution: Keep synchronized sections short and release the lock as soon as
 * possible.
 * 
 * Use a Single Lock Object: Ensure that all threads share the same lock object
 * (e.g., SharedResource.lock) for synchronized blocks. Avoid using different
 * lock objects for the same shared resource.
 * 
 * Solution: Use a common lock object to synchronize on shared resources to
 * ensure proper coordination.
 */