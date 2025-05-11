package multithreading;

class NewThread extends Thread {
	public NewThread() {
		super("New-1");
	}
	
	//	provide the task to new thread here
	public void run() {
		while (true) {
			System.out.println("I am thread: " + currentThread().getName());
		}
	}
}

public class RunInfiniteLoopsOnMultipleThreads {
	
	
	public static void main(String[] args) {
		
		//	INITIAL APPROCACH
//		while (true) {
//			Thread.currentThread().getName();
//		}
		
		//	IF TRIED TO RUN THESE TWO LOOPS USING SAME THREAD, COMPILATION ERROR WILL COME SAYING "UNREACHABLE STATEMENT"
//		while (true) {
//			Thread.currentThread().getName();
//		}
		
		//	TO GET RID OF THIS, LETS GIVE THIS WORK TO RUN INFINITE LOOP TO ANOTHER THREAD
		// NOTE: ASSIGN TASK TO OTHER THREAD, BEFORE MAIN THREAD GOES INTO INFINITE LOOP PROCESSING
		
		//	main thread is first running new thread
		new NewThread().start();
		
		// then running infinite loop on main thread
		while (true) {
			System.out.println("I am thread: " + Thread.currentThread().getName());
		}
	}

}
