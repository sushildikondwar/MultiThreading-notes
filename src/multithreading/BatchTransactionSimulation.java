package multithreading;

import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Batch {
	private int counter;
	private boolean isBatchFilled;
	private int batchThreshold;
	private Object obj; // object monitor
	Lock lock = new ReentrantLock();

	public Batch(Object obj) {
		this.obj = obj;
		System.out.println("Set Batch Threshold value: ");
		Scanner sc = new Scanner(System.in);
		this.batchThreshold = sc.nextInt();
		sc.close();
	}
	
	public void process(int batchId) {
		System.out.println("# Batch(" + batchId + ") processed successfully!");	//	process transaction
	}
	
	public void flush() {
		System.out.println("** Transaction Flushed!");
		this.counter = 0;
	}

	public void proceedWithTransaction() {
		synchronized (obj) {	//	get object monitor, monitoring transaction-processor
			setFilledStatus(true);	//	set batch-filled-status to true, so that transaction-processor can proceed (by breaking out from loop) after notifying
			obj.notifyAll();	// notify to resume the transaction-processor, then it checks 'true' for batchedFilled flag, and proceed with transaction processing
			try {
				obj.wait();	//	making entity registration wait till transaction is getting processed
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

	public void taskCheckInEntityForTransaction() {
		lock.lock();
		this.counter++;	//	queuing up item in batch
		if (this.counter == batchThreshold) {	//	when counter reached to batchThreshold, proceed with transaction
			proceedWithTransaction();
		}
		lock.unlock();
	}

	public boolean getFilledStatus() {
		return this.isBatchFilled;
	}

	public void setFilledStatus(boolean batchFilledStatus) {
		this.isBatchFilled = batchFilledStatus;
	}
}

class TransactionProcessor implements Runnable {

	Object obj; // object monitor
	Batch batch;	//	entity registering tasks to be batched in txn
	int transactionCounter;

	public TransactionProcessor(Object obj, Batch batch) {
		this.obj = obj;
		this.batch = batch;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (obj) {
				while (batch.getFilledStatus() == false) {	//	before batch notifies, it changed flag to 'true' as batch filled - breaks loop
					try {
						obj.wait();	//	resume when notified by batch entity
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				batch.process(++this.transactionCounter);	//	process batch as a single transaction (passing batch id)
				batch.flush();	//	reset the batch counter
				batch.setFilledStatus(false);	//	negate the batch filled status
				obj.notifyAll();	//	notify the batch to resume processing
			}
		}
	}

}

class DummyTaskInjector implements Runnable {
	
	int TaskCounter = 0;
	private Batch batch;
	
	public DummyTaskInjector(Batch batch) {
		this.batch = batch;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {e.printStackTrace();}
			System.out.println("Task(" + (++this.TaskCounter) + ") is being queued for Transaction");
			batch.taskCheckInEntityForTransaction();
		}
	}
}

public class BatchTransactionSimulation {
	
	
	public static void main(String[] str) {
		Object obj = new Object();	//	monitor object
		Batch batch = new Batch(obj);	//	batch operator class
		
		Thread transProc = new Thread(new TransactionProcessor(obj, batch));	//	thread for transaction processing in batches
		Thread taskInjector = new Thread (new DummyTaskInjector(batch));	// thread for injecting tasks in batch to get processed in a single transaction
		
		transProc.start();
		taskInjector.start();
		
	}

}
