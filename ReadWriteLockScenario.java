package multithreading;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * AS IN THE OUTPUT, WE CAN SEE, EVEN THE READ LOCK IS ALREADY BEEN ACQUIRED,
 * OTHER THREADS ARE ALSO SUCCESSFULLY GOT THE READ LOCK. THIS MEANS, IN READ
 * WRITE LOCK, THE READ LOCKS CAN BE ACQUIRED BY MULTIPLE THREADS CONCURRENTLY,
 * AND IF THE TIME COMES WHEN THE READING THREADS RELEASED ALL THE LOCKS, THE
 * WRITE LOCK WILL KICK IN, AND ONCE THE WRITE LOCK GETS ACQUIRED, NO OTHER
 * THREAD CAN ACQUIRE READ OR WRITE LOCK, LIKE IN THE OUTPUT WE CAN SEE, THE
 * THREAD IS COMPLETING WHOLE PROCESS - ACQUIRING LOCK -> PROCESSING ->
 * RELEASING LOCK - IN ONE GO, AND THEN ONLY OTHER THREADS ARE GETTING IN.
 * 
Thread: t1(0) -> Read Lock Acquired..
Thread: t1(0)  -> Read value : 0
Thread: t1(0) -> Read Lock Released..
Thread: t4(0) -> Read Lock Acquired..
Thread: t4(0)  -> Read value : 0
Thread: t4(0) -> Read Lock Released..
Thread: t3(0) -> Write Lock Acquired..
Thread: t3(0) -> Incremented value : 1
Thread: t3(0) -> Write Lock Released..
Thread: t5(0) -> Write Lock Acquired..
Thread: t5(0) -> Incremented value : 2
Thread: t5(0) -> Write Lock Released..
Thread: t1(1) -> Read Lock Acquired..
Thread: t4(1) -> Read Lock Acquired..
Thread: t2(0) -> Read Lock Acquired..
Thread: t1(1)  -> Read value : 2
Thread: t2(0)  -> Read value : 2
Thread: t2(0) -> Read Lock Released..
Thread: t4(1)  -> Read value : 2
Thread: t1(1) -> Read Lock Released..
Thread: t4(1) -> Read Lock Released..
Thread: t3(1) -> Write Lock Acquired..
Thread: t3(1) -> Incremented value : 3
Thread: t3(1) -> Write Lock Released..
Thread: t5(1) -> Write Lock Acquired..
Thread: t5(1) -> Incremented value : 4
Thread: t5(1) -> Write Lock Released..
Thread: t2(1) -> Read Lock Acquired..
Thread: t2(1)  -> Read value : 4
Thread: t2(1) -> Read Lock Released..
Thread: t2(2) -> Read Lock Acquired..
Thread: t4(2) -> Read Lock Acquired..
Thread: t1(2) -> Read Lock Acquired..
Thread: t4(2)  -> Read value : 4
Thread: t4(2) -> Read Lock Released..
Thread: t4(3) -> Read Lock Acquired..
Thread: t4(3)  -> Read value : 4
Thread: t4(3) -> Read Lock Released..
Thread: t4(4) -> Read Lock Acquired..
Thread: t4(4)  -> Read value : 4
Thread: t4(4) -> Read Lock Released..
Thread: t2(2)  -> Read value : 4
Thread: t2(2) -> Read Lock Released..
Thread: t1(2)  -> Read value : 4
Thread: t2(3) -> Read Lock Acquired..
Thread: t1(2) -> Read Lock Released..
Thread: t2(3)  -> Read value : 4
Thread: t1(3) -> Read Lock Acquired..
Thread: t2(3) -> Read Lock Released..
Thread: t1(3)  -> Read value : 4
Thread: t2(4) -> Read Lock Acquired..
Thread: t1(3) -> Read Lock Released..
Thread: t1(4) -> Read Lock Acquired..
Thread: t2(4)  -> Read value : 4
Thread: t2(4) -> Read Lock Released..
Thread: t1(4)  -> Read value : 4
Thread: t1(4) -> Read Lock Released..
 */
public class ReadWriteLockScenario {

	final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

	final ReadLock readLock = lock.readLock();
	final WriteLock writeLock = lock.writeLock();

	private int counter = 0;

	public void getCounter() {
		for (int i = 0; i < 5; i++) { // we are making each thread to acquire read lock and do read operation 5 times
			readLock.lock();
			System.out.println("Thread: " + Thread.currentThread().getName() + "(" + i + ") -> Read Lock Acquired..");
			try {
				System.out.println(
						"Thread: " + Thread.currentThread().getName() + "(" + i + ")  -> Read value : " + this.counter);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out
						.println("Thread: " + Thread.currentThread().getName() + "(" + i + ") -> Read Lock Released..");
				readLock.unlock();
			}
		}
	}

	public void incrementCounter() {
		for (int i = 0; i < 2; i++) { // we are making each thread to acquire write lock and do write operation twice
			writeLock.lock();
			System.out.println("Thread: " + Thread.currentThread().getName() + "(" + i + ") -> Write Lock Acquired..");
			try {
				this.counter++;
				System.out.println("Thread: " + Thread.currentThread().getName() + "(" + i + ") -> Incremented value : "
						+ this.counter);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println(
						"Thread: " + Thread.currentThread().getName() + "(" + i + ") -> Write Lock Released..");
				writeLock.unlock();
			}
		}
	}

	public static void main(String[] str) {

		ReadWriteLockScenario scenario = new ReadWriteLockScenario();

		Runnable run1 = () -> {
			scenario.getCounter();
		};

		Runnable run2 = () -> {
			scenario.incrementCounter();
		};

		Thread t1 = new Thread(run1, "t1");
		Thread t2 = new Thread(run1, "t2");
		Thread t3 = new Thread(run2, "t3");
		Thread t4 = new Thread(run1, "t4");
		Thread t5 = new Thread(run2, "t5");

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();

	}

}
