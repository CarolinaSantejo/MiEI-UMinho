
/**
 * Escreva a descrição da classe Testa aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
import java.util.Scanner;
public class Testa
{
   public static void main () {
       int elem, n,i, min, x,y;
       
       Scanner ler = new Scanner(System.in);
       Pergunta1 p1 = new Pergunta1();
       System.out.println("Introduz o número de valores:");
       n = ler.nextInt();
       int[] a = new int[n];
       for ( i=0; i<n; i++) {
           System.out.printf("Introduz o valor %d: ", i);
           elem = ler.nextInt();
           a[i] = elem;
       }
       min = p1.minimo(a);
       System.out.printf("O mínimo é %d\n", min);
       System.out.println("Array a:");
       for (int m : a) System.out.println (m);
       System.out.printf("Introduz dois indices entre 0 e %d: \n", n-1);
       x = ler.nextInt();
       y = ler.nextInt();
       int[] p = new int[y-x+1];
       p = p1.indices(a, x ,y);
       System.out.println("Array b:");
       for (int m : p) System.out.println (m);
       
       
       
    }
}
