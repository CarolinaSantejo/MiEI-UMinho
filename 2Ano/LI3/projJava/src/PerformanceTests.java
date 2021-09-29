import java.io.BufferedReader;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class PerformanceTests {

    public static void main(String[] args) {
        Set<String> clients = null;
        Set<String> products = null;
        try {
            clients = Files.lines(Path.of("db/Clientes.txt")).collect(Collectors.toSet());
            products = Files.lines(Path.of("db/Produtos.txt")).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = null;
        while(fileName == null) {
            System.out.println("\tTESTES DE PERFORMANCE\n");
            System.out.println("1) 1 milhão de vendas.");
            System.out.println("2) 3 milhões de vendas.");
            System.out.println("3) 5 milhões de vendas.");
            System.out.println("Indique o ficheiro de vendas que pretende utilizar: ");
            Scanner scanner = new Scanner(System.in);
            int option = Integer.parseInt(scanner.nextLine());
            if (option == 1) fileName = "db/Vendas_1M.txt";
            else if (option == 2) fileName = "db/Vendas_3M.txt";
            else if (option == 3) fileName = "db/Vendas_5M.txt";
        }
        double readTimes[] = new double[5];
        double readParseTimes[] = new double[5];
        double readParseValidationTimes[] = new double[5];
        for(int i = 0; i < 5; i++) {
            readTimes[i] = openFile(fileName, false, false, null, null);
            readParseTimes[i] = openFile(fileName, true, false, null, null);
            readParseValidationTimes[i] = openFile(fileName, true, true, clients, products);
        }
        System.out.println("Tempo de leitura: " + Arrays.stream(readTimes).sum());
        System.out.println("Tempo de leitura + parse: " + Arrays.stream(readParseTimes).sum());
        System.out.println("Tempo de leitura + parse + validação: " + Arrays.stream(readParseValidationTimes).sum());
    }

    public static double openFile(String fileName, boolean parsing, boolean verification, Set<String> clientIDs, Set<String> productIDs) {
        try {
            Crono.start();
            BufferedReader bf = Files.newBufferedReader(Path.of(fileName));
            String line;
            while((line = bf.readLine()) != null) {
                if(parsing) {
                    Sale sale = new Sale(line);
                    if(verification) Sale.isValid(sale, clientIDs, productIDs);
                }
            }
            return Crono.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
