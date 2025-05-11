package multithreading;

public class Factorial {

	public static void factorial(int i) {
		int k = i;
		int j = 1;
		while (i > 0) {
			j *= i--;
		}
		System.out.println("factorial of " + k + ": " + j);
	}

	public static void main(String[] str) {
		for (int i = 1; i <= 10; i++) {
			try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}
			factorial(i);
		}
	}

}
