import java.util.*;
/**
 * Escreva a descrição da classe CasaInteligente aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class CasaInteligente
{
    private String nomeCasa;
    private List<Lampada> lamps;
    public CasaInteligente () {
       
    }
    public CasaInteligente (CasaInteligente c1) {
       
    }
    public List<Lampada> getLamps() {
        ArrayList <Lampada> res = new ArrayList<>();
        for (Lampada l: this.lamps)
            res.add(l.clone());  /* com it. internos -> res = this.lamps.stream()
                                       map(Lampada::clone).collect(Collectors.toList()); */
        return res;
    }

    public void addLampada (Lampada l) {
        this.lamps.add(l.clone());
    }
    
    public void ligaLampadaNormal(int index) {
        if (index < this.lamps.size() -1)
            this.lamps.get(index).lampOn();
    }
    
    public int qtEmEco () {
        int qt = 0;
        for (Lampada l: this.lamps)
            if (l.isInEco()) qt++;
        return qt;
    }
    // return this.lamp.stream().filter(l -> l.isInEco()).count();
    /**
     * Determina a lâmpada da casa que está com maior consumo.
     * 
     * Estratégia: criar um TreeSet em que as lampadas ficam ordenadas por ordem decrescente 
     * de consumo.
     */
    public Lampada lampMaisGastadora () {
        TreeSet <Lampada> res = new TreeSet<>();
        for (Lampada l: this.lamps)
            res.add(l);
        return (res.first()).clone();
    }
    
}
