package g8;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static g8.TaggedConnection.Frame;

public class DemultiplexerAd implements AutoCloseable {
    private final TaggedConnection conn;
    private final Lock lock = new ReentrantLock();
    private  final Map<Integer,Entry> buf = new HashMap<>();
    private IOException exception = null;

    private class Entry {
        int waiters = 0;
        Condition cond = lock.newCondition();
        ArrayDeque<byte[]> queue = new ArrayDeque<>();
    }

    private Entry get(int tag) {
        Entry e = buf.get(tag);
        if(e == null) {
            e = new Entry();
            buf.put(tag,e);
        }
        return e;
    }

    public DemultiplexerAd(TaggedConnection conn) {
        this.conn = conn;
    }
    public void start() throws IOException {
        new Thread(()-> {
            try {
                for (; ; ) {
                    Frame frame = conn.receive();
                    lock.lock();
                    try {
                        Entry e = get(frame.tag);
                        e.queue.add(frame.data);
                        e.cond.signal();
                    } finally {
                        lock.unlock();
                    }
                }
            }
            catch (IOException e) {
                lock.lock();
                try {
                    exception = e;
                    buf.forEach((k,v) -> v.cond.signalAll());
                }
                finally {
                    lock.unlock();
                }
            }
        }).start();
    }
    public void send(Frame frame) throws IOException {
        conn.send(frame.tag,frame.data);
    }
    public void send(int tag,byte[] data) throws IOException {
        conn.send(tag,data);
    }
    public byte[] receive(int tag) throws IOException, InterruptedException {
        lock.lock();
        try {
            Entry e = get(tag);
            e.waiters++;
            for(;;) {
                if (!e.queue.isEmpty()) {
                    byte[] res = e.queue.poll();
                    e.waiters--;
                    if (e.queue.isEmpty() && e.waiters == 0)
                        buf.remove(tag);
                    return res;
                }
                if (exception != null)
                    throw exception;
                e.cond.await();
            }
        }
        finally {
            lock.unlock();
        }
    }
    public void close() throws IOException {
        conn.close();
    }
}

