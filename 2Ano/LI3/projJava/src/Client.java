import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client implements Comparable<Client>, Serializable, CatalogItem {
    private final String clientID;

    public Client(String clientID) {
        this.clientID = clientID;
    }

    public String getID() {
        return this.clientID;
    }

    public static boolean isValidID(String clientID) {
        if(clientID == null) return false;
        Pattern pattern = Pattern.compile("[A-Z]([1-4][0-9]{3}|5000)");
        Matcher matcher = pattern.matcher(clientID);
        return matcher.find();
    }

    public int compareTo(Client c) {
        return this.clientID.compareTo(c.clientID);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return this.clientID.equals(client.clientID);
    }

    public int hashCode() {
        return Objects.hash(clientID);
    }
}
