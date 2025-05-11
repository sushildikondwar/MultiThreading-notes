package multithreading;

public class MainThread extends Thread {
	
	public MainThread(String threadName) {
		// TODO Auto-generated constructor stub
		super(threadName);
	}
	
	@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				sleep(2000);	//	put new thread to sleep(2s)
				System.out.println(currentThread().getName());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread th1 = new MainThread("New-Thread-1");
		System.out.println(th1.getState());
		th1.start();
		try {
			Thread.sleep(1000);	//	wait(1s) to get the new thread started processing
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(th1.getState());	//	gets the state when new thread is waiting(2s)
		System.out.println(Thread.currentThread().getState());

	}

}
