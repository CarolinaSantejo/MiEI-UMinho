public class BarrierTest implements Runnable {
    private Barrier b = new Barrier(5);
    public static void main(String[] args) {
        int N = 10;
        Thread t[] = new Thread[N];
        BarrierTest b = new BarrierTest();
        for (int i = 0; i < N; i++) {
            t[i] = new Thread(b);
        }
        for (int i = 0; i < N; i++) {
            t[i].start();
        }
    }

     public void run () {
        try {
            b.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}