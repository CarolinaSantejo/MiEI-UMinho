import java.util.Random;

class Mover implements Runnable {
  Bank b;

  public Mover(Bank b) { this.b=b; }

  public void run() {
    final int moves=7;
    Random rand = new Random();
    int from,to, value = 0;
    boolean transf = false;
    for (int m=0; m<moves; m++) {
      from = rand.nextInt(b.getNextId());
      to = rand.nextInt(b.getNextId());
      value = rand.nextInt(10000);
      transf = b.transfer(from,to,value);
      if (transf) System.out.println("Tranferencia " + from + " -> " + to + ": " + value);
      else System.out.println("Tranferencia " + from + " -> " + to + ": " + value + " (NAO EFETUADA)");
    }
    for (int i = 0; i<2; i++) {
      to = rand.nextInt(b.getNextId());
      value = b.closeAccount(to);
      if (value == 0) System.out.println("Conta " + to + " nao existe" );
      else {
        System.out.println("Conta encerrada com sucesso. Balanço: " + value);
      }
    }
  }
}

class BankTest {
  public static void main(String[] args) throws InterruptedException {
    final int N=10;
    int tot = 0;
    Bank b = new Bank();
    Random rand = new Random();
    int bal = 0;
    int id = 0;
    for (int i=0; i<N; i++) {
      bal = rand.nextInt(40000); // Get one
      id = b.createAccount(bal);
      System.out.println("Conta " + id+ " criada com balanco: " + bal);
    }

    System.out.println("ola");

    Thread t1 = new Thread(new Mover(b));
    Thread t2 = new Thread(new Mover(b));

    t1.start(); t2.start(); t1.join(); t2.join();
    System.out.println(b.toString());
    System.out.println("xau");
  }
}
