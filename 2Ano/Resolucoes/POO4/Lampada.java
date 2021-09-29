
/**
 * Escreva a descrição da classe Lampada aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Lampada
{
    public Lampada() {
        this.modo = 0;
        this.total = 0;
        this.stamp = System.currentTimeMillis();       
  
    }
    
    public Lampada (Lampada l) {
        this.modo = l.getModo();
        this.total = l. getTotal();
        this.stamp = l.getStamp;
    }
    
    public Lampada clone() {
        return new Lampada(this);
    }
    
    public int compareTo(Lampada l) {
        double i = this.periodoConsumo();
        double k = l.periodoConsumo();
        if (i==k)
            return 0;
        if (i<k) 
            return 1;
        else 
            return -1;
    }
}
