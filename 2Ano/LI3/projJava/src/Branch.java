import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Branch implements Serializable {
    private class CliProdInfo implements Serializable{
        private short[] sales;
        private short[] quantity;
        private double[] moneySpent;

        public CliProdInfo() {
            this.sales = new short[12];
            this.quantity = new short[12];
            this.moneySpent = new double[12];
        }

        public void addInfo(int quant, double moneySpent, int month) {
            this.sales[month - 1] += 1;
            this.quantity[month - 1] += quant;
            this.moneySpent[month - 1] += moneySpent;
        }

        public int getSalesNum() {
            int total = 0;
            for(int month = 0; month < 12; month++) total += this.sales[month];
            return total;
        }

        public int getSalesNum(int month) {
            return this.sales[month - 1];
        }

        public double getMoneySpent(int month) {
            return this.moneySpent[month - 1];
        }
        
        public double getMoneySpent() {
            return Arrays.stream(this.moneySpent).sum();
        }

        public int getQuantity() {
            int total = 0;
            for(int month = 0; month < 12; month++) total += this.quantity[month];
            return total;
        }

        public int getQuantity(int month) {
            return this.quantity[month - 1];
        }
    }

    private HashMap<String, HashMap<String, CliProdInfo>> productsBoughtBy;
    private HashMap<String, HashMap<String, CliProdInfo>> clientsWhoBought;

    public Branch() {
        this.productsBoughtBy = new HashMap<>();
        this.clientsWhoBought = new HashMap<>();
    }

    public void addToBranch(Sale sale) {
        HashMap<String, CliProdInfo> productsBoughtByX = productsBoughtBy.computeIfAbsent(sale.getClientID(), k -> new HashMap<>());
        CliProdInfo prodInfo = productsBoughtByX.computeIfAbsent(sale.getProductID(), k -> new CliProdInfo());
        prodInfo.addInfo(sale.getUnitsBought(), sale.getTotalProfit(), sale.getMonthOfPurchase());

        HashMap<String, CliProdInfo> clientsWhoBoughtX = clientsWhoBought.computeIfAbsent(sale.getProductID(), k -> new HashMap<>());
        CliProdInfo CliProdInfo = clientsWhoBoughtX.computeIfAbsent(sale.getClientID(), k -> new CliProdInfo());
        CliProdInfo.addInfo(sale.getUnitsBought(), sale.getTotalProfit(), sale.getMonthOfPurchase());
    }

    public HashSet<String> clientsWhoBoughtIn(int month) {
        HashSet<String> result = new HashSet<>();

        for(HashMap<String, CliProdInfo> clientsWhoBoughtX : this.clientsWhoBought.values()) {
            result.addAll(clientsWhoBoughtX.entrySet().stream()
                    .filter(e -> e.getValue().getQuantity(month) != 0)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet()));
        }

        return result;
    }

    public Map<String, Integer> productsBoughtBy(String clientID) {
        if(!this.productsBoughtBy.containsKey(clientID)) return new HashMap<>();
        return this.productsBoughtBy.get(clientID).entrySet().stream()
                .filter(e -> e.getValue().getQuantity() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, e ->e.getValue().getQuantity()));
    }

    public Set<String> productsBoughtBy(String clientID, int month) {
        if(!this.productsBoughtBy.containsKey(clientID)) return new HashSet<>();
        return this.productsBoughtBy.get(clientID).entrySet().stream()
                .filter(e -> e.getValue().getSalesNum(month) > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public Map<String, Integer> clientsWhoBought(String productID) {
        if(!this.clientsWhoBought.containsKey(productID)) return new HashMap<>();
        return this.clientsWhoBought.get(productID).entrySet().stream()
                .filter(e -> e.getValue().getSalesNum() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, e ->e.getValue().getSalesNum()));
    }

    public Set<String> clientsWhoBought(String productID, int month) {
        if(!this.clientsWhoBought.containsKey(productID)) return new HashSet<>();
        return this.clientsWhoBought.get(productID).entrySet().stream()
                .filter(e -> e.getValue().getSalesNum(month) > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public int totalSalesBy(String clientID, int month) {
        if(!this.productsBoughtBy.containsKey(clientID)) return 0;
        return this.productsBoughtBy.get(clientID).values().stream()
                .mapToInt(prodInfo -> prodInfo.getSalesNum(month))
                .sum();
    }

    public int totalSalesOf(String productID, int month) {
        if(!this.clientsWhoBought.containsKey(productID)) return 0;
        return this.clientsWhoBought.get(productID).values().stream()
                .mapToInt(CliProdInfo -> CliProdInfo.getSalesNum(month))
                .sum();
    }

    public double totalProfitBy(String productID, int month) {
        if(!this.clientsWhoBought.containsKey(productID)) return 0;
        return this.clientsWhoBought.get(productID).values().stream()
                .mapToDouble(CliProdInfo -> CliProdInfo.getMoneySpent(month))
                .sum();
    }

    public double totalSpentBy(String clientID) {
        if(!this.productsBoughtBy.containsKey(clientID)) return 0;
        return this.productsBoughtBy.get(clientID).values().stream()
                .mapToDouble(CliProdInfo::getMoneySpent)
                .sum();
    }

    public double totalSpentBy(String clientID, int month) {
        if(!this.productsBoughtBy.containsKey(clientID)) return 0;
        return this.productsBoughtBy.get(clientID).values().stream()
                .mapToDouble(prodInfo -> prodInfo.getMoneySpent(month))
                .sum();
    }

    public Map<String, Double> getMoneySpentByClients() {
        return this.productsBoughtBy.entrySet().stream()
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue().values().stream().mapToDouble(CliProdInfo::getMoneySpent).sum()))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    public Map<String, Set<String>> getProductsBoughtByClients() {
        return this.productsBoughtBy.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().keySet()));
    }

    public Map<String, Map.Entry<Integer, Double>> getProductQuantBoughtAndProfitByClient(String prodID) {
        Map<String, CliProdInfo> clientList = this.clientsWhoBought.get(prodID);
        if(clientList == null) return new HashMap<>();
        return clientList.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new AbstractMap.SimpleEntry<>(e.getValue().getQuantity(), e.getValue().getMoneySpent())));
    }


    public int hashCode() {
        return Objects.hash(productsBoughtBy, clientsWhoBought);
    }
}