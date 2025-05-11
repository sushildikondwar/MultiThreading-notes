package multithreading;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorList {

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(3);
		Runnable a = () -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("thread: a");
		};
		Runnable b = () -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("thread: b");
		};
		Runnable c = () -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("thread: c");
		};
		System.out.println("List of Runnables getting generated!");
		List<Runnable> runnableList = Arrays.asList(a, b, c);
		System.out.println("List of Runnables generated successfully!");

		List<Callable<Void>> callableList = new ArrayList<>();
		System.out.println("List of Callables generated successfully!");

//		NOTE: RUNNABLES ARE NOT ALLOWED FOR INVOKING ALL AT ONCE
		for (Runnable r : runnableList) {
			callableList.add(Executors.callable(r, null)); // .CALLABLE() will convert runnable to callable with a
															// specific return value 'null' in current scenario
		}

		List<Future<Void>> futures = null;

		try {
			futures = executor.invokeAll(callableList); // Immediately invokes all the worker threads for processing,
														// and simultaneously generated the values
			// Note that a completed task could have terminated either normally or by
			// throwing an exception
			System.out.println("All callables got invoked!");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Printing futures (callable outputs)..");

		for (Future<Void> f : futures) {
			try {
				f.get(); // .GET() will wait till all the execution are done
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		executor.shutdown();
	}

}
