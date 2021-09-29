public class UI {
    public static void mainMenuUI(boolean filesLoaded) {
        StringBuilder sb = new StringBuilder(Formatting.CYAN.getCode() +
                "-----------------------------------\n\tSistema de Gestão de Vendas\n-----------------------------------" +
                Formatting.RESET.getCode());
        sb.append("\n1) Carregar ficheiros.\n");
        sb.append(filesLoaded ? "" : Formatting.RED.getCode()).append("2) Consultas estatísticas.\n");
        sb.append("3) Consultas interativas (queries).\n");
        sb.append(Formatting.RESET.getCode());
        sb.append("4) Carregar objeto a partir de ObjectStream.\n");
        sb.append("5) Gravar objeto em ObjectStream.\n");
        sb.append("\n0) Sair.\n");
        sb.append("\nEscreva o número correspondente à opção pretendida.\n> ");
        System.out.print(sb.toString());
    }

    public static void loadMenuUI() {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***CARREGAMENTO DE FICHEIROS***\n\n" + Formatting.RESET.getCode());
        sb.append("1) Carregar ficheiros predefinidos (ficheiros na pasta db, 1 milhão de vendas).\n");
        sb.append("2) Carregar ficheiros predefinidos (ficheiros na pasta db, 3 milhões de vendas).\n");
        sb.append("3) Carregar ficheiros predefinidos (ficheiros na pasta db, 5 milhões de vendas).\n");
        sb.append("4) Usar ficheiros personalizados.\n");
        sb.append("\nEscreva o número correspondente à opção pretendida.\n> ");
        System.out.print(sb.toString());
    }

    public static void menu1UI() {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***CONSULTAS ESTATÍSTICAS***\n\n" + Formatting.RESET.getCode());
        sb.append("1) Dados referentes às vendas.\n");
        sb.append("2) Dados referentes às estruturas.\n");
        sb.append("\nEscreva o número correspondente à opção pretendida.\n> ");
        System.out.print(sb.toString());
    }

    public static void menu2UI() {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***CONSULTAS INTERATIVAS***\n\n" + Formatting.RESET.getCode());
        sb.append("1) Lista de produtos nunca comprados.\n");
        sb.append("2) Total de vendas num mês.\n");
        sb.append("3) Compras e despesa de um cliente.\n");
        sb.append("4) Vendas e lucro de um produto.\n");
        sb.append("5) Produtos mais comprados por um cliente.\n");
        sb.append("6) Produtos mais vendidos.\n");
        sb.append("7) Três maiores compradores por filial.\n");
        sb.append("8) Clientes que compraram mais produtos diferentes.\n");
        sb.append("9) Clientes que mais compraram um dado produto.\n");
        sb.append("10) Faturação total por mês e filial.\n");
        sb.append("\nEscreva o número correspondente à opção pretendida.\n> ");
        System.out.print(sb.toString());
    }

    public static void salesInfoUI(FileInfo fileInfo, int neverBoughtProducts) {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***DADOS DAS VENDAS***\n\n" + Formatting.RESET.getCode());
        sb.append("Nome do ficheiro: ").append(fileInfo.getSalesPath()).append("\n");
        sb.append("Registos de venda errados: ").append(fileInfo.getTotalSales() - fileInfo.getValidSales()).append("\n");
        sb.append("Número total de produtos: ").append(fileInfo.getValidProducts()).append("\n");
        sb.append("Produtos diferentes comprados: ").append(fileInfo.getValidProducts() - neverBoughtProducts).append("\n");
        sb.append("Produtos não comprados: ").append(neverBoughtProducts).append("\n");
        sb.append("Número total de clientes: ").append(fileInfo.getValidClients()).append("\n");
        sb.append("Clientes que realizaram compras: ").append(fileInfo.getClientsWhoBought()).append("\n");
        sb.append("Clientes que não compraram: ").append(fileInfo.getValidClients() - fileInfo.getClientsWhoBought()).append("\n");
        sb.append("Compras de valor igual a 0.0: ").append(fileInfo.getFreeSales()).append("\n");
        sb.append("Faturação total: ").append(fileInfo.getTotalProfit()).append("\n");
        sb.append("\nPrima ENTER para continuar.");
        System.out.println(sb.toString());
    }

    public static void structuresInfoUI(StructuresInfo structuresInfo) {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***DADOS DAS ESTRUTURAS***\n\n" + Formatting.RESET.getCode());
        sb.append("Total de compras\n");
        for(int month = 1; month <= 12; month++) {
            sb.append(String.format("Mês %02d: %d compras\n", month, structuresInfo.getSales(month)));
        }
        sb.append("\nFaturação total por filial\n");
        for(int month = 1; month <= 12; month++) {
            sb.append(String.format("Mês %02d: %10f %10f %10f\n", month, structuresInfo.getProfit(month, 1), structuresInfo.getProfit(month, 2), structuresInfo.getProfit(month, 3)));
        }
        sb.append("Faturação global: ").append(structuresInfo.getProfit());
        sb.append("\n\nCompradores distintos totais\n");
        for(int month = 1; month <= 12; month++) {
            sb.append(String.format("Mês %02d: %d %d %d\n", month, structuresInfo.getBuyers(month, 1), structuresInfo.getBuyers(month, 2), structuresInfo.getBuyers(month, 3)));
        }
        sb.append("\nPrima ENTER para continuar.");
        System.out.println(sb.toString());
    }

    public static void customPathsUI() {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***CARREGAMENTO DE FICHEIROS***\n\n" + Formatting.RESET.getCode());
        sb.append("Introduza os caminhos relativos para os ficheiros dos clientes, produtos e vendas, por esta ordem, um por linha:");
        System.out.println(sb.toString());
    }

    public static void loadObjUI() {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***CARREGAR OBJETO***\n\n" + Formatting.RESET.getCode());
        sb.append("Introduza o caminho relativo para o ficheiro, ou não escreva nada para usar o caminho predefinido: ");
        System.out.println(sb.toString());
    }

    public static void saveObjUI() {
        StringBuilder sb = new StringBuilder(Formatting.YELLOW.getCode() + "\t***GRAVAR OBJETO***\n\n" + Formatting.RESET.getCode());
        sb.append("Introduza o caminho relativo para o ficheiro, ou não escreva nada para usar o caminho predefinido: ");
        System.out.println(sb.toString());
    }
}
