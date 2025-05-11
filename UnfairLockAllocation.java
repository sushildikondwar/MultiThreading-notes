package multithreading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * UNFAIR LOCKING - REENTRENTLOCK (FALSE) -- by default, reentrant lock works in
 * unfair mode IN THIS CASE, THE OTHER THREAD WILL BE WAITING AT RUNNABLE STATE
 * WHILE RUNNING THE FIRST THREAD (T1) -- BUT WHEN T1 RELEASES THE LOCK, T1
 * ITSELF WILL GO INTO THE RUNNABLE STATE AND WILL BE READY TO GET EXECUTED, AND
 * SCHEDULAR MIGHT PICK UP 'T1' THREAD AGAIN. THREADS MAY FINISH THEIR CRITICAL
 * SECTION AT DIFFERENT TIMES, THIS MIGHT LEADS TO OTHER THREADS TO STARV. IF T1
 * IS DOING SOME COSTLY EXECUTION AND IF T1 IS GIVEN THE HIGHEST THREAD
 * PRIORITY, THEN OTHER THREADS WILL BADLY AFFECT.
 * 
 * OUTPUT
Thread t1: lock acquired
Thread t1 lock released
Thread t1: lock acquired
Thread t1 lock released
Thread t2: lock acquired
Thread t2 lock released
Thread t3: lock acquired
Thread t3 lock released
Thread t4: lock acquired
Thread t4 lock released
Thread t4: lock acquired
Thread t4 lock released
Thread t2: lock acquired
Thread t2 lock released
Thread t2: lock acquired
Thread t2 lock released
Thread t3: lock acquired
Thread t3 lock released
Thread t3: lock acquired
Thread t3 lock released
Thread t4: lock acquired
Thread t4 lock released
Thread t1: lock acquired
Thread t1 lock released
 *
 */

class Task extends Thread {

	final Lock lock;

	public Task(Lock lock, String threadName) {
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

public class UnfairLockAllocation {

	public static void main(String[] str) {

		Lock lock = new ReentrantLock(); // UNFAIR LOCKING

		Thread t1 = new Task(lock, "t1");
		Thread t2 = new Task(lock, "t2");
		Thread t3 = new Task(lock, "t3");
		Thread t4 = new Task(lock, "t4");

		t1.start();
		t2.start();
		t3.start();
		t4.start();

	}

}
