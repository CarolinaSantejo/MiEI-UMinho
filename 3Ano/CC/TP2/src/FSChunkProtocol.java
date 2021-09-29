import Exceptions.ConnectionNotEnded;
import Exceptions.ConnectionNotEstablished;

import java.io.*;
import java.net.*;
import java.util.*;
import java.net.UnknownHostException;



public class FSChunkProtocol {

    public int startConn(DatagramSocket s, HostInfo hostInfo,List<String> files) throws IOException, ConnectionNotEstablished {
        Packet pacote = new Packet((short) -1,1,0,0); //
        InetAddress address = null;
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets)){
                // no 127.0.0.1 (loopback) or interfaces that are down
                if(!netint.isLoopback() || !netint.isUp()){
            Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
            for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                        if(inetAddress instanceof Inet4Address) // no ipv6, only ipv4
                            address = inetAddress;
                }
            }
        }
        try {
            String hostname = address.getHostName();
            HostInfo sI = new HostInfo(s.getLocalPort(),hostname,files);
            pacote.setData(sI.toBytes());
            send(s,hostInfo, pacote);
        }
        catch (NullPointerException e){
            throw new ConnectionNotEstablished("Conexao nao establecida");
        }

	
        // Definir um tempo máximo de espera do ACK

        for (int i = 0; i < 3; i++) {
            try {
                s.setSoTimeout(10000);
                Packet ack = receive(s);
                s.setSoTimeout(0);
                if (ack.getMessageType() == 1 && ack.getAck() == 1) {
                    pacote.setMessageType(0);
                    pacote.setAck(1);
                    send(s,hostInfo, pacote);
                    return 0;
                }

            }
            catch (SocketTimeoutException e) {
                Packet ret = new Packet((short)-1,3,0,0);
                send(s,hostInfo,ret);
            }
        }
        throw new ConnectionNotEstablished("Conexao nao establecida");
    }



    public int endConnection(DatagramSocket s, HostInfo hostInfo) throws IOException, ConnectionNotEnded {
        Packet p = new Packet((short) -2,2,0,0);
        InetAddress address = null;
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets)){
            // no 127.0.0.1 (loopback) or interfaces that are down
            if(!netint.isLoopback() || !netint.isUp()){
                Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                    if(inetAddress instanceof Inet4Address) // no ipv6, only ipv4
                        address = inetAddress;
                }
            }
        }
        try {
            String hostname = address.getHostName();
            HostInfo sI = new HostInfo(s.getLocalPort(),hostname);
            p.setData(sI.toBytes());
            send(s,hostInfo, p);
        }
        catch (NullPointerException e){
            throw new ConnectionNotEnded("Conexao nao establecida");
        }
        Packet ack = receive(s);
        if (ack.getMessageType() == 2 && ack.getAck() == 2) {
            p =  new Packet((short) -2,0,2,0);
            send(s,hostInfo,p);
            return 0;
        }
        return -1;
    }


    public void sendFileInfo(DatagramSocket s, short clientId,HostInfo gwInfo, FileInfo fileInfo) throws IOException {
        Packet p = new Packet(clientId,6,6,0,fileInfo);
        send(s,gwInfo,p);
    }

    public int sendFile(DatagramSocket sock ,short clientId,String filesPath,String fileName, HostInfo gwInfo) throws IOException, InterruptedException {
        FileInfo fI = new FileInfo(fileName);
        fI.updateMetadataByName(filesPath);
        Packet packet = new Packet( clientId, 5,0,0,fI);
        int numberOfPackets = (fI.fileSize / packet.getMaxDataSize())+1;
        byte[] buffer = new byte[(int)fI.fileSize];
        byte[][] buffers = new byte[numberOfPackets][];
        FileInputStream is = new FileInputStream(filesPath + fileName);
        is.read(buffer);
        int size,initPosition;
        for (int i = 0; i < numberOfPackets; i++) {
            if (fI.fileSize > (i+1)*packet.getMaxDataSize()) size = packet.getMaxDataSize();
            else size = fI.fileSize - (i*packet.getMaxDataSize());
            buffers[i] = new byte[size];
            initPosition = i * packet.getMaxDataSize();
            buffers[i] = Arrays.copyOfRange(buffer, initPosition ,initPosition+size);
        }
        is.close();
        for (int i = 0; i < numberOfPackets; i++) {
            fI.packetNumber = (short)i;

            packet = new Packet( clientId, 5,0,0,fI,buffers[i]);
            if (i == 0) packet.setAck(4);
            send(sock, gwInfo, packet);
            if (numberOfPackets>1) Thread.sleep(5); // Para enviar mais devagar
        }
        Packet ack = receive(sock);
        if (ack.getAck() == 5) {
            return 0;
        }
        else {
            if (ack.getMessageType() == 3) {
                for (int i = 0; i < numberOfPackets; i++) {
                    packet = new Packet( clientId, 5,3,0,fI,buffers[i]);
                    send(sock, gwInfo, packet);
                    if (numberOfPackets>1) Thread.sleep(5); // Para enviar mais devagar
                }
                ack = receive(sock);
                if (ack.getAck() == 5) {
                    return 0;
                }
            }
        }
        return -1;
    }

    public void send(DatagramSocket s, HostInfo hostInfo, Packet p) throws IOException {
        InetAddress host = InetAddress.getByName(hostInfo.address);
        byte[] packet = p.packetToBytes();
        s.send(new DatagramPacket(packet,packet.length,host,hostInfo.getPort()));
    }



    public Packet receive(DatagramSocket s) throws IOException {
        byte[] packetBytes = new byte[65536];
        DatagramPacket dp = new DatagramPacket(packetBytes,packetBytes.length);
        s.receive(dp);
        Packet pacote = new Packet();
        pacote.bytesToPacket(packetBytes);
        return pacote;
    }

}
