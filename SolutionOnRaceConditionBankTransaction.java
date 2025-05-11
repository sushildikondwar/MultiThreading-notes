package multithreading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BankTransactionWithLock {

	boolean isLock = false;
	private final Lock lock = new ReentrantLock();

	private int balance;

	BankTransactionWithLock(int initialBalance) {
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
		try {
			this.isLock = lock.tryLock();			
			if (this.isLock) {
				if (this.balance >= amount) {
					System.out.println("Thread " + Thread.currentThread().getName() + ": processing withdrawl");
					this.balance -= amount;
				} else {
					System.out.println("Thread " + Thread.currentThread().getName() + ": insufficient balance");
				}
			} else {
				System.out.println("Thread " + Thread.currentThread().getName() + ": couldn't acquire lock");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			if (lock.tryLock())	//	this will not only provide boolean, but also attempts to acquire lock
			if (this.isLock)	//	this will check, what was the result of isLock for the recent lock attempt, if the lock was acquired, then only unlock the lock
				lock.unlock();
		}
	}
}

public class SolutionOnRaceConditionBankTransaction {

	public static void main(String[] str) {
		int initialBalance = 150;
		int amountToWithdraw = 100;
		BankTransactionWithLock transaction = new BankTransactionWithLock(initialBalance);

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
