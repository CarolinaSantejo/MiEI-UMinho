import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class FileInfo implements Serializable {
    private String clientsPath;
    private int totalClients;
    private int validClients;
    private int clientsWhoBought;

    private String productsPath;
    private int totalProducts;
    private int validProducts;

    private String salesPath;
    private int totalSales;
    private int validSales;
    private int freeSales;
    private double totalProfit;

    public FileInfo() {
        this.clientsPath = null;
        this.totalClients = 0;
        this.validClients = 0;
        this.clientsWhoBought = 0;

        this.productsPath = null;
        this.totalProducts = 0;
        this.validProducts = 0;

        this.salesPath = null;
        this.totalSales = 0;
        this.validSales = 0;
        this.freeSales = 0;
        this.totalProfit = 0;
    }

    public FileInfo(String clientsPath, String productsPath, String salesPath) {
        this.clientsPath = clientsPath;
        this.totalClients = 0;
        this.validClients = 0;
        this.clientsWhoBought = 0;

        this.productsPath = productsPath;
        this.totalProducts = 0;
        this.validProducts = 0;

        this.salesPath = salesPath;
        this.totalSales = 0;
        this.validSales = 0;
        this.freeSales = 0;
        this.totalProfit = 0;
    }

    public FileInfo(String clientsPath, int totalClients, int validClients, int clientsWhoBought, String productsPath,
                    int totalProducts, int validProducts, String salesPath, int totalSales, int validSales, int freeSales, double totalProfit) {
        this.clientsPath = clientsPath;
        this.totalClients = totalClients;
        this.validClients = validClients;
        this.clientsWhoBought = clientsWhoBought;
        this.productsPath = productsPath;
        this.totalProducts = totalProducts;
        this.validProducts = validProducts;
        this.salesPath = salesPath;
        this.totalSales = totalSales;
        this.validSales = validSales;
        this.freeSales = freeSales;
        this.totalProfit = totalProfit;
    }

    public String getClientsPath() {
        return this.clientsPath;
    }

    public void setClientsPath(String clientsPath) {
        this.clientsPath = clientsPath;
    }

    public int getTotalClients() {
        return this.totalClients;
    }

    public void setTotalClients(int totalClients) {
        this.totalClients = totalClients;
    }

    public int getValidClients() {
        return this.validClients;
    }

    public void setValidClients(int validClients) {
        this.validClients = validClients;
    }

    public int getClientsWhoBought() {
        return this.clientsWhoBought;
    }

    public void setClientsWhoBought(int clientsWhoBought) {
        this.clientsWhoBought = clientsWhoBought;
    }

    public String getProductsPath() {
        return this.productsPath;
    }

    public void setProductsPath(String productsPath) {
        this.productsPath = productsPath;
    }

    public int getTotalProducts() {
        return this.totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getValidProducts() {
        return this.validProducts;
    }

    public void setValidProducts(int validProducts) {
        this.validProducts = validProducts;
    }

    public String getSalesPath() {
        return this.salesPath;
    }

    public void setSalesPath(String salesPath) {
        this.salesPath = salesPath;
    }

    public int getTotalSales() {
        return this.totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public int getValidSales() {
        return this.validSales;
    }

    public void setValidSales(int validSales) {
        this.validSales = validSales;
    }

    public int getFreeSales() {
        return this.freeSales;
    }

    public void setFreeSales(int freeSales) {
        this.freeSales = freeSales;
    }

    public double getTotalProfit() {
        return this.totalProfit;
    }

    public void addProduct(boolean valid) {
        this.totalProducts++;
        if(valid) this.validProducts++;
    }

    public void addClient(boolean valid) {
        this.totalClients++;
        if(valid) this.validClients++;
    }

    public void addSale(boolean valid, double totalProfit, int month, int branchID) {
        this.totalSales++;
        if(valid) {
            this.validSales++;
            if(totalProfit == 0) this.freeSales++;
            this.totalProfit += totalProfit;
        }
    }

    public String toString() {
        final StringBuffer sb = new StringBuffer("FileInfo {\n");
        sb.append("\tclientsPath = '").append(clientsPath).append("'\n");
        sb.append("\ttotalClients = ").append(totalClients).append('\n');
        sb.append("\tvalidClients = ").append(validClients).append('\n');
        sb.append("\tclientsWhoBought = ").append(clientsWhoBought).append("\n");
        sb.append("\tproductsPath = '").append(productsPath).append("'\n");
        sb.append("\ttotalProducts = ").append(totalProducts).append('\n');
        sb.append("\tvalidProducts = ").append(validProducts).append('\n');
        sb.append("\tsalesPath = '").append(salesPath).append("'\n");
        sb.append("\ttotalSales = ").append(totalSales).append('\n');
        sb.append("\tvalidSales = ").append(validSales).append('\n');
        sb.append("\tfreeSales = ").append(freeSales).append("\n");
        sb.append("\ttotalProfit = ").append(totalProfit).append("\n");
        sb.append("\n}");
        return sb.toString();
    }

    public FileInfo clone() {
        return new FileInfo(clientsPath, totalClients, validClients, clientsWhoBought, productsPath,
                totalProducts, validProducts, salesPath, totalSales, validSales, freeSales, totalProfit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientsPath, totalClients, validClients, clientsWhoBought, productsPath, totalProducts, validProducts, salesPath, totalSales, validSales, freeSales, totalProfit);
    }
}
