import java.util.concurrent.locks.ReentrantLock;

class Bank {
  private static class Account {
    ReentrantLock l = new ReentrantLock();
    private int balance;
    Account(int balance) { this.balance = balance; }
    int balance() { return balance; }
    boolean deposit(int value) {
      //l.lock();
      balance += value;
      //l.unlock();
      return true;
    }
    boolean withdraw(int value) {
      if (value > balance)
        return false;
      //l.lock();
      balance -= value;
      //l.unlock();
      return true;
    }
  }

  // Bank slots and vector of accounts
  private int slots;
  private Account[] av; 

  public Bank(int n)
  {
    slots=n;
    av=new Account[slots];
    for (int i=0; i<slots; i++) av[i]=new Account(0);
  }


  // Account balance
  public int balance(int id) {
    if (id < 0 || id >= slots)
      return 0;
    return av[id].balance();
  }

  // Deposit
  boolean deposit(int id, int value) {
    if (id < 0 || id >= slots)
      return false;
    return av[id].deposit(value);
  }

  // Withdraw; fails if no such account or insufficient balance
  public boolean withdraw(int id, int value) {
    if (id < 0 || id >= slots)
      return false;
    return av[id].withdraw(value);
  }

  boolean transfer (int from,int to,int value) {
    int minAccount = Math.min(from,to);
    int maxAccount = Math.max(from,to);
    av[minAccount].l.lock();
    av[maxAccount].l.lock();
    try {
      withdraw(from, value);
      deposit(to, value);
    }
    finally {
      av[from].l.unlock();
      av[to].l.unlock();
    }
    return true;
  }

  int totalBalance() {
    int balance = 0;
    for (int i = 0; i<slots; i++)
      balance += balance(i);
    return balance;
  }


}
