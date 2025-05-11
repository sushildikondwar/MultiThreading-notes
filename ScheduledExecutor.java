package multithreading;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutor {

	public static void main(String[] args) {

		ScheduledExecutorService scheduledExecutor = new ScheduledThreadPoolExecutor(2);

		//	SCHEDULED AT FIXED RATE
		scheduledExecutor.scheduleAtFixedRate(() -> { // THIS WILL TRIGGER THE PROCESSING OF TASK IRRESPECTIVE OF THE
														// PREVIOUS PROCESSING STATUS, GIVES PRIORITY TO TIME INSTANCES
														// -- IF PREVIOUS TASK IS STILL PENDING, THEN IT CAN CONTINUE
														// EXECUTING WITH FURTHER COMING PROCESSINGS, BUT IT WON'T BE
														// OVERLAPPING, IT WILL WORK AS SINGLE THREAD WORK AS IF POOL
														// HAS ONLY ONE THREAD RESERVED, ORELSE CAN BE RUN CONCURRENTLY
														// IF MULTIPLE THREADS RESERVED IN THREAD-POOL
			try {
				System.out.println("waiting-2s");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Hello!");
		}, 0, 1, TimeUnit.SECONDS);

		//	SCHEDULED WITH FIXED DELAY
		scheduledExecutor.scheduleWithFixedDelay(() -> { // THIS WILL WAIT FOR THE PROCESS TO EXECUTE FULLY, AND THEN IT
															// TAKES A DELAYED BREAK AS STATED, THEN NEXT ITERATION WILL
															// GETS PROCEEDED
			try {
				System.out.println("waiting-2s");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("World..");
		}, 0, 500, TimeUnit.MILLISECONDS);
		
		scheduledExecutor.close();
	}
}
