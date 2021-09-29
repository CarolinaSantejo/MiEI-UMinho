import Exceptions.NoServersConnected;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;

import static java.util.Collections.sort;

public class HttpGw implements Runnable{
    // variaveis para conexao aos servers
    static DatagramSocket sock;
    static int serverId = 0;
    static protocolDemultiplexer demProtocol = new protocolDemultiplexer();
    static Map<Integer, HostInfo> serversConnected = new HashMap<>();
    static int selectServer;
    static ReadWriteLock mapReadWriteLock = new ReentrantReadWriteLock();
    static Lock mapReadLock = mapReadWriteLock.readLock();
    static Lock mapWriteLock = mapReadWriteLock.writeLock();
    static ReentrantLock selectServerLock = new ReentrantLock();
    // Variaveis para conexao http
    static Set<Short> clientsConnected = new TreeSet<>();
    static ReentrantLock clientsLock = new ReentrantLock();
    static final File WEB_ROOT = new File("ErrorFiles");
    static final String DEFAULT_FILE = "index.html";
    static final String FILE_NOT_FOUND = "404.html";
    static final String NO_SERVERS_CONNECTED = "noServers.html";
    static final String METHOD_NOT_SUPPORTED = "not_supported.html";
    // port to listen connection
    static final int PORT = 8080;

    // verbose mode
    static final boolean verbose = true;

    // Client Connection via Socket Class
    private Socket connect;

    public HttpGw(Socket c) {
        connect = c;
    }

    public static void main(String args[]) {

        // Conexao http

        try {
            ServerSocket serverConnect = new ServerSocket(PORT);
            System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");
            sock = new DatagramSocket(8080);
            sock.setSoTimeout(0);
            demProtocol.start(sock);
            listenServers();
            listenServersEndConnection();
            // we listen until user halts server execution
            while (true) {

                HttpGw myServer = new HttpGw(serverConnect.accept());



                // create dedicated thread to manage the client connection
                Thread thread = new Thread(myServer);
                thread.start();
            }

        } catch (IOException e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }

    }

    public static short generateClientId(){
        Random r = new Random();
        short s = (short) r.nextInt(1 << 15);
        while(clientsConnected.contains(s)){
            s = (short) r.nextInt(1 << 15);
        }
        clientsConnected.add(s);
        return s;
    }

    public static void listenServers() {
        new Thread(()-> {
            while(true) {
                HostInfo sI;
                try {
                    sI = demProtocol.listenConn(sock);
                    if (sI != null) {
                        System.out.println("New server connected: " + sI.address + " " + sI.port);
                        mapWriteLock.lock();
                        serversConnected.put(serverId, sI);
                        mapWriteLock.unlock();
                        serverId++;
                    }

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public static void listenServersEndConnection() {
        new Thread(()-> {
            while(true) {
                HostInfo sI;
                try {
                    sI = demProtocol.receiveEndConn(sock);
                    if (sI != null) {
                        System.out.println("Server ended connection: " + sI.address + " " + sI.port);
                        mapWriteLock.lock();
                        int id = -1;
                        for (Map.Entry entry : serversConnected.entrySet()) {
                            if (sI.address.equals(((HostInfo)entry.getValue()).getAddress())) {
                                id = (int)entry.getKey();
                                break;
                            }
                        }
                        if (id != -1) serversConnected.remove(id);
                        mapWriteLock.unlock();
                    }

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public static HostInfo selectServer(String fileRequested) throws NoServersConnected, FileNotFoundException {
        mapReadLock.lock();
        if (!serversConnected.isEmpty()) {
            Set<Integer> keySet = serversConnected.keySet();
            mapReadLock.unlock();
            ArrayList<Integer> keyList = new ArrayList<>(keySet);
            sort(keyList);
            selectServerLock.lock();
            if (selectServer >= keySet.size())
                selectServer = 0;
            mapReadLock.lock();
            HostInfo server = serversConnected.get(selectServer).clone();
            mapReadLock.unlock();
        if (server.getFiles().contains(fileRequested)){
                selectServer++;
                selectServerLock.unlock();
                return server;
        }
        else {
        selectServer++;
        selectServerLock.unlock();
        mapReadLock.lock();
        for(Integer s : keyList) {                
            server = serversConnected.get(s).clone();
            if (server.getFiles().contains(fileRequested)) {
                return server;
            }
        }
        	mapReadLock.unlock();
        }
        	throw new FileNotFoundException("Ficheiro não encontrado!");
        }
        else {
            mapReadLock.unlock();
            throw new NoServersConnected("No servers connected to gateway!");
        }

    } 

    public void run() {
        // we manage our particular client connection
        BufferedReader in = null; PrintWriter out = null; BufferedOutputStream dataOut = null;
        String fileRequested = null;
        short clientId = generateClientId();
        if (verbose) {
            System.out.println("Connecton opened. (" + new Date() + ")");
        }
        try {
            // we read characters from the client via input stream on the socket
            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            // we get character output stream to client (for headers)
            out = new PrintWriter(connect.getOutputStream());
            // get binary output stream to client (for requested data)
            dataOut = new BufferedOutputStream(connect.getOutputStream());

            // get first line of the request from the client
            String input = in.readLine();
            // we parse the request with a string tokenizer
            StringTokenizer parse = new StringTokenizer(input);
            String method = parse.nextToken().toUpperCase(); // we get the HTTP method of the client
            // we get file requested
            fileRequested = parse.nextToken();

            // we support only GET and HEAD methods, we check

            // GET or HEAD method
            if (fileRequested.endsWith("/")) {
                fileRequested += DEFAULT_FILE;
            }
            String[] tokens = fileRequested.split("/");
            fileRequested = tokens[1];

            HostInfo server = selectServer(fileRequested);

            File file = demProtocol.requestFile(sock,clientId,server,fileRequested);
            int fileLength = (int) file.length();
            String content = getContentType(fileRequested);
            if (method.equals("GET")) { // GET method so we return content
                byte[] fileData = readFileData(file, fileLength);

                // send HTTP Headers
                out.println("HTTP/1.1 200 OK");
                out.println("Server: " +"Address: "+ server.address + "Port: "+ server.port);
                out.println("Date: " + new Date());
                out.println("Content-type: " + content);
                out.println("Content-length: " + fileLength);
                out.println(); // blank line between headers and content, very important !
                out.flush(); // flush character output stream buffer
                dataOut.write(fileData, 0, fileLength);
                dataOut.flush();
            }

            if (verbose) {
                System.out.println("File " + fileRequested + " of type " + content + " returned");
            }

        }
        catch (NoServersConnected e) {
            try {
                exceptions(out, dataOut, fileRequested,2);
            } catch (IOException ioe) {
                System.err.println("Error with file no servers connected exception : " + ioe.getMessage());
            }
        }
        catch (FileNotFoundException fnfe) {
            try {
                exceptions(out, dataOut, fileRequested,1);
            } catch (IOException ioe) {
                System.err.println("Error with file not found exception : " + ioe.getMessage());
            }

        }
        catch (IOException | InterruptedException ioe) {
            System.err.println("Server error : " + ioe);
        }
        finally {
            try {
                clientsLock.lock();
                clientsConnected.remove(clientId);
                clientsLock.unlock();
                in.close();
                out.close();
                dataOut.close();
                connect.close(); // we close socket connection
            } catch (Exception e) {
                System.err.println("Error closing stream : " + e.getMessage());
            }

            if (verbose) {
                System.out.println("Connection closed.\n");
            }
        }


    }

    private String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".txt"))
            return "text/html";
        else {
            if(fileRequested.endsWith("gif") || fileRequested.endsWith("jpeg") ||fileRequested.endsWith("png")) return "image/jpeg";
            else {
                if(fileRequested.endsWith("mov")|| fileRequested.endsWith("mp4")||fileRequested.endsWith("wmv")) return "video/mp4";
                else {
                    if (fileRequested.endsWith("mp3")) return "audio/mpeg";
                    else {
                        if(fileRequested.endsWith(".pdf")) return "application/pdf";
                        else
                            return "text/plain";
                    }
                }
            }
        }
    }
    private byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];

        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null)
                fileIn.close();
        }

        return fileData;
    }

    private void exceptions(PrintWriter out, OutputStream dataOut, String fileRequested, int exception) throws IOException {
        File file;
        if(exception == 1) file = new File(WEB_ROOT, FILE_NOT_FOUND);
        else file = new File(WEB_ROOT, NO_SERVERS_CONNECTED);
        int fileLength = (int) file.length();
        String content = "text/html";
        byte[] fileData = readFileData(file, fileLength);

        if(exception == 1) out.println("HTTP/1.1 404 File Not Found");
        else out.println("HTTP/1.1 502 Bad Gateway");
        out.println("Server: ");
        out.println("Date: " + new Date());
        out.println("Content-type: " + content);
        out.println("Content-length: " + fileLength);
        out.println(); // blank line between headers and content, very important !
        out.flush(); // flush character output stream buffer

        dataOut.write(fileData, 0, fileLength);
        dataOut.flush();

        if (verbose) {
            System.out.println("File " + fileRequested + " not found");
        }
    }


    //simple function to echo data to terminal
    public static void echo(String msg) {
        System.out.println(msg);
    }


}
