import java.util.*;


public class Queries {
    public static void query1(GestVendas sgv) {
        Crono.start();
        List<String> result = sgv.getProductsNeverBought();
        System.out.println(Crono.getTimeString());
        Scanner scanner = new Scanner(System.in);
        int pageNum = 1;
        int pageSize = 20;
        while(true) {
            QueriesUI.query1ResultUI(result, pageNum, pageSize);
            int option = scanner.nextInt();
            if(option == 1 && pageNum * pageSize <= result.size()) pageNum++;
            else if(option == 2 && pageNum > 1) pageNum--;
            else if(option == 0) break;
        }
    }

    public static void query2(GestVendas sgv) {
        int month = 0;
        Scanner scanner = new Scanner(System.in);
        while(month < 1 || month > 12) {
            QueriesUI.query2UI();
            String input = scanner.nextLine();
            month = Integer.parseInt(input);
        }
        Crono.start();
        Map<Integer, AbstractMap.SimpleEntry<Integer, Integer>> result = sgv.getTotalSalesAndClients(month);
        System.out.println(Crono.getTimeString());
        QueriesUI.query2ResultUI(result);
        scanner.nextLine();
    }

    public static void query3(GestVendas sgv) {
        Scanner scanner = new Scanner(System.in);
        String clientID = null;
        while(!Client.isValidID(clientID)) {
            QueriesUI.query3UI();
            clientID = scanner.nextLine();
        }
        Crono.start();
        HashMap<Integer, List<Double>> result = sgv.getClientSalesInfo(clientID);
        System.out.println(Crono.getTimeString());
        QueriesUI.query3ResultUI(result);
        scanner.nextLine();
    }

    public static void query4(GestVendas sgv) {
        Scanner scanner = new Scanner(System.in);
        String prodID = null;
        while(!Product.isValidID(prodID)) {
            QueriesUI.query4UI();
            prodID = scanner.nextLine();
        }
        Crono.start();
        HashMap<Integer, List<Double>> result = sgv.getProductSalesInfo(prodID);
        System.out.println(Crono.getTimeString());
        QueriesUI.query4ResultUI(result);
        scanner.nextLine();
    }

    public static void query5(GestVendas sgv) {
        Scanner scanner = new Scanner(System.in);
        String clientID = null;
        while(!Client.isValidID(clientID)) {
            QueriesUI.query5UI();
            clientID = scanner.nextLine();
        }
        Crono.start();
        List<Map.Entry<String, Integer>> result = sgv.mostBoughtProducts(clientID);
        System.out.println(Crono.getTimeString());
        int pageNum = 1;
        int pageSize = 20;
        while(true) {
            QueriesUI.query5ResultUI(result, clientID, pageNum, pageSize);
            int option = scanner.nextInt();
            if(option == 1 && pageNum * pageSize < result.size()) pageNum++;
            else if(option == 2 && pageNum > 1) pageNum--;
            else if(option == 0) break;
        }
    }

    public static void query6(GestVendas sgv) {
        Scanner scanner = new Scanner(System.in);
        QueriesUI.query6UI();
        int N = scanner.nextInt();
        Crono.start();
        List<Map.Entry<String,AbstractMap.SimpleEntry<Integer, Integer>>> result = sgv.mostSoldProducts(N);
        System.out.println(Crono.getTimeString());
        int pageNum = 1;
        int pageSize = 10;
        while(true) {
            QueriesUI.query6ResultUI(result, N, pageNum, pageSize);
            int option = scanner.nextInt();
            if(option == 1 && pageNum * pageSize < result.size()) pageNum++;
            else if(option == 2 && pageNum > 1) pageNum--;
            else if(option == 0) break;
        }
    }

    public static void query7(GestVendas sgv) {
        Crono.start();
        Map<Integer, List<Map.Entry<String, Double>>> result = sgv.threeBiggestBuyers();
        System.out.println(Crono.getTimeString());
        
        QueriesUI.query7ResultUI(result);
        Scanner inputScanner = new Scanner(System.in);
        inputScanner.nextLine();
    }

    public static void query8(GestVendas sgv) {
        Scanner scanner = new Scanner(System.in);
        QueriesUI.query8UI();
        int N = scanner.nextInt();
        Crono.start();
        List<Map.Entry<String, Integer>> result = sgv.clientsWhoBoughtMostProducts(N);
        System.out.println(Crono.getTimeString());
        int pageNum = 1;
        int pageSize = 10;
        while(true) {
            QueriesUI.query8ResultUI(result, N, pageNum, pageSize);
            int option = scanner.nextInt();
            if(option == 1) {
                if(pageNum * pageSize < result.size()) pageNum++;
                else pageNum = 1;
            }
            else if(option == 2) {
                if(pageNum > 1) pageNum--;
                else pageNum = ((result.size() - 1) / pageSize) + 1;
            }
            else if(option == 0) break;
        }
    }

    public static void query9(GestVendas sgv) {
        Scanner scanner = new Scanner(System.in);
        QueriesUI.query9UI('p');
        String prodID = scanner.nextLine();
        QueriesUI.query9UI('l');
        int N = scanner.nextInt();
        Crono.start();
        List<Map.Entry<String, Map.Entry<Integer, Double>>> result = sgv.clientsWhoBoughtProductMost(prodID, N);
        System.out.println(Crono.getTimeString());
        int pageNum = 1;
        int pageSize = 10;
        while(true) {
            QueriesUI.query9ResultUI(result, prodID, N, pageNum, pageSize);
            int option = scanner.nextInt();
            if(option == 1) {
                if(pageNum * pageSize < result.size()) pageNum++;
                else pageNum = 1;
            }
            else if(option == 2) {
                if(pageNum > 1) pageNum--;
                else pageNum = ((result.size() - 1) / pageSize) + 1;
            }
            else if(option == 0) break;
        }
    }

    public static void query10(GestVendas sgv) {
        Scanner scanner = new Scanner(System.in);
        Crono.start();
        List<Map.Entry<String, List<List<Double>>>> result = sgv.totalProfit();
        System.out.println(Crono.getTimeString());
        int pageNum = 1;
        while(true) {
            QueriesUI.query10ResultUI(result, pageNum);
            int option = scanner.nextInt();
            if(option == 1) {
                if(pageNum < result.size()) pageNum++;
                else pageNum = 1;
            }
            else if(option == 2) {
                if(pageNum > 1) pageNum--;
                else pageNum = result.size();
            }
            else if(option == 0) break;
        }
    }
}
