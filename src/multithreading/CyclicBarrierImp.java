package multithreading;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * KEY RULE For CyclicBarrier to work, **thread pool size ≥ barrier count (number of tasks)** -- RCA PROVIDED BELOW.
 */

class Add implements Runnable {
	CyclicBarrier barrier;
	int addTill;

	public Add(CyclicBarrier barrier, int i) {
		this.barrier = barrier;
		this.addTill = i;
	}

	@Override
	public void run() {
		int ans = 0;
		for (int k = 1; k <= this.addTill; k++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ans += k;
		}
		System.out.println("Adder thread waiting for other threads at barrier..");
		try {
			this.barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("#-> Adder thread result: " + ans);
	}
}

class Subtract implements Runnable {
	CyclicBarrier barrier;
	int subtractTill;

	public Subtract(CyclicBarrier barrier, int i) {
		this.barrier = barrier;
		this.subtractTill = i;
	}

	@Override
	public void run() {
		int ans = 0;
		for (int k = 1; k <= this.subtractTill; k++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ans -= k;
		}
		System.out.println("Subtractor thread waiting for other threads at barrier..");
		try {
			this.barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println("#-> Subtractor thread result: " + ans);
	}
}

class Multiply implements Runnable {
	CyclicBarrier barrier;
	int myltiplyTill;

	public Multiply(CyclicBarrier barrier, int i) {
		this.barrier = barrier;
		this.myltiplyTill = i;
	}

	@Override
	public void run() {
		int ans = 1;
		for (int k = 1; k <= this.myltiplyTill; k++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ans *= k;
		}
		System.out.println("Multiplier thread waiting for other threads at barrier..");
		try {
			this.barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println("#-> multiplier thread result: " + ans);
	}
}

class Divide implements Runnable {
	CyclicBarrier barrier;
	int divideTill;

	public Divide(CyclicBarrier barrier, int i) {
		this.barrier = barrier;
		this.divideTill = i;
	}

	@Override
	public void run() {
		double ans = 51;
		for (int k = 1; k <= this.divideTill; k++) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ans /= k;
		}
		System.out.println("Divider thread waiting for other threads at barrier..");
		try {
			this.barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println("#-> divider thread result: " + ans);
	}
}

public class CyclicBarrierImp {

	public static void main(String[] args) {

		CyclicBarrier barrier = new CyclicBarrier(4); // allocated 4 barriers - each for add, sub, mul, div

//		ExecutorService executor = Executors.newFixedThreadPool(3);	//	IN THIS CASE, DIVIDE THREAD WON'T PICKED UP BY THREAD IN THREAD POOL, RCA IS BELOW:
		/*
		 * Thread pool has only 3 threads. First 3 tasks (Add, Subtract, Multiply) start
		 * and wait at the barrier. Divide task isn’t picked up as all threads are
		 * blocked at await(). Barrier needs 4 threads but only 3 reached — deadlock.
		 * Divide stays in the queue and never runs.
		 */

		ExecutorService executor = Executors.newFixedThreadPool(4);	//	ITS MANDATORY TO RESERVE THREADS ATLEAST EQUAL TO NO. OF TASKS - in case of cyclic barrier
		executor.submit(new Add(barrier, 20));
		executor.submit(new Subtract(barrier, 10));
		executor.submit(new Multiply(barrier, 15));
		executor.submit(new Divide(barrier, 10));

		barrier.reset();	//	THIS IS USED TO APPLY BARRIER TO NEXT SET OF TASK-BUNCH -- barrier is reusable :)
		
		executor.shutdown();

	}

}
