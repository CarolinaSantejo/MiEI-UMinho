package g8;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class FramedConnection implements AutoCloseable {
    private Socket s;
    private DataInputStream in;
    private DataOutputStream out;
    private ReentrantLock lr;
    private ReentrantLock lw;
    public FramedConnection(Socket socket) throws IOException {
        this.s = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        this.lr = new ReentrantLock();
        this.lw = new ReentrantLock();
    }
    public void send(byte[] data) throws IOException {
        lw.lock();
        out.writeInt(data.length);
        out.write(data);
        out.flush();
        lw.unlock();
    }
    public byte[] receive() throws IOException {
        try {
            lr.lock();
            byte[] data = new byte[in.readInt()];
            in.readFully(data);
            return data;
        }
        finally {
            lr.unlock();
        }
    }
    public void close() throws IOException {
        this.s.close();
    }
}