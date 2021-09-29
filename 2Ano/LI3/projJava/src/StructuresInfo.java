import java.io.Serializable;
import java.util.Arrays;

public class StructuresInfo implements Serializable {
    private int[] sales; // by month
    private double[][] totalProfit;
    private int[][] distinctBuyers;

    public StructuresInfo() {
        this.sales = new int[12];
        this.totalProfit = new double[12][3];
        this.distinctBuyers = new int[12][3];
    }

    public StructuresInfo(StructuresInfo si) {
        this.sales = si.sales.clone();
        this.totalProfit = si.totalProfit.clone();
        this.distinctBuyers = si.distinctBuyers.clone();
    }

    public void addSale(int month) {
        this.sales[month - 1]++;
    }

    public void addProfit(int month, int branch, double profit) {
        this.totalProfit[month - 1][branch - 1] += profit;
    }

    public void addBuyer(int month, int branch) {
        this.distinctBuyers[month - 1][branch - 1]++;
    }

    public int getSales(int month) {
        return this.sales[month - 1];
    }

    public double getProfit(int month, int branch) {
        return this.totalProfit[month - 1][branch - 1];
    }

    public double getProfit() {
        int total = 0;
        for(int month = 0; month < 12; month++) {
            total += Arrays.stream(this.totalProfit[month]).sum();
        }
        return total;
    }

    public int getBuyers(int month, int branch) {
        return this.distinctBuyers[month - 1][branch - 1];
    }

    public StructuresInfo clone() {
        return new StructuresInfo(this);
    }

    public int hashCode() {
        int result = Arrays.hashCode(sales);
        result = 31 * result + Arrays.hashCode(totalProfit);
        result = 31 * result + Arrays.hashCode(distinctBuyers);
        return result;
    }
}
