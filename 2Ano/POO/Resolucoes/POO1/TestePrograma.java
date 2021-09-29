
/**
 * Escreva a descrição da classe TestePrograma aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
import java.util.Scanner;
public class TestePrograma
{
    public static void main(String[] args) {
        //inicialização de um scanner para leitura
        double cl, res;
        Scanner ler = new Scanner(System.in);
        System.out.println("Introduz um valor:");
        Ficha1 f1 = new Ficha1();
        cl = ler.nextDouble();
        res = f1.celsiusParaFarenheit(cl);
        System.out.println (res);
}
}