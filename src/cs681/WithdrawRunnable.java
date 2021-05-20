package cs681;
import java.util.concurrent.locks.ReentrantLock;

public class WithdrawRunnable implements Runnable {

	private BankAccount account;
	private boolean done = false;
	private final ReentrantLock lock = new ReentrantLock();

	public WithdrawRunnable(BankAccount account) {
		this.account = account;
	}

	public void setDone(){
		lock.lock();
		try {
			done = true;
		}
		finally {
			lock.unlock();
		}
	}
	
	public void run(){
		for(int i = 0; i < 10; i++) {
			lock.lock();
			try { 
				if (done) {
					System.out.println("Stopped withdrawing.");
					break;
				}
				account.withdraw(100);
			
			} finally {
			lock.unlock();
			}
			try {
				Thread.sleep(500);
			} catch(InterruptedException exception) {
				System.out.println(exception.toString());
				continue;
			}
		}
	}
}

