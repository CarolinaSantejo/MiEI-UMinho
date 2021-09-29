import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Bills implements Serializable {
    private HashMap<String, Bill> bills;

    public Bills() {
        this.bills = new HashMap<>();
    }

    public void addToBills(Sale sale) {
        Bill bill = bills.get(sale.getProductID());
        if(bill == null) {
            bill = new Bill(sale.getProductID());
            bills.put(bill.getProductID(), bill);
        }
        bill.addSale(sale.getUnitsBought(), sale.getBranchID(), sale.getMonthOfPurchase(), sale.isOnSale());
        bill.addProfit(sale.getTotalProfit(), sale.getBranchID(), sale.getMonthOfPurchase(), sale.isOnSale());
    }

    public boolean containsProduct(String prodID) {
        return this.bills.containsKey(prodID);
    }

    public List<Bill> sorted(Comparator<Bill> comparator) {
        return this.bills.values().stream().sorted(comparator).collect(Collectors.toList());
    }

    public int getTotalSales(int branchID, int month) {
        return this.bills.values().stream()
                .mapToInt(b -> b.getSales(branchID, month))
                .sum();
    }

    public double getTotalProfit(int branchID, int month) {
        return this.bills.values().stream()
                .mapToDouble(b -> b.getProfit(branchID, month))
                .sum();
    }

    public int hashCode() {
        return Objects.hash(bills);
    }
}
