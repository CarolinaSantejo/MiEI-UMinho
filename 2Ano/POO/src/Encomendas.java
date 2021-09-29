import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.stream.Collectors;
import java.io.*;

public class Encomendas implements Serializable
{
    private Set<Encomenda> encomendas;

    public Encomendas() {
        this.encomendas = new TreeSet<>();
    }

    public Encomendas(Set<Encomenda> set) {
        this.setEnc(set);
    }

    public Encomendas(Encomendas us) {
        this.encomendas = us.getEnc();
    }

    public Set<Encomenda> getEnc() {
        return this.encomendas.stream()
                .map(Encomenda::clone)
                .collect(Collectors.toSet());
    }

    public void setEnc(Set<Encomenda> set) {
        this.encomendas = set.stream()
                             .map(Encomenda::clone)
                             .collect(Collectors.toSet());
    }
    
    public void addEnc (Encomenda e) {
        this.encomendas.add(e.clone());
    }
    
    public Encomenda getEncomendaByCod(String code) {
        return this.encomendas.stream().reduce(null, (acc, e) -> e.getCodEnc().equals(code) ? e.clone() : acc);
    }
    
    public List<Encomenda> getListaEncomenda(List<String>lista){
        return lista.stream().map(this::getEncomendaByCod).collect(Collectors.toList());
    }
    
    public Encomendas clone() {
        return new Encomendas(this);
    }
    
    public boolean equals (Encomendas e){
        return this.encomendas.equals(e.encomendas);
    }
    
    public void solicitaEnc(String e){
        this.encomendas.stream().filter(a->a.getCodEnc().equals(e)).forEach(a->a.setFoiSolicitada(true));
    }
    
    public void quemTransportou(String e,String t){
        this.encomendas.stream().filter(a->a.getCodEnc().equals(e)).forEach(a->a.setQuemTransportou(t));
    }
    
    public void removeOld(String cod){
        this.encomendas.removeIf(a->a.getCodEnc().equals(cod));
    }

    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("Encomendas: ").append(encomendas.toString()).append('\n');
        return sb.toString();
    }
    
    public int newCodeNumber(){
        List<Integer> l = this.encomendas.stream()
                                         .map(Encomenda::getCodEnc)
                                         .map(a->Integer.valueOf(a.substring(1)))
                                         .sorted()
                                         .collect(Collectors.toList());
        if(l.isEmpty()) return 1;                                 
        Integer i = l.get(0);
        int aux = i+1;
        while (l.contains(aux)) aux++;
        return aux;
     }
    
    
}
