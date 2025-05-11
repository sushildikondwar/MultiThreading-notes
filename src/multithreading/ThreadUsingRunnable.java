package multithreading;

class RunnableThread implements Runnable {
	@Override
	public void run() {
		while (true) {
			System.out.println("I am thread: " + Thread.currentThread().getName());
		}
		
	}
}

public class ThreadUsingRunnable {
	
	public static void main(String [] args) {
		
		//	by doing this t1 will get the info of run method
		Thread t1 = new Thread(new RunnableThread());
		//	start() will run the run() provided in Runnable impl. class
		t1.start();
		
		//	main thread running infinite loop
		while(true)
			System.out.println("I am thread: " + Thread.currentThread().getName());
	}

}
