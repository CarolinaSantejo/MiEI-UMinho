import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;


class ContactListRW {
    private List<Contact> contacts;
    private ReentrantLock lock = new ReentrantLock();

    public ContactListRW() {
        contacts = new ArrayList<>();

        contacts.add(new Contact("John", 20, 253123321, null, new ArrayList<>(Arrays.asList("john@mail.com"))));
        contacts.add(new Contact("Alice", 30, 253987654, "CompanyInc.", new ArrayList<>(Arrays.asList("alice.personal@mail.com", "alice.business@mail.com"))));
        contacts.add(new Contact("Bob", 40, 253123456, "Comp.Ld", new ArrayList<>(Arrays.asList("bob@mail.com", "bob.work@mail.com"))));
    }

    // @TODO
    public boolean addContact (DataInputStream c) throws IOException {
        Contact contact = Contact.deserialize(c);
        lock.lock();
        try {
            contacts.add(contact);
        }
        finally {
            lock.unlock();
        }
        return true;
    }

    // @TODO
    public void getContacts () throws IOException {

        for(Contact c : contacts) {
            System.out.println(c.toString());
        }
    }

    public void serialize (DataOutputStream out) throws IOException {
        lock.lock();
        try {
            out.writeInt(contacts.size());
            for(Contact c : contacts) {
                c.serialize(out);
            }
        }
        finally {
            lock.unlock();
        }
    }

}

class ServerWorkerW implements Runnable {
    private Socket socket;
    private ContactListRW contacts = new ContactListRW();
    public ServerWorkerW (Socket socket, ContactListRW contactList) {
        this.socket = socket;
        this.contacts = contactList;
    }

    // @TODO
    @Override
    public void run() {
        System.out.println("New connection established #" + Thread.currentThread().getId());
        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            boolean isOpen = true;
            while (isOpen) {
                try {
                    contacts.addContact(in);
                }
                catch (EOFException e) {
                    isOpen = false;
                }
            }
            contacts.getContacts();

            socket.shutdownInput();
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

class ServerWorkerR implements Runnable {
    private Socket socket;
    private ContactListRW contacts = new ContactListRW();
    public ServerWorkerR (Socket socket, ContactListRW contactList) {
        this.socket = socket;
        this.contacts = contactList;
    }

    // @TODO
    @Override
    public void run() {
        System.out.println("New connection established #" + Thread.currentThread().getId());
        try {
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            contacts.serialize(out);
            socket.shutdownInput();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

class SWorkerRW implements Runnable {
    ServerSocket sSocket;
    private ContactListRW contacts;
    private int wType;
    public SWorkerRW (int port, ContactListRW contactList, int type) throws IOException {
        this.sSocket = new ServerSocket(port);
        this.contacts = contactList;
        this.wType = type;
    }
    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = sSocket.accept();
            Thread worker;
            if (wType == 0) {
                worker = new Thread(new ServerWorkerW(socket, contacts));
            }
            else {
                worker = new Thread(new ServerWorkerR(socket, contacts));
            }
            worker.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


public class ServerRW {

    public static void main (String[] args) throws IOException {
        ContactListRW contactList = new ContactListRW();
        while (true) {
            Thread workerW = new Thread(new SWorkerRW(12345, contactList,0));
            Thread workerR = new Thread(new SWorkerRW(34567, contactList,1));
            workerW.start();
            workerR.start();
        }
    }

}
