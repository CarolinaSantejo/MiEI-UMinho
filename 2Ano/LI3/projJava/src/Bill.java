import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Bill implements Serializable {
    private String productID;
    private double[][][] totalProfit;
    private int[][][] totalSales;
    private int[][][] totalQuantitySold;

    public Bill(String productID) {
        this.productID = productID;
        this.totalProfit = new double[3][12][2];
        this.totalSales = new int[3][12][2];
        this.totalQuantitySold = new int[3][12][2];
    }

    public Bill(Bill bill) {
        this.productID = bill.productID;
        this.totalProfit = bill.totalProfit.clone();
        this.totalSales = bill.totalSales.clone();
        this.totalQuantitySold = bill.totalQuantitySold.clone();
    }

    public String getProductID() {
        return this.productID;
    }

    public void addSale(int quant, int branchID, int month, boolean onSale) {
        this.totalSales[branchID - 1][month - 1][onSale ? 1 : 0]++;
        this.totalQuantitySold[branchID - 1][month - 1][onSale ? 1 : 0] += quant;
    }

    public void addProfit(double profit, int branchID, int month, boolean onSale) {
        this.totalProfit[branchID - 1][month - 1][onSale ? 1 : 0] += profit;
    }

    public int getSales(int branchID, int month) {
        return this.totalSales[branchID - 1][month - 1][0] + this.totalSales[branchID - 1][month - 1][1];
    }

    public int getSales(int branchID, int month, boolean onSale) {
        return this.totalSales[branchID - 1][month - 1][onSale ? 1 : 0];
    }

    public double getProfit(int branchID, int month) {
        return this.totalProfit[branchID - 1][month - 1][0] + this.totalProfit[branchID - 1][month - 1][1];
    }

    public double getProfit(int branchID, int month, boolean onSale) {
        return this.totalProfit[branchID - 1][month - 1][onSale ? 1 : 0];
    }

    public int getTotalSaleMonth(int month, int branch){
        return this.totalSales[branch][month][0]+totalSales[branch][month][1];
    }

    public double getTotalSpentMonth(int month, int branch){
        return this.totalProfit[branch][month][0]+this.totalProfit[branch][month][1];
    }

    public int getTotalQuantitySold() {
        int total = 0;
        for(int branch = 0; branch < 3; branch++) {
            for(int month = 0; month < 12; month++) {
                total += this.totalQuantitySold[branch][month][0] + this.totalQuantitySold[branch][month][1];
            }
        }
        return total;
    }

    public Bill clone() {
        return new Bill(this);
    }


    public int hashCode() {
        int result = Objects.hash(productID);
        result = 31 * result + Arrays.hashCode(totalProfit);
        result = 31 * result + Arrays.hashCode(totalSales);
        result = 31 * result + Arrays.hashCode(totalQuantitySold);
        return result;
    }
}