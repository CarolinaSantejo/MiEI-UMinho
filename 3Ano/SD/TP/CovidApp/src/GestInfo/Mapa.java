package GestInfo;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class Mapa {

    ReentrantLock lock;
    private Localizacao[][] mapa;   /** em cada posição do array temos a localizacao nessa posição */
    int N;                          /** tamanho do mapa */

    public Mapa() {
        this.lock = new ReentrantLock();
        this.N = 5;
        mapa = new Localizacao[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                mapa[i][j] = new Localizacao(i,j);
            }
        }
    }

    public Localizacao getLocalizacao(int x, int y) {
        try {
            this.mapa[x][y].lockLocal();
            return this.mapa[x][y];
        }
        finally {
            this.mapa[x][y].unlockLocal();
        }
    }

    public Localizacao[][] getMapa() {
        Localizacao[][] newMapa = new Localizacao[N][N];
        try {
            lock.lock();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    newMapa[i][j] = mapa[i][j].clone();
                }
            }
        }
        finally {
            lock.unlock();
        }
        return newMapa;
    }

    public int getN(){
        try {
            lock.lock();
            return this.N;
        }
        finally {
            lock.unlock();
        }
    }
    public void setN(int x){
        try {
            lock.lock();
            this.N = x;
        }
        finally {
            lock.unlock();
        }
    }

    public void getLocalVazio(int x, int y) throws InterruptedException {
        mapa[x][y].lockLocal();
        try {
            while (mapa[x][y].getNpessoas() > 0){
                mapa[x][y].getCond().await();
            }
            throw new InterruptedException();
        }
        finally {
            mapa[x][y].unlockLocal();
        }
    }



    @Override
    public String toString() {
        return "GestInfo.Mapa{" +
                "mapa=" + Arrays.toString(mapa) +
                '}';
    }
}
