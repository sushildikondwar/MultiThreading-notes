package multithreading;

class Pen1 {
	public synchronized void writeWithPenAndPaper(Paper1 paper) {
		System.out.println(Thread.currentThread().getName() + " is using pen " + this + " and trying to write");
		paper.finishWriting();
	}

	public synchronized void finishWriting() {
		System.out.println(Thread.currentThread().getName() + " finished writing using pen " + this);
	}
}

class Paper1 {
	public synchronized void writeWithPaperAndPen(Pen1 pen) {
		System.out.println(Thread.currentThread().getName() + " is using paper " + this + " and trying to write");
		pen.finishWriting();
	}

	public synchronized void finishWriting() {
		System.out.println(Thread.currentThread().getName() + " finished writing using paper " + this);
	}
}

//	DEADLOCK RESOLUTION - control when the thread should proceed with its execution
public class DeadlockResolutionDemo {

	public static void main(String[] str) {

		Pen1 pen = new Pen1();
		Paper1 paper = new Paper1();

		Runnable r1 = () -> {
			synchronized (paper) { // this will allow t1 to execute below method only if 'paper' monitor has no
									// active lock -- which it true as this is the first thread under execution
				pen.writeWithPenAndPaper(paper);
			}
		};
		Runnable r2 = () -> {
			synchronized (pen) { // this will allow t2 only and only if it has inactive(lock not acquired by
									// other thread) 'pen' monitor -- meaning, t1 will complete the method execution
									// and releases lock on pen by t1, then only t2 can execute the below method,
									// which will be successfully executed as pen has no lock on it
				paper.writeWithPaperAndPen(pen);
			}
		};

		// creating 2 threads for execution
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
