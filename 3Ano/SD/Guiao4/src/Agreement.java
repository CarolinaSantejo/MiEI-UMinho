import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Agreement {
    int n;
    ReentrantLock l = new ReentrantLock();
    int agmnt;

    Agreement (int n) {this.n = n}
    private static class Instance {
        int valor = 0;
        int cliente = 0;
    }
    void propose (int value) throws InterruptedException {
        l.lock();
        try {
            Instance i = new Instance();
            if (i.valor < value) {
                i.valor = value;
            }
            if (i.cliente >= n) {
                System.out.println(Thread.currentThread().getName() + " will signal all threads");
            } else (i.cliente<n) {
                    System.out.println(Thread.currentThread().getName() + " must wait for other threads");
            cond.await();
            System.out.println(Thread.currentThread().getName() + " interrupted. Value: " + value);
        }
        }

    }
}
