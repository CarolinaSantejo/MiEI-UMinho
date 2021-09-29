import Exceptions.FragmentationPacketsNotReceived;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class protocolDemultiplexer {
    private  final Map<Integer, Entry> buf = new HashMap<>();
    private final Lock lock = new ReentrantLock();
    private IOException exception = null;
    FSChunkProtocol protocol = new FSChunkProtocol();

    private class Entry {
        int waiters = 0;
        Condition cond = lock.newCondition();
        ArrayDeque<Packet> queue = new ArrayDeque<>();
    }

    private Entry getEntryBuf(int tag) {
        Entry e = buf.get(tag);
        if(e == null) {
            e = new Entry();
            buf.put(tag,e);
        }
        return e;
    }

    public Packet receive(int clientId) throws IOException, InterruptedException{
        lock.lock();
        try {
            Entry e = getEntryBuf(clientId);
            e.waiters++;
            for(;;) {
                if (!e.queue.isEmpty()) {
                    Packet res = e.queue.poll();
                    e.waiters--;
                    if (e.queue.isEmpty() && e.waiters == 0)
                        buf.remove(clientId);
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

    public void start(DatagramSocket sock) throws IOException {
        new Thread(()-> {
            try {
                for (;;) {
                    Packet frame = protocol.receive(sock);
                    lock.lock();
                    try {
                        Entry e = getEntryBuf((int)frame.clientId);
                        e.queue.add(frame);
                        e.cond.signal();
                    } finally {
                        lock.unlock();
                    }
                }
            }
            catch (IOException e)  {
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

    public HostInfo listenConn(DatagramSocket s) throws IOException, InterruptedException {
        Packet beg = receive(-1);
        if (beg.getMessageType() == 1) {
            HostInfo sI = new HostInfo();
            sI.fromBytes(beg.getData());
            Packet begAck = new Packet((short) -1,1,1,0);
            protocol.send(s,sI,begAck);
            try {
                Packet ack = receive(-1);
                if (ack.getAck() == 1) {
                    System.out.println("Connection established!");
                    return sI;
                }
                else {
                    if(ack.getMessageType() == 3) protocol.send(s,sI,begAck);
                }
            }
            catch (SocketTimeoutException e){
                e.printStackTrace();
            }

        }
        return null;
    }

    public HostInfo receiveEndConn(DatagramSocket s) throws IOException, InterruptedException {
        Packet end = receive(-2);
        if (end.getMessageType() == 2) {
            HostInfo sI = new HostInfo();
            sI.fromBytes(end.getData());
            Packet endAck = new Packet((short) -2,2,2,0);
            protocol.send(s,sI,endAck);
            // Definir um tempo máximo de espera do ACK
            s.setSoTimeout(60000);
            Packet ack = receive(-2);
            if (ack.getAck() == 2) {
                return sI;
            }
        }
        return null;
    }

    public FileInfo requestFileInfo(DatagramSocket s,int clientId ,HostInfo hostInfo, String filePathName) throws IOException, InterruptedException {
        FileInfo fI = new FileInfo(0, filePathName, (short) 0);
        Packet p = new Packet((short) clientId, 6, 0, 0, fI);
        protocol.send(s, hostInfo, p);

        p = receive(clientId);
        if (p.getExp() == 1)
            throw new FileNotFoundException("Ficheiro não encontrado!");
        else {
            if (p.getAck() == 6 && p.getMessageType() == 6) fI = p.file;
        }
        return fI;
    }

    public File requestFile(DatagramSocket s, int clientId, HostInfo hostInfo, String filePathName) throws IOException, InterruptedException {
        FileInfo fI = new FileInfo(clientId,filePathName, (short) 0);
        Packet p = new Packet((short) clientId,4,0,0,fI);
        protocol.send(s,hostInfo,p);
        p = receive(clientId);
        File f = null;
        if(p.getExp() == 1) throw new FileNotFoundException("Ficheiro não encontrado!");
        else {
            if (p.getAck() == 4 && p.getMessageType() == 5) {
                try {
                    f = receiveNewFile(s, clientId, p);
                    p = new Packet((short) clientId, 0, 5, 0);
                    protocol.send(s, hostInfo, p);
                }
                catch (FragmentationPacketsNotReceived e) {
                    p = new Packet((short) clientId, 3, 0, 0);
                    protocol.send(s, hostInfo, p);
                    p = receive(clientId);
                    if (p.getAck() == 3 && p.getMessageType() == 5) {
                        try {
                            f = receiveNewFile(s, clientId, p);
                            p = new Packet((short) clientId, 0, 5, 0);
                            protocol.send(s, hostInfo, p);
                        }
                        catch (FragmentationPacketsNotReceived ef) {
                            f = null;
                        }
                    }
                }
            }
        }
        return f;
    }

    private File receiveNewFile(DatagramSocket s,int clientId,Packet pacote) throws IOException, InterruptedException, FragmentationPacketsNotReceived {
        String nome = pacote.file.fileName;
        int sizeFile = pacote.file.fileSize;
        int pNumber = pacote.file.packetNumber;
        int numberOfPackets = (sizeFile / pacote.getMaxDataSize()) + 1;
        List<Packet> pacotes = new ArrayList<>();
        pacotes.add(pacote);
        for (int i = 0; i < numberOfPackets - 1; i++) {
            Packet p = receive(clientId);
            pacotes.add(p);
        }
        if (pacotes.size() == numberOfPackets){
            pacotes.sort(Comparator.comparing(a -> a.file.packetNumber));
            return createNewFile(pacotes);
        }
        else {
            throw new FragmentationPacketsNotReceived("Pacotes não recebidos na totalidade");
        }
    }

    private File createNewFile(List<Packet> listP) {
        String name = listP.get(0).file.fileName;

        File outputFile = new File(name);

        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            for(Packet p : listP) {
                outputStream.write(p.getData(), 0, p.data.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return outputFile;
    }
}
