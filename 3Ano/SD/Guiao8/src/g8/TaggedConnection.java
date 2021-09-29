package g8;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class TaggedConnection implements AutoCloseable{
    private Socket s;
    private DataInputStream in;
    private DataOutputStream out;
    private ReentrantLock lr;
    private ReentrantLock lw;
    public static class Frame {
        public final int tag;
        public final byte[] data;
        public Frame(int tag,byte[] data) {this.tag = tag;this.data = data; }
    }
    public TaggedConnection(Socket socket) throws IOException {
        this.s = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        this.lr = new ReentrantLock();
        this.lw = new ReentrantLock();
    }
    public void send(Frame frame) throws IOException {
        send(frame.tag,frame.data);
    }
    public void send(int tag,byte[] data) throws IOException {
        lw.lock();
        out.writeInt(4 + data.length);
        out.writeInt(tag);
        out.write(data);
        out.flush();
        lw.unlock();
    }
    public Frame receive() throws IOException {
        int tag;
        try {
            lr.lock();
            byte[] data = new byte[in.readInt()-4];
            tag = in.readInt();
            in.readFully(data);
            return new Frame(tag,data);
        }
        finally {
            lr.unlock();
        }
    }
    public void close() throws IOException {
        this.s.close();
    }
}
