public interface CatalogItem {
    static boolean isValidID(String itemID) {
        return itemID != null;
    }

    String getID();
}
