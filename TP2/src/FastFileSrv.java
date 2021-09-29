import Exceptions.ConnectionNotEnded;
import Exceptions.ConnectionNotEstablished;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class FastFileSrv {
    static HostInfo gwInfo;
    static String filesPath = "ServerFiles/";
    static FSChunkProtocol protocol = new FSChunkProtocol();
    static DatagramSocket sock;

    public static void main(String args[]) throws IOException {
        String address = args[0];
        int port = Integer.parseInt(args[1]);
        gwInfo = new HostInfo(port,address);
        getFileNamesPath();
        try {
            sock = new DatagramSocket();
            if(protocol.startConn(sock, gwInfo,gwInfo.files) == 0) {
                System.out.println("Connection established!");
                while (true) {
                    sock.setSoTimeout(0);
                    Packet request = protocol.receive(sock);
                    if (request.getMessageType() == 4) {
                        try {
                            FileInfo f = request.file;
                            if (protocol.sendFile(sock,request.clientId,filesPath, f.fileName, gwInfo)== 0) echo("Ficheiro enviado!");
                            else echo("Ficheiro não recebido pelo cliente!");
                        }
                        catch (FileNotFoundException e) {
                            protocol.send(sock,gwInfo, new Packet(request.clientId,0,0,1));
                        }
                    }
                    if (request.getMessageType() == 6) {
                        FileInfo f = request.file;
                        System.out.println(request.toString());
                        try {
                            f.updateMetadataByName(filesPath);
                            System.out.println(f.toString());
                            protocol.sendFileInfo(sock, request.clientId, gwInfo, f);
                            echo("FileInfo enviada!");
                        }
                        catch (FileNotFoundException e) {
                            protocol.send(sock,gwInfo, new Packet(request.clientId,0,0,1));
                        }
                    }
                }
            }
        }
        catch(IOException | ConnectionNotEstablished | InterruptedException e) {
            System.err.println("EXCEPTION: " + e);
            try {
                protocol.endConnection(sock,gwInfo);
            }
            catch (ConnectionNotEnded ee) {
                echo("Gateway não recebeu termino de conexao");
            }
        }
    }

    //simple function to echo data to terminal
    public static void echo(String msg) {
        System.out.println(msg);
    }

    public static void getFileNamesPath() {
        File folder = new File(filesPath);
        File[] listOfFiles = folder.listFiles();
        List<String> files = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
            if (listOfFiles[i].isFile()) {
                files.add(listOfFiles[i].getName());
            }
        }
        gwInfo.setFiles(files);
    }



}
