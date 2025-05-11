package multithreading;

import java.util.ArrayList;
import java.util.List;

/*
 * DISADVANTAGE OF .JOIN() IS: WE MANUALLY NEED TO ADD .JOIN() METHOD TO EACH WORKER THREAD
 * 
 * ALTERNATIVE: TO USE COUNTDOWNLATCH**
 */

public class FactorialWithJoin {

	public static void factorial(int i) {
		int k = 1, n = i;
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while (i > 0) {
			k *= i--;
		}
		System.out.println("factorial of " + n + ": " + k);
	}

	public static void main(String[] args) {

		List<Thread> threadContainer = new ArrayList<Thread>(); // all newly created threads are stored in this
																// container

		for (int j = 1; j <= 10; j++) {
			int m = j;
			threadContainer.add(new Thread(() -> { // makes thread in new state
				factorial(m);
			}));

		}

		for (Thread t : threadContainer) {
			try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
			t.start(); // starts all the threads in the container
		}

		// NOW TO WAIT FOR ALL THE WORKER THREADS TO COMPLETE, MANUALLY I NEED TO PUT
		// .JOIN .JOIN .JOIN .... METHODS FOR EACH WORKER THREAD, WHICH SEEMS
		// INEFFICIENT

		try {
			threadContainer.get(0).join();
			threadContainer.get(1).join();
			threadContainer.get(2).join();
			threadContainer.get(3).join();
			threadContainer.get(4).join();
			threadContainer.get(5).join();
			threadContainer.get(6).join();
			threadContainer.get(7).join();
			threadContainer.get(8).join();
			threadContainer.get(9).join();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("All threads successfully executed!");
	}

}
