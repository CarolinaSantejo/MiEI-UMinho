import java.util.*;
import java.util.AbstractMap.SimpleEntry;
/**
 * Escreva a descrição da classe Grafo aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Grafo
{
    private Map<Integer, Set<Integer>> adj;
    
    /**
     * Contrutor vazio
     */
    public Grafo() {
        this.adj = new HashMap<>();
    }
    
    public Grafo (Map<Integer, Set<Integer>> outraAdj) {
        this.adj = new HashMap<>();
        
        for(Map.Entry<Integer,Set<Integer>> e: outraAdj.entrySet())
            this.adj.put(e.getKey(), new HashSet(e.getValue()));
    }
    
    /**
     * Métodos de instância
     */
    
    // ii)
    public void addArco(Integer orig, Integer dest) {
        this.adj.putIfAbsent(orig, new HashSet<>());
        this.adj.putIfAbsent(dest, new HashSet<>());
        this.adj.get(orig).add(dest);
    }
    
    // iii)
    public boolean isSink(Integer v) {
        // verificar se existe no map. Caso nao exista deve devolver false.
        // Questao: como distinguir o false de nao existir vertice do false de nao ter vertices adjacentes
        return this.adj.containsKey(v) && this.adj.get(v).isEmpty();
    }
    
    // iv)
    public boolean isSource(Integer v) {
        boolean source = this.adj.containsKey(v); // se v nao existe no map
        
        Iterator<Set<Integer>> it = this.adj.values().iterator();
        while (it.hasNext() && source)
            source = !it.next().contains(v);
            
        return source;
    }
    
    // v)
    public int size() {
        // nº de vertices + nº arcos
        int tam = this.adj.size();
        
        // nº de arcos
        tam += this.adj.values().stream().mapToInt(s -> s.size()).sum();
        return tam;
    }
    
    // vi)
    boolean haCaminho (Integer orig, Integer dest) {
        return haCaminho(orig,dest, new HashSet<>());
    }
    
    // metodo auxiliar
    public boolean haCaminho(Integer orig, Integer dest, Set<Integer> visitados) {
        boolean hac;
        if (orig == dest)
            hac = true;
        else {
            if (!this.adj.containsKey(orig) || visitados.contains(orig)) {
                hac = false;
            }
            else {
                Iterator<Integer> i = this.adj.get(orig).iterator();
                hac = false;
                visitados.add(orig);
                while (i.hasNext() && !hac)
                    hac = haCaminho(i.next(), dest, visitados);
            }
        }
        return hac;
    }
    
    // vii)
    // public List<String> getCaminho(String orig, String dest)
    
    //viii)
    public Set<Map.Entry<Integer,Integer>> fanOut(Integer vorig) {
        Set<Map.Entry<Integer,Integer>> res = new HashSet<>();
        for (Integer vdest: this.adj.get(vorig))
            res.add(new SimpleEntry(vorig,vdest)); // SimpleEntry é do tipo Map.Entry(é uma implementacao)
        
            return res;
    }
    
    // ix)
    public Set<Map.Entry<Integer,Integer>> fanIn(Integer vdest) {
        Set<Map.Entry<Integer,Integer>> res = new HashSet<>();
        for (Map.Entry<Integer,Set<Integer>> e: this.adj.entrySet())
            if (e.getValue().contains(vdest)) 
                res.add(new SimpleEntry(e.getKey(), vdest));
        
            return res;
    }
    
}
