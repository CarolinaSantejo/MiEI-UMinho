import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Bank extends java.util.concurrent.locks.ReentrantLock {

    private static class Account {
        Lock l = new ReentrantLock();
        private int balance;
        Account(int balance) { this.balance = balance; }
        int balance() { return balance; }
        boolean deposit(int value) {
            balance += value;
            return true;
        }
        boolean withdraw(int value) {
            if (value > balance)
                return false;
            balance -= value;
            return true;
        }
        @Override
        public String toString() {
            return "Balanco: " + balance;
        }
    }

    private Map<Integer, Account> map = new HashMap<Integer, Account>();
    private int nextId = 0;
    private ReentrantLock lBanco = new ReentrantLock();
    private ReentrantReadWriteLock l = new ReentrantReadWriteLock();
    private Lock rl = l.readLock();
    private Lock wl = l.writeLock();

    public int getNextId() {
        return nextId;
    }

    @Override
    public String toString() {
        return "Bank {\n" +
                map.toString() +
                "\nNextId=" + nextId +
                '}';
    }

    // create account and return account id
    public int createAccount(int balance) {
        Account c = new Account(balance);
        //this.lBanco.lock();
        wl.lock();
        try {
            int id = nextId;
            nextId += 1;
            map.put(id, c);
            return id;
        }
        finally {
            wl.unlock();
        //    this.lBanco.unlock();
        }
    }

    // close account and return balance, or 0 if no such account
    public int closeAccount(int id) {
        //this.lBanco.lock();
        wl.lock();
        Account c = map.remove(id);
        if (c == null) {
        //    this.lBanco.unlock();
            wl.unlock();
            return 0;
        }
        c.l.lock();
        wl.unlock();
        //this.lBanco.unlock();
        try {
            return c.balance();
        }
        finally {
            c.l.unlock();
        }
    }

    // account balance; 0 if no such account
    public int balance(int id) {
        Account c;
        //lBanco.lock();
        rl.lock();
        try {
            c = map.get(id);
            if (c == null)
                return 0;
            c.l.unlock();
        }
        finally {
            rl.unlock();
        //    lBanco.unlock();
        }
        try {
            return c.balance();
        }
        finally {
            c.l.unlock();
        }
    }

    // deposit; fails if no such account
    public boolean deposit(int id, int value) {
        Account c;
        rl.lock();
        //lBanco.lock();
        try {
            c = map.get(id);
            if (c == null)
                return false;
            c.l.lock();
        }
        finally {
            rl.unlock();
            //lBanco.unlock();
        }
        try {
            return c.deposit(value);
        }
        finally {
            c.l.unlock();
        }
    }

    // withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        Account c;
        //lBanco.lock();
        wl.lock();
        try {
            c = map.get(id);
            if (c == null)
                return false;
            c.l.lock();
        }
        finally {
            wl.unlock();
        //    lBanco.unlock();
        }
        try {
            return c.withdraw(value);
        }
        finally {
            c.l.unlock();
        }
    }

    // transfer value between accounts;
    // fails if either account does not exist or insufficient balance
    public boolean transfer(int from, int to, int value) {
        Account cfrom, cto;
        //lBanco.lock();
        rl.lock();
        try {
            cfrom = map.get(from);
            cto = map.get(to);
            if (cfrom == null || cto == null) {
                return false;
            }
            cfrom.l.lock();
            cto.l.lock();
        }
        finally {
            rl.unlock();
        //    lBanco.unlock();
        }
        try {
            try {
                if (!cfrom.withdraw(value))
                    return  false;
            }
            finally {
                cfrom.l.unlock();
            }
            return cto.deposit(value);
        }
        finally {
            cto.l.unlock();
        }
    }

    // sum of balances in set of accounts; 0 if some does not exist
    public int totalBalance(int[] ids) {
        int total = 0;
        for (int i : ids) {
            //this.lBanco.lock();
            rl.lock();
            Account c = map.get(i);
            if (c == null) {
                this.lBanco.unlock();
                return 0;
            }
            c.l.lock();
            rl.unlock();
            //this.lBanco.unlock();
            total += c.balance();
            c.l.unlock();
        }
        return total;
  }

}
