package multithreading;

import java.util.concurrent.CountDownLatch;

//	EXAMPLE OF COUNTDOWN LATCH WITH SINGLE CORE THREAD PROCESSING, FOR COUNTDOWN LATCH WITH MULTI-CORE CONCURRENT PROCESSING, CHECK CLASS 'FactorialWithConcurrencyWithCountDownLatch'

//	DISADVANTAGE OF COUNTDOWNLATCH: It can only be used once, no reuse possible

//	ALTERNATIVE: USE CYCLICBARRIER - check class: 'FactorialWithCyclicBarrier'
public class FactorialWithCountDownLatch {

	public static void factorial(int i, CountDownLatch latch) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int k = 1, m = i;
		while (i > 0) {
			k *= i--;
		}
		System.out.println("factorial of " + m + ": " + k);
		System.out.println("counting down the latch..");
		latch.countDown(); // decrementing the latch count as current worker thread execution completed
		System.out.println("current latch count: " + latch.getCount());
	}

	public static void main(String[] args) {

		int noOfThreadCountToWaitFor = 10;

		CountDownLatch latch = new CountDownLatch(noOfThreadCountToWaitFor);

		// creating threads with latch object injected
		for (int n = 1; n <= 10; n++) {
			int x = n;
			new Thread(() -> {
				factorial(x, latch);
			}).start();
		}
		try {
			latch.await(); // makes main thread to wait till the latch fully opens up
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
