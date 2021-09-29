
/**
 * Escreva a descrição da classe Pergunta1 aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
import java.lang.System;
public class Pergunta1
{
    // (a)
    public int minimo (int[] a) {
        int i;
        int min = a[0];
        for (i=1; i<a.length; i++) {
            if (a[i]<min) min = a[i];
        }
        return min;
    }
    // (b)
    public int[] indices (int[] a, int m,int n) {
        int l = n-m+1;
        int[] p = new int[l];
        System.arraycopy(a, m, p , 0, l);
        return p;
    }
    public int[] comuns (int[] a, int[] b) {
        int n; int usados = 0;
        if (a.length<b.length) n = a.length;
        else n = b.length;
        int[] c = new int[n];
        for (int i = 0; i<n; i++) {
            if (a.length>b.length)
            for (int j = 0; j< a.length; j++) {
                if (b[i] == a[j]) c[usados] = b[i];
            }
            else
            for (int j = 0; j< a.length; j++) {
                if (b[i] == a[j]) c[usados] = b[i];
            }
        
        }
        return c;
    }
    
}
