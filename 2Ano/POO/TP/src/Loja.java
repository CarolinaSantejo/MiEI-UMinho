import java.util.List;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.stream.Collectors;
import java.io.*;
public class Loja extends Conta implements Serializable, Randoms {

    private Queue<Encomenda> filaEspera;
    private double tempoEsperaIndividual;
    private List<Encomenda> encProntas;

    public Loja(String cod, String nome, double x, double y) {
        super(cod, nome, x, y);
        this.filaEspera = new ArrayDeque<>();
        this.tempoEsperaIndividual=1;
        this.encProntas= new ArrayList<>();
    }
    
    public Loja(String cod, String nome, double x, double y, String novoEmail, String novaPassword) {
        super(cod, nome, x, y, novoEmail, novaPassword);
        this.filaEspera = new ArrayDeque<>();
        this.tempoEsperaIndividual=1; 
        this.encProntas = new ArrayList<>();
    }
    
    public Loja(String cod, String nome, double x, double y, String novoEmail, String novaPassword,double te) {
        super(cod, nome, x, y, novoEmail, novaPassword);
        this.filaEspera = new ArrayDeque<>();
        this.tempoEsperaIndividual=te;
        this.encProntas = new ArrayList<>();
    }
    

    public Loja(String cod, String nome, double x, double y, String novoEmail, String novaPassword, List<Encomenda> l, double te) {
        super(cod, nome, x, y, novoEmail, novaPassword);
        this.setFilaEspera(l);
        this.tempoEsperaIndividual=te;
        this.setEncProntas(l);
    }

    public Loja(Loja outro) {
        super(outro);
        this.filaEspera = outro.getFilaEspera();
        this.tempoEsperaIndividual=outro.tempoEsperaIndividual;
        this.encProntas = outro.getEncProntas();
        
    }


    public void addEncomenda(Encomenda e) {
        this.filaEspera.add(e.clone());
    }

    public void despacharEnc(String s) {
        Encomenda remov = null;
        for (Encomenda e : this.filaEspera)
            if (e.getCodEnc().equals(s)) {
                remov = e;
                this.filaEspera.remove(e);
                break;
            }
        this.encProntas.add(remov);


    }
    
    public double getTempoEsperaIndividual(){return this.tempoEsperaIndividual;}
    public void serTempoEsperaIndividual(double d){this.tempoEsperaIndividual=d;}

    public void setFilaEspera(List<Encomenda> l) {
        this.filaEspera = l.stream()
                .map(Encomenda::clone)
                .collect(Collectors.toCollection(ArrayDeque::new));
    }
    
    public void setEncProntas(List<Encomenda> l) {
        this.encProntas = l.stream()
                .map(Encomenda::clone)
                .collect(Collectors.toList());
    }

    public Queue<Encomenda> getFilaEspera() {
        return this.filaEspera.stream()
                .map(Encomenda::clone)
                .collect(Collectors.toCollection(ArrayDeque::new));
    }
    
    public List<Encomenda> getEncProntas() {
        return this.encProntas.stream()
                .map(Encomenda::clone)
                .collect(Collectors.toList());
    }
    
    public boolean checkIfEncPronta(String cod){
        if (this.encProntas.isEmpty()) return false;
        return this.encProntas.stream().anyMatch(a->a.getCodEnc().equals(cod));
    }
    //Quanto tempo ate ser atendida
    public Double tempoEsperaTeorico(String enc) {
        return (quantosNaFrente(enc)*this.tempoEsperaIndividual);
    }
    
    public Double tempoEspera(String enc) {
        int count;
        if (checkIfEncPronta(enc)) return 0.0;
        count = quantosNaFrente(enc);
        return (calculaTempo(count*this.tempoEsperaIndividual));
    }
    
    
    
    public int quantosNaFrente(String cod){
         int count = 1;
        for (Encomenda e: filaEspera) {
            if (e.getCodEnc().equals(cod))
                break;
            count++;
        }
        return count;
    }
    
    
    public void remove(String cod){
        boolean p = false;
        if (!filaEspera.isEmpty() && !encProntas.isEmpty()) {
            for (Encomenda e: this.filaEspera){
                if (e.getCodEnc().equals(cod)) {
                filaEspera.remove(e);
                p=true;
                break;
            }
            }
            if (!p){
            for (Encomenda e: this.encProntas){
                if (e.getCodEnc().equals(cod)) {
                encProntas.remove(e);
                break;
                    }
                }
            }
                
                
        }
        }
    
    
    
    
    
    
    //CLONE
    public Loja clone() {
        return new Loja(this);
    }

    //ToString
    public String toString() {
        StringBuilder sb = new StringBuilder("Loja\n");
        sb.append(super.toString());
        sb.append("Fila de espera: ").append(this.filaEspera.toString()).append("\n");
        sb.append("Fila de Despacho: ").append(this.encProntas.toString()).append("\n");
        return sb.toString();
        
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Loja loja = (Loja) o;
        return this.filaEspera.size() == loja.filaEspera.size() &&
                this.filaEspera.containsAll(loja.filaEspera) &&
                loja.filaEspera.containsAll(this.filaEspera) &&
                this.encProntas.containsAll(loja.encProntas) &&
                loja.encProntas.containsAll(this.encProntas) ;
                
    }
}
