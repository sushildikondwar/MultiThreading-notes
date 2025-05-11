package multithreading;

/*
 * .JOIN() method makes main thread wait until the corresponding worker thread dies
 * On all the threads manually .join() method needs to be invoked, if 100 threads invoked,
 * then manually 1000 join() invocation needs to be done, which is a DISADVANTAGE
 * 
 * #	To get better taste of it, check class - 'FactorialWithJoin'
 */
public class WaitingForWorkerThreadUsingJOIN {

	public static void main(String[] args) {

		Thread t1 = new Thread(() -> {
			System.out.println("T1 under execution,,");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("T1 execution completed!");
		});

		Thread t3 = new Thread(() -> {
			System.out.println("T3 under execution,,");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("T3 execution completed!");
		});

		Thread t2 = new Thread(() -> {
			System.out.println("T2 under execution,,");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("T2 execution completed!");
		});
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		t1.start();
		t2.start();
		t3.start();

		try {
			t1.join(); // .JOIN() method makes thread to wait till the corresponding thread to execute
						// successfully and dies, it makes main thread to wait
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
