import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Benchmark {
    public static void main(String[] args) {
        GestVendas sgv = new GestVendas();
        List<String> files = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        while(option < 1 || option > 4) {
            System.out.println("\tBENCHMARK\n");
            System.out.println("1) 1 milhão de vendas.");
            System.out.println("2) 3 milhões de vendas.");
            System.out.println("3) 5 milhões de vendas.");
            System.out.println("4) Todos os anteriores.");
            System.out.print("\nSelecione a opção que pretende: ");
            option = Integer.parseInt(scanner.nextLine());
        }
        if(option == 1 || option == 4) files.add("db/Vendas_1M.txt");
        if(option == 2 || option == 4) files.add("db/Vendas_3M.txt");
        if(option == 3 || option == 4) files.add("db/Vendas_5M.txt");
        for(String fileName : files) {

            sgv.loadSGVFromFiles("db/Clientes.txt", "db/Produtos.txt", fileName);
            List<Double> benchmark = new ArrayList<>(12);
            double times[] = new double[5];

            //-------Query 1---------//

            for (int i = 0; i < 5; i++) {
                Crono.start();
                sgv.getProductsNeverBought();
                times[i] = Crono.stop();
            }
            benchmark.add(Arrays.stream(times).average().orElse(0));

            //--------Query 2-------//

            for (int i = 0; i < 5; i++) {
                Crono.start();
                sgv.getTotalSalesAndClients(4);
                times[i] = Crono.stop();
            }
            benchmark.add(Arrays.stream(times).average().orElse(0));

            //-------Query 3--------//

            for (int i = 0; i < 5; i++) {
                Crono.start();
                sgv.getClientSalesInfo("F2916");
                times[i] = Crono.stop();
            }
            benchmark.add(Arrays.stream(times).average().orElse(0));

            //-------Query 4--------//

            for (int i = 0; i < 5; i++) {
                Crono.start();
                sgv.getProductSalesInfo("AF1184");
                times[i] = Crono.stop();
            }
            benchmark.add(Arrays.stream(times).average().orElse(0));

            //-------Query 5--------//

            for (int i = 0; i < 5; i++) {
                Crono.start();
                sgv.mostBoughtProducts("Z5000");
                times[i] = Crono.stop();
            }
            benchmark.add(Arrays.stream(times).average().orElse(0));

            //-------Query 6--------//

            for (int i = 0; i < 5; i++) {
                Crono.start();
                sgv.mostSoldProducts(200);
                times[i] = Crono.stop();
            }
            benchmark.add(Arrays.stream(times).average().orElse(0));

            //-------Query 7 -------//

            for (int i = 0; i < 5; i++) {
                Crono.start();
                sgv.threeBiggestBuyers();
                times[i] = Crono.stop();
            }
            benchmark.add(Arrays.stream(times).average().orElse(0));

            //-------Query 8 -------//

            for (int i = 0; i < 5; i++) {
                Crono.start();
                sgv.clientsWhoBoughtMostProducts(50);
                times[i] = Crono.stop();
            }
            benchmark.add(Arrays.stream(times).average().orElse(0));


            //-------Query 9--------//

            for (int i = 0; i < 5; i++) {
                Crono.start();
                sgv.clientsWhoBoughtProductMost("AF1184", 10);
                times[i] = Crono.stop();
            }
            benchmark.add(Arrays.stream(times).average().orElse(0));

            //-------Query 10--------//

            for (int i = 0; i < 5; i++) {
                Crono.start();
                sgv.totalProfit();
                times[i] = Crono.stop();
            }
            benchmark.add(Arrays.stream(times).average().orElse(0));

            System.out.println("Ficheiro: " + fileName + "\n");
            for(int i = 0; i < 10; i++) {
                System.out.println("Query " + (i + 1) + ": " + benchmark.get(i) + " segundos");
            }
            System.out.println("\n\n");
        }
    }
}