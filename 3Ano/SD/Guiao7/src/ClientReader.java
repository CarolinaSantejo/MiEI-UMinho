import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientReader {

    public static void main (String[] args) throws IOException {
        Socket socket = new Socket("localhost", 34567);

        DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        int size = in.readInt();
        Contact c;
        for (int i = 0; i<size; i++) {
            c = Contact.deserialize(in);
            System.out.println(i+1 + ":" + c.toString());
        }
        socket.shutdownInput();
        socket.close();
    }
}
