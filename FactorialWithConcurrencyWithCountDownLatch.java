package multithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * COUNTDOWN LATCH DEMONTRATION WITH WORKER THREADS GETTING PROCESSED IN A THREAD POOL
 * DISADVANTAGE: COUNTDOWN-LATCH CAN ONLY BE USED ONCE, NO RE-USE OF IT
 * 
 * ALTERNATIVE: WE HAVE CYCLIC-BARRIER
 * ALSO, WHAT TO DO, IF WE WANT TO PAUSE ALL THE WORKER THREADS IN BETWEEN SOMEWHERE TILL ALL WORKER DONE THEIR WORK TILL THAT POINT
 * --> WE USE 'CYCLIC-BARRIER'
 */
class WorkerThread implements Runnable {

	public CountDownLatch latch;
	public int factorialOf;

	public WorkerThread(CountDownLatch latch, int k) {
		this.latch = latch; // Common Latch Object Is Shared To All Worker Thread Instances
		this.factorialOf = k;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int m = this.factorialOf;
		int ans = 1;
		// factorial logic
		while (m > 0) {
			ans *= m--;
		}
		System.out.println("factorial of " + this.factorialOf + ": " + ans);
		this.latch.countDown();
		System.out.println("remaining latch count: " + this.latch.getCount());
	}

}

public class FactorialWithConcurrencyWithCountDownLatch {

	public static void main(String[] args) {

		CountDownLatch latch = new CountDownLatch(10);
		ExecutorService executor = Executors.newFixedThreadPool(2);

		for (int i = 1; i <= 10; i++) {
			executor.submit(new WorkerThread(latch, i));
		}
		executor.shutdown();
	}

}
