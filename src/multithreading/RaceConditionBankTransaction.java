package multithreading;

class BankTransaction {
	private int balance;

	BankTransaction(int initialBalance) {
		this.balance = initialBalance;
	}
	
	public int getBalance() {
		return this.balance;
	}

	public void setBalance(int amount) {
		this.balance = amount;
	}

	public void withdrawMoney(int amount) throws IllegalAccessException {
		System.out.println("Thread " + Thread.currentThread().getName() + ": attempting withdrawl");
		if (this.balance >= amount) {
			System.out.println("Thread " + Thread.currentThread().getName() + ": processing withdrawl");
			this.balance -= amount;
			// race condition might occur, and both threads can decrease the transaction to
			// -ve
			// verifying the transaction integrity
			if (this.balance >= 0) {
				System.out.println("Thread " + Thread.currentThread().getName() + ": completed withdrawl");
				System.out.println("Thread " + Thread.currentThread().getName() +": Current balance: " + this.balance);
			} else {
				throw new IllegalAccessException(
						"Race condition occurred in thread: " + Thread.currentThread().getName());
			}
		} else {
			System.out.println("Thread " + Thread.currentThread().getName() + ": insufficient balance");
		}
	}
}

public class RaceConditionBankTransaction {

	public static void main(String[] str) {
		int initialBalance = 150;
		int amountToWithdraw = 100;
		BankTransaction transaction = new BankTransaction(initialBalance);

		Runnable run1 = () -> {
			try {
				transaction.withdrawMoney(amountToWithdraw);
			} catch (IllegalAccessException e) {
				System.out.println(e.getMessage() + "\nROLLING BACK TRANSACTION!");
				transaction.setBalance(initialBalance);
				System.out.println("Current balance: " + transaction.getBalance());
			}
		};

		Thread t1 = new Thread(run1);
		t1.start();

		Thread t2 = new Thread(run1);
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
