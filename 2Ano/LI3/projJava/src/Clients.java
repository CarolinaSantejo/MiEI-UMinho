import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Clients implements Serializable, Catalog {
    private TreeSet<Client> clients;

    public Clients() {
        this.clients = new TreeSet<>();
    }

    public boolean add(CatalogItem client) {
        if(client instanceof Client) return clients.add((Client) client);
        else return false;
    }

    public boolean find(String clientID) {
        Client tempCli = new Client(clientID);
        return clients.contains(tempCli);
    }

    public Set<String> setIDs() {
        return this.clients.stream().map(Client::getID).collect(Collectors.toSet());
    }

    public int hashCode() {
        return Objects.hash(clients);
    }
}
