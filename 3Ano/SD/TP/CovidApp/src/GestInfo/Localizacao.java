package GestInfo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Para bloquear apenas uma localizacao (dizer no relatorio)
 * Vimos que nao era eficiente bloquear o mapa totalmente
 */

public class Localizacao {

    private final int x;
    private final int y;
    private ReentrantLock lock;
    private int npessoas;
    private Condition cond;

    public Localizacao(int x, int y) {
        this.x = x;
        this.y = y;
        this.lock = new ReentrantLock();
        this.npessoas = 0;
        this.cond = lock.newCondition();
    }

    public Localizacao(int x, int y,int npessoas) {
        this.x = x;
        this.y = y;
        this.lock = new ReentrantLock();
        this.npessoas = npessoas;
        this.cond = lock.newCondition();
    }

    public void retirar(){
        lock.lock();
        try {
            this.npessoas--;
            if(this.npessoas==0) this.cond.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public void somar(){
        lock.lock();
        try {
            this.npessoas++;
        }finally {
            lock.unlock();
        }
    }

    public int nPessoas(){
        return npessoas;
    }

    public void lockLocal (){
        lock.lock();
    }

    public void unlockLocal (){
        lock.unlock();
    }

    public int getNpessoas() {
        return npessoas;
    }

    public void setNpessoas(int npessoas) {
        this.npessoas = npessoas;
    }

    public Condition getCond() {
        return cond;
    }

    public void setCond(Condition cond) {
        this.cond = cond;
    }

    public Localizacao clone() {
        return new Localizacao(x,y,npessoas);
    }

    public int getX() {
        try{
            lock.lock();
            return x;
        }
        finally {
            lock.unlock();
        }
    }


    public int getY() {
        try{
            lock.lock();
            return y;
        }
        finally {
            lock.unlock();
        }
    }


    @Override
    public String toString() {
        return "GestInfo.Localizacao{" +
                "x=" + x +
                ", y=" + y +
                ", npessoas=" + npessoas +
                '}';
    }
}



