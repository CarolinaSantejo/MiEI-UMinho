import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer  {

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(12345);
            while (true) {
                Socket socket = ss.accept();
                ServerWorker s = new ServerWorker(socket);
                Thread t = new Thread(s);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static class ServerWorker implements Runnable {
        Socket socket;
        ServerWorker(Socket s) {
            this.socket = s;
        }
        public void run () {
            try {
                System.out.println("New connection established #" + Thread.currentThread().getId());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                int soma = 0;
                int c = 0;
                String line;
                while ((line = in.readLine()) != null) {
                    try {
                        soma += Integer.parseInt(line);
                    } catch (NumberFormatException e) { }
                    c++;
                    out.println(soma);
                    out.flush();
                }
                float media = (float)soma/c;
                out.println(media);
                out.flush();

                socket.shutdownOutput();
                socket.shutdownInput();
                socket.close();
            }
            catch (IOException e) {}
        }
    }

}
