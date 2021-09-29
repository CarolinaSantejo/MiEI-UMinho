class Incrementer implements Runnable {
  public void run() {
    final long I=100;

    for (long i = 0; i < I; i++)
      System.out.println(i);
  }

  public static void main(String[] args) throws InterruptedException {
    int N = 10;
    Incrementer inc = new Incrementer();
    Thread[] t = new Thread[N];
    for (int i = 0; i<N; i++)
      t[i] = new Thread(inc);
    for (int i = 0; i<N; i++)
      t[i].start();
    for (int i = 0; i<N; i++)
      t[i].join();
    System.out.println("fim");
  }
}
