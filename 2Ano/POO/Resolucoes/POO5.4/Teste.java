import java.util.*;
import java.time.LocalDate;

/**
 * Escreva a descrição da classe Teste aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Teste {
    public static void main () {
        Scanner ler = new Scanner(System.in);             
        LinhaEncomenda linhaE1 = new LinhaEncomenda("1234","Banana",2,500,0,0.5);
        LinhaEncomenda linhaE2 = new LinhaEncomenda("1235","Pera",1,123,0,0.1);
        LinhaEncomenda linhaE3 = new LinhaEncomenda("1236","Maçã",1,101,0,0);
        LinhaEncomenda linhaE4 = new LinhaEncomenda("1237","Ananás",6.45,20,0.23,0.2);
        LinhaEncomenda linhaE5 = new LinhaEncomenda("1345","Leite",1.45,100,0.23,0);
        LinhaEncomenda linhaE6 = new LinhaEncomenda("2145","Couve",1.25,150,0.23,0.1);
        LocalDate data = LocalDate.now();
        LocalDate data2 = LocalDate.of(2009,11,4);
        LocalDate data3 = LocalDate.of(2029,10,21);
        Encomenda enc = new Encomenda("Joao",1234567,"Rua Eu",12,data);
        Encomenda enc2 = new Encomenda("Maria",7321221,"Rua Tu",5,data2);
        Encomenda enc3 = new Encomenda("Teresa",4654321,"Rua Nos",45,data3);
        // Encomenda 1
        enc.adicionaLinha(linhaE1);
        enc.adicionaLinha(linhaE5);
        enc.adicionaLinha(linhaE3);
        enc.adicionaLinha(linhaE4);
        // Encomenda 2
        enc2.adicionaLinha(linhaE1);
        enc2.adicionaLinha(linhaE6);
        enc2.adicionaLinha(linhaE5);
        enc2.adicionaLinha(linhaE2);
        // Encomenda 3
        enc3.adicionaLinha(linhaE2);
        enc3.adicionaLinha(linhaE3);
        enc3.adicionaLinha(linhaE4);
        enc3.adicionaLinha(linhaE6);
        
        GestaoEncomendas gest = new GestaoEncomendas("Continente");
        gest.addEncomenda(enc);
        gest.addEncomenda(enc2);
        gest.addEncomenda(enc3);
        
        while(true) {
            System.out.println("Selecione uma das opções:");
            System.out.println("1 - Ver encomendas");
            System.out.println("2 - Ver encomenda apartir do seu numero");
            System.out.println("3 - Remover encomenda apartir do seu numero");
            System.out.println("4 - Ver nº de encomenda com mais produtos encomendados");
            System.out.println("5 - Encomendas onde um produto está presente");
            System.out.println("6 - Encomendas após uma data");
            System.out.println("7 - Encomendas por ordem decrescente de código");
            System.out.println("8 - Encomendas por codigo de produto");
            System.out.println("0 - Sair do programa;");
            String refProd;
            int opcao = ler.nextInt();
            int nEnc;
            switch (opcao) {
                case 1:
                    System.out.println(gest.toString());
                    break;
                case 2:
                    System.out.println("Introduza um nº de encomenda: ");
                    nEnc = ler.nextInt();
                    if (gest.getEncs().containsKey(nEnc)) {
                        System.out.println("Encomenda " + nEnc + ":" );
                        System.out.println(gest.getEncomenda(nEnc));
                    }
                    else System.out.println("Não existe nenhuma encomenda com o nº dado\n");
                    break;
                case 3:
                    System.out.println("Introduza um nº de encomenda: ");
                    nEnc = ler.nextInt();
                    if (gest.getEncs().containsKey(nEnc)) {
                        gest.removeEncomenda(nEnc);
                        System.out.println("Encomenda removida com sucesso\n");
                    }
                    else System.out.println("Não existe nenhuma encomenda com o nº dado\n");
                    break;
                case 4:
                    System.out.println("Encomenda com mais produtos encomendados :" + gest.encomendaComMaisProdutos());
                    break;
                case 5:
                    System.out.println("Indique um codigo de produto");
                    ler.nextLine();
                    refProd = ler.nextLine();
                    System.out.println("Encomendas com o produto " + refProd + ":" );
                    System.out.println(gest.encomendasComProduto(refProd));
                    break;
                case 6:
                    System.out.println("Encomendas após " + data + ":");
                    System.out.println(gest.encomendasAposData(data));
                    break;
                case 7:
                    System.out.println(gest.encomendasValorDecrescente());
                    break;
                case 8:
                    System.out.println(gest.encomendasDeProduto());
                    break;
                case 0: 
                    System.exit(0);
            }
        }
        /*
        
        while(true) {
            System.out.println("Selecione uma das opções:");
            System.out.println("1 - Valor total da encomenda;");
            System.out.println("2 - Valor total dos descontos;");
            System.out.println("3 - Nº total de produtos;");
            System.out.println("4 - O produto está na encomenda?;");
            System.out.println("5 - Mostrar encomenda;");
            System.out.println("0 - Sair do programa;");
            String refProd;
             int opcao = ler.nextInt();
            switch (opcao) {
                case 1:
                    System.out.println("Valor total da encomenda: " + enc.calculaValorTotal());
                    break;
                case 2:
                    System.out.println("Valor total de descontos: " + enc.calculaValorDesconto());
                    break;
                case 3:
                    System.out.println("Nº total de Produtos: " + enc.numeroTotalProdutos());
                    break;
                case 4:
                    System.out.println("Indique a referencia do produto:");              
                    ler.nextLine();
                    refProd = ler.nextLine();
                    System.out.print("O produto '" + refProd);
                    if (enc.existeProdutoEncomenda(refProd)) System.out.println("' existe na encomenda");
                    else System.out.println("' não existe na encomenda");
                    break;
                case 5:
                    System.out.println(enc.toString());
                    break;
                case 0: 
                    System.exit(0);      
                      
            }
            System.out.println();
        
        
        }

        
        */   
     
    }
}