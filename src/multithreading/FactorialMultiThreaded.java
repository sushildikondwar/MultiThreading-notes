package multithreading;

public class FactorialMultiThreaded {

	//	allocating factorial calculation to each new thread created
	public void factorial(int i) {
		int k = i;
		int j = 1;
		if (i == 0) {
			System.out.println("factorial of " + i + ": " + 0);
			return;
		}
		while (i > 0) {
			j = j * i--;
		}
		System.out.println("factorial of " + k + ": " + j);
		return;
	}

	public static void main(String[] str) {

		for (int i = 1; i <= 10; i++) {
			int m;
			m = i;
			Runnable r = () -> {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {}
				FactorialMultiThreaded f = new FactorialMultiThreaded();
				f.factorial(m);
			};
			Thread t1 = new Thread(r);
			t1.start();
		}

	}

}
