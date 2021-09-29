import java.util.Set;

public class Sale {
    private String productID;
    private double unitaryPrice;
    private int unitsBought;
    private char np;
    private String clientID;
    private int monthOfPurchase;
    private int branchID;

    public Sale(String productID, double unitaryPrice, int unitsBought, char np, String clientID, int monthOfPurchase, int branchID) {
        this.productID = productID;
        this.unitaryPrice = unitaryPrice;
        this.unitsBought = unitsBought;
        this.np = np;
        this.clientID = clientID;
        this.monthOfPurchase = monthOfPurchase;
        this.branchID = branchID;
    }

    public Sale(String line) {
        String[] parts = line.split(" ");
        this.productID = parts[0];
        this.unitaryPrice = Double.parseDouble(parts[1]);
        this.unitsBought = Integer.parseInt(parts[2]);
        this.np = parts[3].charAt(0);
        this.clientID = parts[4];
        this.monthOfPurchase = Integer.parseInt(parts[5]);
        this.branchID = Integer.parseInt(parts[6]);
    }

    public String getProductID() {
        return this.productID;
    }

    public double getUnitaryPrice() {
        return this.unitaryPrice;
    }

    public int getUnitsBought() {
        return this.unitsBought;
    }

    public boolean isOnSale() {
        return this.np == 'P';
    }

    public String getClientID() {
        return this.clientID;
    }

    public int getMonthOfPurchase() {
        return this.monthOfPurchase;
    }

    public int getBranchID() {
        return this.branchID;
    }

    public double getTotalProfit() {
        return this.unitaryPrice * this.unitsBought;
    }

    public static boolean isValid(Sale sale, Clients clients, Products products) {
        return clients.find(sale.clientID) &&
                products.find(sale.productID) &&
                sale.unitaryPrice >= 0 && sale.unitaryPrice < 1000 &&
                sale.unitsBought >= 1 && sale.unitsBought <= 200 &&
                (sale.np == 'N' || sale.np == 'P') &&
                sale.monthOfPurchase >= 1 && sale.monthOfPurchase <= 12 &&
                sale.branchID >= 1 && sale.branchID <= 3;
    }

    public static boolean isValid(Sale sale, Set<String> clients, Set<String> products) {
        return clients.contains(sale.getClientID()) &&
                products.contains(sale.getProductID()) &&
                sale.unitaryPrice >= 0 && sale.unitaryPrice < 1000 &&
                sale.unitsBought >= 1 && sale.unitsBought <= 200 &&
                (sale.np == 'N' || sale.np == 'P') &&
                sale.monthOfPurchase >= 1 && sale.monthOfPurchase <= 12 &&
                sale.branchID >= 1 && sale.branchID <= 3;
    }
}
