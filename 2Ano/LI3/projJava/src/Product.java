import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Product implements Comparable<Product>, Serializable, CatalogItem {
    private final String productID;

    public Product(String prodID) {
        this.productID = prodID;
    }

    public String getID() {
        return this.productID;
    }

    public static boolean isValidID(String prodID) {
        if(prodID == null) return false;
        Pattern pattern = Pattern.compile("[A-Z]{2}([1-9][0-9]{3})");
        Matcher matcher = pattern.matcher(prodID);
        return matcher.find();
    }

    public int compareTo(Product p) {
        return this.productID.compareTo(p.productID);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return this.productID.equals(product.productID);
    }

    public int hashCode() {
        return Objects.hash(productID);
    }
}
