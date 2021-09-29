import java.util.*;

public class QueriesUI {
    public static void query1ResultUI(List<String> list, int pageNum, int pageSize) {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***PRODUTOS NUNCA COMPRADOS***\n\n" + Formatting.RESET.getCode());
        sb.append("Total: ").append(list.size()).append("\n\n");
        int half = (pageSize / 2);
        for(int i = 0; i < pageSize / 2; i++) {
            int index = (pageNum - 1) * pageSize + i;
            if(index < list.size()) sb.append(index + 1).append(". ").append(list.get(index));
            if(index + half < list.size()) sb.append("\t").append(index + half + 1).append(". ").append(list.get(index + half));
            sb.append("\n");
        }
        sb.append(String.format("\nPágina %d de %d.\n", pageNum, (list.size() - 1) / pageSize + 1));
        sb.append("\n1) Página seguinte.\n2) Página anterior.\n\n0) Retroceder.\n");
        sb.append("\nEscreva o número correspondente à opção pretendida.\n> ");
        System.out.print(sb.toString());
    }

    public static void query2UI() {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***TOTAL DE VENDAS E CLIENTES NUM MÊS***\n\n" + Formatting.RESET.getCode());
        sb.append("Introduza um número correspondente ao mês pretendido (1-12): ");
        System.out.print(sb.toString());
    }

    public static void query2ResultUI(Map<Integer, AbstractMap.SimpleEntry<Integer, Integer>> result) {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***TOTAL DE VENDAS E CLIENTES NUM MÊS***\n\n" + Formatting.RESET.getCode());
        sb.append("         | Total Vendas | Total Clientes |\n");
        sb.append(String.format("Total    |       %6d |         %6d |\n",result.get(0).getKey(),result.get(0).getValue()));
        sb.append(String.format("Filial 1 |       %6d |         %6d |\n",result.get(1).getKey(),result.get(1).getValue()));
        sb.append(String.format("Filial 2 |       %6d |         %6d |\n",result.get(2).getKey(),result.get(2).getValue()));
        sb.append(String.format("Filial 3 |       %6d |         %6d |\n",result.get(3).getKey(),result.get(3).getValue()));
        sb.append("\nPressione ENTER para continuar.");
        System.out.println(sb.toString());
    }

    public static void query3UI() {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***COMPRAS E DESPESAS TOTAIS DE UM CLIENTE***\n\n" + Formatting.RESET.getCode());
        sb.append("Introduza um código de cliente válido: ");
        System.out.print(sb.toString());
    }

    public static void query3ResultUI(HashMap<Integer, List<Double>> result) {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***COMPRAS E DESPESAS TOTAIS DE UM CLIENTE***\n\n" + Formatting.RESET.getCode());
        sb.append("     | Compras |  Produtos |             |\n");
        sb.append(" Mês |  Totais | Comprados | Total Gasto |\n");
        sb.append("------------------------------------------\n");
        for(int month = 1; month <= 12; month++) {
            sb.append(String.format("  %2d | %7d | %9d | %11.2f |\n", month, result.get(month).get(0).intValue(), result.get(month).get(1).intValue(), result.get(month).get(2)));
        }
        sb.append("\nPrima ENTER para continuar.");
        System.out.println(sb.toString());
    }

    public static void query4UI() {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***VENDAS E LUCRO TOTAL DE UM PRODUTO***\n\n" + Formatting.RESET.getCode());
        sb.append("Introduza um código de produto válido: ");
        System.out.print(sb.toString());
    }

    public static void query4ResultUI(HashMap<Integer, List<Double>> result) {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***VENDAS E LUCRO TOTAL DE UM PRODUTO***\n\n" + Formatting.RESET.getCode());
        sb.append("     | Vendas | Clientes que |                |\n");
        sb.append(" Mês | Totais |    Compraram | Total Faturado |\n");
        sb.append("------------------------------------------\n");
        for(int month = 1; month <= 12; month++) {
            sb.append(String.format("  %2d | %6d | %12d | %14.2f |\n", month, result.get(month).get(0).intValue(), result.get(month).get(1).intValue(), result.get(month).get(2)));
        }
        sb.append("\nPrima ENTER para continuar.");
        System.out.println(sb.toString());
    }

    public static void query5UI() {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***COMPRAS E DESPESAS TOTAIS DE UM CLIENTE***\n\n" + Formatting.RESET.getCode());
        sb.append("Introduza um código de cliente válido: ");
        System.out.print(sb.toString());
    }

    public static void query5ResultUI(List<Map.Entry<String, Integer>> list, String clientID, int pageNum, int pageSize) {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***PRODUTOS MAIS COMPRADOS POR UM CLIENTE***\n\n" + Formatting.RESET.getCode());
        sb.append("Cliente: ").append(clientID).append("\n\n");
        int half = pageSize / 2;
        for(int i = 0; i < half; i++) {
            int index = (pageNum - 1) * pageSize + i;
            if(index < list.size()) sb.append(String.format("%3d", index + 1)).append(". ").append(list.get(index).getKey())
                    .append(" - ").append(list.get(index).getValue());
            if(index + half < list.size()) sb.append("\t").append(String.format("%3d", index + half + 1)).append(". ").append(list.get(index + half).getKey())
                    .append(" - ").append(list.get(index + half).getValue());
            sb.append("\n");
        }
        sb.append(String.format("\nPágina %d de %d.\n", pageNum, (list.size() - 1) / pageSize + 1));
        sb.append("\n1) Página seguinte.\n2) Página anterior.\n\n0) Retroceder.\n");
        sb.append("\nEscreva o número correspondente à opção pretendida.\n> ");
        System.out.print(sb.toString());
    }

    public static void query6UI() {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***PRODUTOS MAIS VENDIDOS***\n\n" + Formatting.RESET.getCode());
        sb.append("Quantos produtos pretende encontrar? ");
        System.out.print(sb.toString());
    }

    public static void query6ResultUI(List<Map.Entry<String,AbstractMap.SimpleEntry<Integer, Integer>>> list, int limit, int pageNum, int pageSize) {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***PRODUTOS MAIS VENDIDOS***\n\n" + Formatting.RESET.getCode());
        for(int i = 0; i < pageSize; i++) {
            int index = (pageNum - 1) * pageSize + i;
            if(index < list.size() && index < limit) sb.append(String.format("%3d", index + 1)).append(". ").append(list.get(index).getKey())
                    .append(" - ").append(list.get(index).getValue().getKey()).append(" unidades vendidas - ").append(list.get(index).getValue().getValue()).append(" compradores");
            sb.append("\n");
        }
        sb.append(String.format("\nPágina %d de %d.\n", pageNum, (list.size() - 1) / pageSize + 1));
        sb.append("\n1) Página seguinte.\n2) Página anterior.\n\n0) Retroceder.\n");
        sb.append("\nEscreva o número correspondente à opção pretendida.\n> ");
        System.out.print(sb.toString());
    }

    public static void query7ResultUI(Map<Integer, List<Map.Entry<String, Double>>> map) {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***MAIORES COMPRADORES***\n\n" + Formatting.RESET.getCode());
        for(int branch = 1; branch <= 3; branch++) {
            sb.append(String.format("Filial %d: ", branch));
            for(int i = 0; i < 3 ; i++)
                sb.append(String.format("%d. %s - gastou %.2f    ", i + 1, map.get(branch).get(i).getKey(),map.get(branch).get(i).getValue()));
            sb.append("\n");
        }
        sb.append("\nPrima ENTER para continuar.");
        System.out.println(sb.toString());
    }

    public static void query8UI() {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***CLIENTES QUE COMPRARAM MAIS PRODUTOS***\n\n" + Formatting.RESET.getCode());
        sb.append("Quantos clientes pretende encontrar? ");
        System.out.print(sb.toString());
    }

    public static void query8ResultUI(List<Map.Entry<String, Integer>> list, int limit, int pageNum, int pageSize) {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***CLIENTES QUE COMPRARAM MAIS PRODUTOS***\n\n" + Formatting.RESET.getCode());
        for(int i = 0; i < pageSize; i++) {
            int index = (pageNum - 1) * pageSize + i;
            if(index < list.size() && index < limit) sb.append(String.format("%5d", index + 1)).append(". ").append(list.get(index).getKey())
                    .append(" - ").append(list.get(index).getValue()).append(" produtos comprados");
            sb.append("\n");
        }
        sb.append(String.format("\nPágina %d de %d.\n", pageNum, (list.size() - 1) / pageSize + 1));
        sb.append("\n1) Página seguinte.\n2) Página anterior.\n\n0) Retroceder.\n");
        sb.append("\nEscreva o número correspondente à opção pretendida.\n> ");
        System.out.print(sb.toString());
    }

    public static void query9UI(char mode) {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***CLIENTES QUE MAIS COMPRARAM UM PRODUTO***\n\n" + Formatting.RESET.getCode());
        if(mode == 'l') sb.append("Quantos clientes pretende encontrar? ");
        else sb.append("Introduza um código de produto válido: ");
        System.out.print(sb.toString());
    }

    public static void query9ResultUI(List<Map.Entry<String, Map.Entry<Integer, Double>>> list, String prodID, int limit, int pageNum, int pageSize) {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***CLIENTES QUE MAIS COMPRARAM UM PRODUTO***\n\n" + Formatting.RESET.getCode());
        sb.append("Produto ").append(prodID).append("\n\n");
        for(int i = 0; i < pageSize; i++) {
            int index = (pageNum - 1) * pageSize + i;
            if(index < list.size() && index < limit) sb.append(String.format("%5d", index + 1)).append(". ").append(list.get(index).getKey())
                    .append(" - ").append(list.get(index).getValue().getKey()).append(String.format(" unidades compradas - %.2f gasto", list.get(index).getValue().getValue()));
            sb.append("\n");
        }
        sb.append(String.format("\nPágina %d de %d.\n", pageNum, (list.size() - 1) / pageSize + 1));
        sb.append("\n1) Página seguinte.\n2) Página anterior.\n\n0) Retroceder.\n");
        sb.append("\nEscreva o número correspondente à opção pretendida.\n> ");
        System.out.print(sb.toString());
    }

    public static void query10ResultUI(List<Map.Entry<String, List<List<Double>>>> list, int pageNum) {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***LUCRO TOTAL POR MÊS E FILIAL***\n\n" + Formatting.RESET.getCode());
        sb.append("Produto ").append(list.get(pageNum - 1).getKey()).append("\n\n");
        sb.append(" Mês |  Filial 1 |  Filial 2 |  Filial 3\n");
        for(int month = 1; month <= 12; month++) {
            List<Double> monthList = list.get(pageNum - 1).getValue().get(month - 1);
            sb.append(String.format("  %2d | %9.2f | %9.2f | %9.2f\n", month, monthList.get(0), monthList.get(1), monthList.get(2)));
        }
        sb.append(String.format("\nPágina %d de %d.\n", pageNum, list.size()));
        sb.append("\n1) Página seguinte.\n2) Página anterior.\n\n0) Retroceder.\n");
        sb.append("\nEscreva o número correspondente à opção pretendida.\n> ");
        System.out.print(sb.toString());
    }
}
