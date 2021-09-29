import java.util.concurrent.locks.ReentrantLock;

class Bank implements Runnable{

  private static class Account {
    private int balance;
    Account(int balance) {
      this.balance = balance;
    }
    int balance() {
        return balance;
    }
    boolean deposit(int value) {
      ReentrantLock l = new ReentrantLock();
      l.lock();
      balance += value;
      l.unlock();
      return true;
    }
  }

  // Our single account, for now
  private Account savings = new Account(0);

  // Account balance
  public int balance() {
    return savings.balance();
  }

  // Deposit
  boolean deposit(int value) {
    return  savings.deposit(value);
  }

  public void run() {
    int I = 1000;
    for (int i = 0; i<I; i++)
      deposit(100);
  }

  public static void main(String[] args) throws InterruptedException {
    int N = 10;
    Bank inc = new Bank();
    Thread[] t = new Thread[N];
    for (int i = 0; i<N; i++)
      t[i] = new Thread(inc);
    for (int i = 0; i<N; i++)
      t[i].start();
    for (int i = 0; i<N; i++)
      t[i].join();
    System.out.println(inc.balance());
  }
}
