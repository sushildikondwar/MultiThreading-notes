package multithreading;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * EFFICIENT CALCULATION OF ALL THE FACTORIALS - DISTRIBUTED TASKS TO MULTIPLE THREADS
 * FUTURE.GET()
 * EXECUTOR.SHUTDOWN()
 * EXECUTOR.AWAITTERMINATION()
 */
class FactorialWithExecutor {

	public void factorial(int i) {
		int k = i;
		int ans = 1;
		if (i <= 0) {
			return;
		} else {
			while (i > 0) {
				ans = ans * i--;
			}
			System.out.println("factorial of " + k + ": " + ans);
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		FactorialWithExecutor exClass = new FactorialWithExecutor();
		ExecutorService executor = Executors.newFixedThreadPool(2);

		for (int i = 0; i <= 10; i++) {
			System.out.println("calculating factorial of " + i + "..");
			int m;
			m = i;
//			Future<?> abc = executor.submit(() -> {
			executor.submit(() -> {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
				}
				exClass.factorial(m);
			});
//			System.out.println(abc.get());	//	future.get() will return the value obtained by worker thread (in case of Runnable sam, .get() will return null, as Runnable.run() returns void -- check Callable example)
			// USING GET() METHOD LIKE ABOVE IS INAPPROPRIATE!!!! -- BECAUSE, NEXT THREAD
			// WILL GET GENERATED IN NEXT ITERATION, BUT NEXT ITERATION WILL ONLY COME WHEN
			// .GET() METHOD SATISFIES, APPEARENTLY WORKING AS A SINGLE THREAD
			// .GET() METHOD SHOULD NOT BLOCK THE CREATION OF THREADS
			// FOR EACH WORKER THREAD, THERE SHOULD BE ITS OWN .GET() METHOD, UNLIKE ABOVE
			// SCENARIO, WHERE SAME FUTURE IS TAKING VALUE OF ALL THE THREADS, WHICH IS
			// INAPPROPRIATE
		}
		executor.shutdown(); // this only prevents new tasks from being submitted
		executor.awaitTermination(1, TimeUnit.SECONDS); // it tells main thread to wait post shutdown() till execution
														// completes or timeout occurred -- returns 'true' if execution
														// completed, 'false' if timeout occured during execution
		System.out.println("Main thread execution done! (jvm will wait for all other worker threads for completion..)");
	}

}
