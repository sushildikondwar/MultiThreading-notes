package multithreading;

/**
 * DEADLOCK CAN OCCUR WHEN FOUR CONDITIONS ARE MET: 1. MUTUAL EXCLUSION: ONLY
 * ONE THREAD CAN ACCESS A RESOURCE AT A TIME 2. HOLD AND WAIT: A THREAD HOLDING
 * ATLEAST ONE RESOURCE IS WAITING TO ACQUIRE ADDITIONAL RESOURCES HELD BY OTHER
 * THREADS. 3. NO PREEMPTION: RESOURCES CANNOT BE FORCIBLY TAKEN FROM THREADS
 * HOLDING THEM 4. CIRCULAR WAIT: A SET OF THREADS IS WAITING FOR EACH OTHER IN
 * CIRCULAR CHAIN
 */

class Pen {
	//	here, t1 is executing and waiting for t2 to fully execute the writeWithPaperAndPen() method, then only it can access finishWriting() method of Paper
	public synchronized void writeWithPenAndPaper(Paper paper) {
		System.out.println(Thread.currentThread().getName() + " is using pen " + this + " and trying to write");
		paper.finishWriting();	//	this method is already in use by thread 't2' -- IMPORTANT NOTE: even though there are two sync methods, but the object of paper is same, so object's monitor is also same. So, even though t2 is using writeWithPaperAndPen() method, no other thread can access finishWriting() of that class (e.g., Paper)
	}

	public synchronized void finishWriting() {
		System.out.println(Thread.currentThread().getName() + " finished writing using pen " + this);
	}
}

class Paper {
	//	here, t2 is concurrently executing and waiting for t1 to fully execute the writeWithPenAndPaper() method, then only it can access finishWriting() method of Pen
	public synchronized void writeWithPaperAndPen(Pen pen) {
		System.out.println(Thread.currentThread().getName() + " is using paper " + this + " and trying to write");
		pen.finishWriting();	//	this method will go to waiting till writeWithPenAndPaper() of Pen is fully executed, then only monitor will allow other thread (t2) to access the method finishWriting() of that class, which won't be happening as t1 itself is waiting to execute finishWriting() method of Paper class, which has monitor lock due to t2 execution
	}
	
	public synchronized void finishWriting() {
		System.out.println(Thread.currentThread().getName() + " finished writing using paper " + this);
	}
}

//	IN THIS SCENARIO, BOTH PEN AND PAPER WILL BE WAITING TO COMPLETE THE TASK EXECUTION BY THEIR CORRESPONDING THREADS
public class DeadlockDemo {
	
	public static void main(String[] str) {
		
		Pen pen = new Pen();
		Paper paper = new Paper();
		
		Runnable r1 = () -> {
			pen.writeWithPenAndPaper(paper);
		};
		Runnable r2 = () -> {
			paper.writeWithPaperAndPen(pen);
		};
		
		//	creating 2 threads for execution
		Thread t1 = new Thread(r1, "t1");
		Thread t2 = new Thread(r2, "t2");
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		t1.start();
		t2.start();
	}

}
