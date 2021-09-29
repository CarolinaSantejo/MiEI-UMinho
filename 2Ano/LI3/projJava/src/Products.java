import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Products implements Serializable, Catalog {
    private TreeSet<Product> products;

    public Products() {
        this.products = new TreeSet<>();
    }

    public boolean add(CatalogItem product) {
        if(product instanceof Product) return products.add((Product) product);
        else return false;
    }

    public boolean find(String prodID) {
        Product tempProd = new Product(prodID);
        return products.contains(tempProd);
    }

    public Set<String> setIDs() {
        return this.products.stream().map(Product::getID).collect(Collectors.toSet());
    }

    public List<Product> filter(Predicate<? super Product> predicate) {
        return products.stream().filter(predicate).collect(Collectors.toList());
    }

    public int hashCode() {
        return Objects.hash(products);
    }
}
