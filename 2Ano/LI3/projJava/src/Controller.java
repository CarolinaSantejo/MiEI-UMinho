import java.io.IOException;
import java.util.Scanner;

public class Controller {
    public static void run() {
        GestVendas sgv = null;
        String path;
        Scanner inputScanner = new Scanner(System.in);
        while(true) {
            UI.mainMenuUI(sgv != null);
            int option = Integer.parseInt(inputScanner.nextLine());
            switch(option) {
                case 1:
                    sgv = new GestVendas();
                    String clientsFilePath = "db/Clientes.txt";
                    String productsFilePath = "db/Produtos.txt";
                    String salesFilePath;
                    option = 0;
                    while(option < 1 || option > 4) {
                        UI.loadMenuUI();
                        option = Integer.parseInt(inputScanner.nextLine());
                    }
                    if(option == 1) salesFilePath = "db/Vendas_1M.txt";
                    else if(option == 2) salesFilePath = "db/Vendas_3M.txt";
                    else if(option == 3) salesFilePath = "db/Vendas_5M.txt";
                    else {
                        UI.customPathsUI();
                        clientsFilePath = inputScanner.nextLine();
                        productsFilePath = inputScanner.nextLine();
                        salesFilePath = inputScanner.nextLine();
                    }
                    Crono.start();
                    sgv.loadSGVFromFiles(clientsFilePath, productsFilePath, salesFilePath);
                    Crono.stop();
                    System.out.println(Crono.getTimeString());


                    break;
                case 2:
                    if(sgv != null) {
                        UI.menu1UI();
                        option = Integer.parseInt(inputScanner.nextLine());
                        switch (option) {
                            case 1:
                                UI.salesInfoUI(sgv.getFileInfo(), sgv.getProductsNeverBought().size());
                                inputScanner.nextLine();
                                break;
                            case 2:
                                UI.structuresInfoUI(sgv.getStructuresInfo());
                                inputScanner.nextLine();
                                break;
                        }
                    }
                    break;
                case 3:
                    if(sgv != null) {
                        UI.menu2UI();
                        option = Integer.parseInt(inputScanner.nextLine());
                        switch (option) {
                            case 1:
                                Queries.query1(sgv);
                                break;
                            case 2:
                                Queries.query2(sgv);
                                break;
                            case 3:
                                Queries.query3(sgv);
                                break;
                            case 4:
                                Queries.query4(sgv);
                                break;
                            case 5:
                                Queries.query5(sgv);
                                break;
                            case 6:
                                Queries.query6(sgv);
                                break;
                            case 7:
                                Queries.query7(sgv);
                                break;
                            case 8:
                                Queries.query8(sgv);
                                break;
                            case 9:
                                Queries.query9(sgv);
                                break;
                            case 10:
                                Queries.query10(sgv);
                                break;
                        }
                    }
                    break;
                case 4:
                    UI.loadObjUI();
                    path = inputScanner.nextLine();
                    if(path.length() < 2) path = "gestVendas.dat";
                    try {
                        sgv = GestVendas.load("path");
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    UI.saveObjUI();
                    path = inputScanner.nextLine();
                    if(path.length() < 2) path = "gestVendas.dat";
                    try {
                        GestVendas.save(sgv,"gestVendas.dat");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                case 0:
                    System.exit(0);
                    break;
            }
        }
    }
}
