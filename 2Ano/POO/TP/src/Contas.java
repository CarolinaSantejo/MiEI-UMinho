import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.Map.Entry;
import java.util.List;
import java.io.*;

public class Contas implements Serializable {
    private Map<String,Conta> mapContas;

    public Contas() {
        this.mapContas = new TreeMap<>();
    }

    public Contas(Map<String, Conta> map) {
        this.setContas(map);
    }

    public Contas(Contas us) {
        this.mapContas = us.getContas();
    }

    public Map<String, Conta> getContas() {
        return this.mapContas.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Entry::getValue, (a, b)->a, TreeMap::new));
    }
    
    public void addConta (Conta conta) {
        this.mapContas.put(conta.getCodigo(), conta.clone());
    }
    
    public void setContas(Map<String,Conta> map) {
        this.mapContas = map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e->e.getValue().clone(), (a,b)->a, TreeMap::new));
    }

    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("Contas: ").append(mapContas.toString()).append('\n');
        return sb.toString();
    }
    
    
    public Contas clone(){
        return new Contas(this);
    }

    public Conta getContaByEmail(String email){
        Conta conta = this.mapContas.values().stream()
                .reduce(null, (acc, x) -> x.getEmail().equals(email) ? x : acc);
        if(conta != null) return conta.clone();
        else return null;
    }
    
    public Conta getContaByCode(String code){
        if (this.mapContas.containsKey(code))
            return this.mapContas.get(code).clone();
        return null;
    }
    
    public boolean existeEmail(String s){
        return this.mapContas.entrySet().stream().anyMatch(a->s.equals(a.getValue().getEmail()));
    }
    
       
    public int newCodeNumber(){
        List<Integer> l = this.mapContas.keySet().stream()
                                                 .map(a->Integer.valueOf(a.substring(1)))
                                                 .sorted()
                                                 .collect(Collectors.toList());
        if (l.isEmpty()) return 1;                                         
        Integer i = l.get(0);
        int aux = i+1;
        while (l.contains(aux)) aux++;
        return aux;
     }
    
    
}
