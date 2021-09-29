import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface Catalog {
    boolean add(CatalogItem item);

    boolean find(String itemID);

    Set<String> setIDs();
}
