import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Escreva a descrição da classe Teste aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Teste
{
    public static void main () {
        LinhaEncomenda linhaE1 = new LinhaEncomenda("1234","Banana",2,5,0,0.5);
        LinhaEncomenda linhaE2 = new LinhaEncomenda("1235","Pera",1,8,0,0.1);
        LinhaEncomenda linhaE3 = new LinhaEncomenda("1236","Maçã",1,10,0,0);
        LocalDate data = LocalDate.now();
        Encomenda enc = new Encomenda("Joao",1234567,"Rua Eu",1,data);
        enc.adicionaLinha(linhaE1);
        enc.adicionaLinha(linhaE2);
        enc.adicionaLinha(linhaE3);
        enc.removeProduto("1234");
        System.out.println(enc.toString());
        System.out.println("Valor total da encomenda: " + enc.calculaValorTotal());
        System.out.println("Valor total de descontos: " + enc.calculaValorDesconto());
        System.out.println("Nº total de Produtos: " + enc.numeroTotalProdutos());
        System.out.println("Existe o produto?: " + enc.existeProdutoEncomenda("1235"));
        
        
    }
}
