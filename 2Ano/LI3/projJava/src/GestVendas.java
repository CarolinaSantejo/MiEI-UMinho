import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.stream.Stream;

public class GestVendas implements Serializable {
    private Clients clients;
    private Products products;
    private Bills bills;
    private ArrayList<Branch> branches;
    private FileInfo fileInfo;
    private StructuresInfo structuresInfo;

    public GestVendas() {
        this.clients = new Clients();
        this.products = new Products();
        this.bills = new Bills();
        this.branches = new ArrayList<>(3);
        for(int i = 0; i < 3; i++) this.branches.add(new Branch());
        this.fileInfo = new FileInfo();
        this.structuresInfo = new StructuresInfo();
    }

    public FileInfo getFileInfo() {
        return this.fileInfo.clone();
    }

    public StructuresInfo getStructuresInfo() {
        return this.structuresInfo.clone();
    }

    public void loadSGVFromFiles(String clientsFilePath, String productsFilePath, String salesFilePath) {
        this.fileInfo = new FileInfo(clientsFilePath, productsFilePath, salesFilePath);
        try {
            Files.lines(Paths.get(clientsFilePath)).forEach(l -> this.fileInfo.addClient(this.clients.add(new Client(l))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.lines(Paths.get(productsFilePath)).forEach(l -> this.fileInfo.addProduct(this.products.add(new Product(l))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader br = Files.newBufferedReader(Paths.get(salesFilePath));
            String line;
            Set<String> clientIDs = new HashSet<>();
            List<List<Set<String>>> clientIDsX = new ArrayList<>(12);
            for(int i = 0; i < 12; i++) clientIDsX.add(new ArrayList<>(Arrays.asList(new HashSet<>(), new HashSet<>(), new HashSet<>())));
            while((line = br.readLine()) != null) {
                Sale sale = new Sale(line);
                if(Sale.isValid(sale, this.clients, this.products)) {
                    clientIDs.add(sale.getClientID());
                    this.fileInfo.addSale(true, sale.getTotalProfit(), sale.getMonthOfPurchase(), sale.getBranchID());
                    this.bills.addToBills(sale);
                    this.branches.get(sale.getBranchID() - 1).addToBranch(sale);

                    this.structuresInfo.addSale(sale.getMonthOfPurchase());
                    this.structuresInfo.addProfit(sale.getMonthOfPurchase(), sale.getBranchID(), sale.getTotalProfit());
                    clientIDsX.get(sale.getMonthOfPurchase() - 1).get(sale.getBranchID() - 1).add(sale.getClientID());
                }
                else this.fileInfo.addSale(false, 0, 0 ,0);
            }
            this.fileInfo.setClientsWhoBought(clientIDs.size());
            for(int i = 0; i < 12; i++) {
                for(int j = 0; j < 3; j++) {
                    for(String x : clientIDsX.get(i).get(j)) {
                        this.structuresInfo.addBuyer(i + 1, j + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(GestVendas sgv, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(sgv);
        oos.close();
        fos.close();
    }

    public static GestVendas load(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        GestVendas loaded = (GestVendas) ois.readObject();
        ois.close();
        fis.close();
        return loaded;
    }

    /* Query 1 */
    public List<String> getProductsNeverBought() {
        return this.products.filter(p -> !this.bills.containsProduct(p.getID())).stream()
                .map(CatalogItem::getID)
                .collect(Collectors.toList());
    }

    /* Query 2 */
    public Map<Integer, AbstractMap.SimpleEntry<Integer, Integer>> getTotalSalesAndClients(int month) {
        Map<Integer, AbstractMap.SimpleEntry<Integer, Integer>> result = new HashMap<>();

        Iterator<Branch> iter = this.branches.iterator();

        result.put(0, new AbstractMap.SimpleEntry<>(0,0));

        int i = 1;
        while(iter.hasNext()) {
            Branch branch = iter.next();
            AbstractMap.SimpleEntry<Integer, Integer> value = new AbstractMap.SimpleEntry<>(this.bills.getTotalSales(i, month), branch.clientsWhoBoughtIn(month).size());
            result.merge(0, value, (e1, e2)-> new AbstractMap.SimpleEntry<>(e1.getKey() + e2.getKey(), e1.getValue() + e2.getValue()));
            result.put(i, value);
            i++;
        }

        return result;
    }

    /* Query 3 */
    public HashMap<Integer, List<Double>> getClientSalesInfo(String clientID) {
        HashMap<Integer, List<Double>> result = new HashMap<>();
        for(int month = 1; month <= 12; month++) {
            int finalMonth = month;
            int salesNum = this.branches.stream().mapToInt(b -> b.totalSalesBy(clientID, finalMonth)).sum();
            Set<String> prodsBought = new HashSet<>();
            this.branches.stream().map(b -> b.productsBoughtBy(clientID, finalMonth)).forEach(prodsBought::addAll);
            int prodsBoughtNum = prodsBought.size();
            double totalSpent = this.branches.stream().mapToDouble(b -> b.totalSpentBy(clientID, finalMonth)).sum();
            result.put(month, Arrays.asList((double) salesNum, (double) prodsBoughtNum, totalSpent));
        }
        return result;
    }

    /* Query 4 */

    public HashMap<Integer, List<Double>> getProductSalesInfo(String productID) {
        HashMap<Integer, List<Double>> result = new HashMap<>();
        for(int month = 1; month <= 12; month++) {
            int finalMonth = month;
            int salesNum = this.branches.stream().mapToInt(b -> b.totalSalesOf(productID, finalMonth)).sum();
            Set<String> clientsWhoBoughtX = new HashSet<>();
            this.branches.stream().map(b -> b.clientsWhoBought(productID, finalMonth)).forEach(clientsWhoBoughtX::addAll);
            int clientsWhoBoughtXNum = clientsWhoBoughtX.size();
            double totalSpent = this.branches.stream().mapToDouble(b -> b.totalProfitBy(productID, finalMonth)).sum();
            result.put(month, Arrays.asList((double) salesNum, (double) clientsWhoBoughtXNum, totalSpent));
        }
        return result;
    }

    /*Query 5*/
    public List<Map.Entry<String, Integer>> mostBoughtProducts(String clientID){
        Map<String, Integer> map = new TreeMap<>();
        for(Branch b : this.branches) {
            b.productsBoughtBy(clientID).forEach((key, value) -> map.merge(key, value, Integer::sum));
        }
        return map.entrySet().stream()
                .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()) == 0 ? e1.getKey().compareTo(e2.getKey()) : e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toList());
    }

    /*Query 6*/

    public List<Map.Entry<String, AbstractMap.SimpleEntry<Integer, Integer>>> mostSoldProducts(int N){
        List<Map.Entry<String, AbstractMap.SimpleEntry<Integer, Integer>>> result = new ArrayList<>();
        List<Bill> mostSold = this.bills.sorted((b1, b2) -> b2.getTotalQuantitySold() - b1.getTotalQuantitySold());
        for(int i = 0; i < N && i < mostSold.size(); i++) {
            Bill bill = mostSold.get(i);
            result.add(new AbstractMap.SimpleEntry<>(bill.getProductID(), new AbstractMap.SimpleEntry<>(bill.getTotalQuantitySold(),
                    this.branches.stream().map(b -> b.clientsWhoBought(bill.getProductID())).flatMap(b -> b.keySet().stream()).collect(Collectors.toSet()).size())));
        }
        return result;
    }

    /*Query 7*/

    public Map<Integer, List<Map.Entry<String, Double>>> threeBiggestBuyers() {
        Map<Integer, List<Map.Entry<String, Double>>> result = new HashMap<>();
        for(int i = 0; i < this.branches.size(); i++) {
            result.put(i + 1, this.branches.get(i).getMoneySpentByClients().entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()) == 0 ? e1.getKey().compareTo(e2.getKey()) : e2.getValue().compareTo(e1.getValue()))
            .limit(3)
            .collect(Collectors.toList()));
        }
        return result;
    }

    /*Query 8*/

    public List<Map.Entry<String, Integer>> clientsWhoBoughtMostProducts(int N) {
        Map<String, Set<String>> productsBoughtByClients = new HashMap<>();
        for(Branch branch : branches) {
            branch.getProductsBoughtByClients().forEach((k,v) -> productsBoughtByClients.merge(k, v, (v1,v2) -> Stream.concat(v1.stream(),v2.stream()).collect(Collectors.toSet())));
        }
        return productsBoughtByClients.entrySet().stream()
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue().size()))
                .sorted((e1, e2) -> e2.getValue() - e1.getValue() == 0 ? e1.getKey().compareTo(e2.getKey()) : e2.getValue() - e1.getValue())
                .limit(N)
                .collect(Collectors.toList());
    }

    /*Query 9*/

    public List<Map.Entry<String, Map.Entry<Integer, Double>>> clientsWhoBoughtProductMost(String prodID, int N) {
        Map<String, Map.Entry<Integer, Double>> quantAndProfitByClient = new HashMap<>();
        for(Branch branch : branches) {
            branch.getProductQuantBoughtAndProfitByClient(prodID).forEach((k,v) -> quantAndProfitByClient.merge(k, v, (v1, v2) -> new AbstractMap.SimpleEntry<Integer, Double>(v1.getKey() + v2.getKey(), v1.getValue() + v2.getValue())));
        }
        return quantAndProfitByClient.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().getKey() - e1.getValue().getKey() == 0 ? e2.getValue().getValue().intValue() - e1.getValue().getValue().intValue() : e2.getValue().getKey() - e1.getValue().getKey())
                .limit(N)
                .collect(Collectors.toList());
    }

    /*Query 10*/
    public List<Map.Entry<String, List<List<Double>>>> totalProfit() {
        Map<String, List<List<Double>>> result = new HashMap<>();
        for(String prodID : products.setIDs()) {
            List<List<Double>> months = new ArrayList<>();
            for (int month = 1; month <= 12; month++) {
                List<Double> branches = new ArrayList<>();
                for (int branch = 1; branch <= this.branches.size(); branch++) {
                    branches.add(this.branches.get(branch - 1).totalProfitBy(prodID, month));
                }
                months.add(branches);
            }
            result.put(prodID, months);
        }
        return result.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toList());
    }
}
