import java.util.*;
import java.time.LocalDate;
/**
 * Classe constituida por uma tabela de hash associada a cada String nomeEmpresa 
 * 
 * @author Carolina Santejo 
 * @version 01042020
 */
public class GestaoEncomendas
{
    private String nomeEmpresa;
    private Map<Integer, Encomenda> encomendas;
    
    public GestaoEncomendas () {
        this.nomeEmpresa = "";
        this.encomendas = new HashMap<>();
    }
    
    public GestaoEncomendas (String nome) {
        this.nomeEmpresa = nome;
        this.encomendas = new HashMap<>();
    }
    
    public GestaoEncomendas (String nome, Map<Integer, Encomenda> encs){
        this.nomeEmpresa = nome;
        setEncomendas(encs);
    }
    
    public void setNome (String nome){
        this.nomeEmpresa = nome;
    }
   
    public void setEncomendas (Map<Integer, Encomenda> encs){
        this.encomendas = new HashMap<>();
        for(Encomenda e:encs.values())
            this.encomendas.put(e.getNEnc(), e.clone());
    }
    
    public String getNome() {
        return this.nomeEmpresa;
    }
    
    public Map<Integer, Encomenda> getEncs() {
        Map<Integer, Encomenda> encs = new HashMap<>();
        for(Encomenda e:this.encomendas.values()) //values() devolve uma Collection<Encomenda>
            encs.put(e.getNEnc(), e.clone());     
       return encs;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nome da Empresa: ").append(this.nomeEmpresa).append("\n\n");
        for(Encomenda e : this.encomendas.values()) {
            sb.append("----Encomenda ").append(e.getNEnc()).append("----\n").append(e.toString()).append("\n"); 
        }
        return sb.toString();
    }
    
    // (b) 
    //  i.
    
    public Set<Integer> todosCodigosEnc() {
        Set<Integer> encCod = new HashSet<>();
        encCod = this.encomendas.keySet();
        return encCod;
    }
    
    // ii.
    
    public void addEncomenda(Encomenda enc) {
        this.encomendas.put(enc.getNEnc(), enc);
    }
    
    // iii.
    
    public Encomenda getEncomenda(int codEnc) {
        Encomenda e = this.encomendas.get(codEnc).clone();
        return e;
    }
    
    // iv.
    
    public void removeEncomenda(int codEnc) {
        this.encomendas.remove(codEnc);
    }
    
    // v.
    
    public int encomendaComMaisProdutos() {
        int i;
        int ola = 0;
        int max = 0;
        for(Encomenda e:this.encomendas.values()){
            i = e.numeroTotalProdutos();
            if(i>max) {
                ola = e.getNEnc();
                max = i;
            }
        }
        return ola;
    }
    
    // vi.
    
    public Set<Integer> encomendasComProduto(String codProd) {
        Set<Integer> encCod = new HashSet<>();
        for(Encomenda e:this.encomendas.values()){
            for (LinhaEncomenda l: e.getEncomendas()) {
                if (l.getR().equals(codProd)) encCod.add(e.getNEnc());
            }
        }
        return encCod;
    }
    
    // vii.
    
    public Set<Integer> encomendasAposData(LocalDate d){
        Set<Integer> encCod = new HashSet<>();
        for(Encomenda e:this.encomendas.values()){
            if(e.getData().compareTo(d)>0) encCod.add(e.getNEnc());
        }
        return encCod;
    }
    
    // viii.
    
    public Set<Integer> encomendasValorDecrescente(){
        Set<Integer> encCod = new TreeSet<Integer>(new CompEncDecrescente());
        for(Encomenda e:this.encomendas.values()){
            encCod.add(e.getNEnc());
        }
        return encCod;
    }
    
    // ix.
    
    public Map<String,List<Integer>> encomendasDeProduto() {
        Map<String, List<Integer>> encProd = new HashMap<String, List<Integer>>();
        String key;
        Integer value;
        for(Encomenda e:this.encomendas.values()){
            for (LinhaEncomenda l: e.getEncomendas()) {
                List<Integer> list = new ArrayList<>();
                key = l.getR();
                value = e.getNEnc();
                if (encProd.containsKey(key)) {
                    list = encProd.get(key);
                    if(!list.contains(value)) list.add(value);                  
                    encProd.put(key, list);
                }
                else {
                    list.add(value);
                    encProd.put(key, list);
                }
            }
        }
        
        return encProd;
    }
}
