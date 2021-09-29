import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Warehouse {
  private Map<String, Product> m =  new HashMap<String, Product>();
  ReentrantLock l = new ReentrantLock();

  private class Product {
    int q = 0;
    Condition isEmpty = l.newCondition();
    ReentrantLock lp = new ReentrantLock();
  }

  private Product get(String s) {
    Product p = m.get(s);
    if (p != null) return p;
    p = new Product();
    m.put(s, p);
    return p;
  }

  public void supply(String s, int q) {
    l.lock();
    Product p = get(s);
    p.lp.lock();
    l.unlock();
    p.q += q;
    p.isEmpty.signalAll();
    p.lp.unlock();
  }

  /* Versao egoista
  public void consume(String[] a) throws InterruptedException {
    l.lock();
    for (String s : a) {
      get(s).lp.lock();
    }
    l.unlock();
    for (String s : a) {
      Product p = get(s);
      while (p.q == 0){
        p.isEmpty.await();
      }
      p.q--;
      p.lp.unlock();
    }
  }
   */

  public void consume(String[] a) throws InterruptedException {
    l.lock();
    for (String s : a) {
      get(s).lp.lock();
    }
    l.unlock();
    int i = 0;
    while (i < a.length) {
      Product p = get(a[i]);
      i++;
      if (p.q == 0) {
        for (int j = 0; j<i; j++)
          get(a[j]).lp.unlock();
        i = 0;
        p.isEmpty.await();
      }
    }
    for (String s : a) {
      Product p = get(s);
      p.q--;
      p.lp.unlock();
    }
  }
}
