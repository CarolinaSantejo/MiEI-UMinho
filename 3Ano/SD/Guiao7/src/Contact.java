import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

class Contact {
    private String name;
    private int age;
    private long phoneNumber;
    private String company;     // Pode ser null
    private List<String> emails;

    public Contact (String name, int age, long phone_number, String company, List<String> emails) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phone_number;
        this.company = company;
        this.emails = new ArrayList<>(emails);
    }
    public static Contact deserialize (DataInputStream in) throws IOException {
        String name = in.readUTF();
        int age = in.readInt();
        long phoneNumber = in.readLong();
        String company = null;
        if (in.readBoolean())  company = in.readUTF();
        List<String> emails = new ArrayList<>();
        int numberEmails = in.readInt();
        for (int i = 0; i<numberEmails; i++)
            emails.add(in.readUTF());
        return new Contact(name,age,phoneNumber,company,emails);
    }

    public void serialize (DataOutputStream out) throws IOException {
        out.writeUTF(this.name);
        out.writeInt(this.age);
        out.writeLong(this.phoneNumber);
        boolean comp = this.company != null;
        if (comp) out.writeUTF(this.company);
        int size = this.emails.size();
        out.writeInt(size);
        for (String e: this.emails)
            out.writeUTF(e);
        out.flush();
    }

    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append(this.name).append(";");
        builder.append(this.age).append(";");
        builder.append(this.phoneNumber).append(";");
        builder.append(this.company).append(";");
        builder.append("{");
        for (String s : this.emails) {
            builder.append(s).append(";");
        }
        builder.append("}");
        return builder.toString();
    }

}
