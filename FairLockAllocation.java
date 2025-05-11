package multithreading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * UNFAIR LOCKING - REENTRENTLOCK (TRUE) -- reentrant lock working in fair mode
 * IN THIS CASE, THE OTHER THREAD WILL GET EQUAL CHANCE AFTER ONE THREAD
 * COMPLETES EXECUTION. IT MAINTAINS INTERNAL QUEUE OF WAITING THREADS. LIKE
 * THIS THE THREAD WAITING LONGER WILL GET THE LOCK. IT ENSURES SAME THREAD IS
 * NOT BEEN CHOOSEN BY SCHEDULAR FOR EXECUTION. THE ORDER IN WHICH THE THREAD
 * GOT CREATED IS STORED IN THE QUEUE AND SIMILAR TO ROUND ROBIN, EACH THREAD
 * WILL BE EXECUTED ONE ROUND OF EXECUTION, THEN OTHER, AND THEN NEXT TO IT,
 * ETC. THIS INCREASES THE FAIRNESS, REDUCES THE THREAD STARVATION. THIS
 * PREVENTS ANY SINGLE THREAD FROM MONOPOLIZING THE LOCK AND ENSURES
 * PREDICTABLE, STARVATION-FREE ACCESS.
 * 
 * OUTPUT Thread t1: lock acquired Thread t1 lock released Thread t2: lock
 * acquired Thread t2 lock released Thread t3: lock acquired Thread t3 lock
 * released Thread t4: lock acquired Thread t4 lock released Thread t1: lock
 * acquired Thread t1 lock released Thread t2: lock acquired Thread t2 lock
 * released Thread t3: lock acquired Thread t3 lock released Thread t4: lock
 * acquired Thread t4 lock released Thread t1: lock acquired Thread t1 lock
 * released Thread t2: lock acquired Thread t2 lock released Thread t3: lock
 * acquired Thread t3 lock released Thread t4: lock acquired Thread t4 lock
 * released
 * 
 */

class Task1 extends Thread {

	final Lock lock;

	public Task1(Lock lock, String threadName) {
		super(threadName);
		this.lock = lock;
	}

	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			try {
				this.lock.lock();
				System.out.println("Thread " + Thread.currentThread().getName() + ": lock acquired");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("Thread " + Thread.currentThread().getName() + " lock released");
				this.lock.unlock();
			}
		}
	}
}

public class FairLockAllocation {

	public static void main(String[] str) {

		Lock lock = new ReentrantLock(true); // FAIR LOCKING

		Thread t1 = new Task1(lock, "t1");
		Thread t2 = new Task1(lock, "t2");
		Thread t3 = new Task1(lock, "t3");
		Thread t4 = new Task1(lock, "t4");

		t1.start();
		t2.start();
		t3.start();
		t4.start();

	}

}
