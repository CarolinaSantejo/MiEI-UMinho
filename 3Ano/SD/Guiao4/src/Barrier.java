import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier {
    int c = 0;
    int N = 0;
    int epoch = 0;
    ReentrantLock l = new ReentrantLock();
    Condition cond = l.newCondition();
    Barrier (int N) {
        this.N = N;
    }
    void await() throws InterruptedException {
        l.lock();
        c++;
        int e = epoch;
        if (c>=N) {
            System.out.println(Thread.currentThread().getName() + " will signal all threads");
            c = 0;
            epoch++;
            cond.signalAll();
        }
        while (c<N && e == epoch) {
            System.out.println(Thread.currentThread().getName() + " must wait for epoch " + e);
            cond.await();
            System.out.println(Thread.currentThread().getName() + " interrupted. My epoch: " + e + " Current epoch " + epoch);
        }

        l.unlock();
    }

}


