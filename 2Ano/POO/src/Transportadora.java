import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class Transportadora extends Conta implements Serializable, TranspVolunt,Randoms {
    private String nif;
    private double raio;
    private double precoKm;
    private double precoKg;
    private List<Integer> classificacao;
    
    
    //Encomendas que aceitou
    private List<String> encAceites;
    private boolean disponivel;
    //numero máximo de encomendas que transporta de cada vez
    private int maxCapacidade;
    private boolean medicamentos;
    private double kmPercorridos;
    private double velocidade;

    public Transportadora(String cod, String nome, double x, double y, String nif, double raio, double preco) {
        super(cod, nome, x, y);
        this.raio = raio;
        this.nif = nif;
        this.precoKm = preco;
        this.encAceites = new ArrayList<>();
        this.disponivel = true;
        this.maxCapacidade = Integer.MAX_VALUE;
        this.classificacao = new ArrayList<>();
        this.medicamentos = false;
        this.kmPercorridos = 0;
        this.precoKg = 0;
        this.velocidade = calculaVelocidadeVol();
    }

    public Transportadora(String cod, String nome, double x, double y, String nif, double raio, double preco, int max,double precoPeso) {
        super(cod, nome, x, y);
        this.raio = raio;
        this.nif = nif;
        this.precoKm = preco;
        this.precoKg = precoPeso;
        this.encAceites = new ArrayList<>();
        this.disponivel = true;
        this.maxCapacidade = max;
        this.classificacao = new ArrayList<>();
        this.medicamentos = false;
        this.kmPercorridos = 0;
        this.velocidade = calculaVelocidadeVol();
    }

    public Transportadora(String cod, String nome, double x, double y, String nif, double raio, double preco, String novoEmail, String novaPass,double precoPeso) {
        super(cod, nome, x, y, novoEmail, novaPass);
        this.raio = raio;
        this.nif = nif;
        this.precoKm = preco;
        this.precoKg = precoPeso;
        this.encAceites = new ArrayList<>();
        this.disponivel = true;
        this.classificacao = new ArrayList<>();
        this.maxCapacidade = Integer.MAX_VALUE;
        this.velocidade = calculaVelocidadeVol();
        this.medicamentos=false;
        this.kmPercorridos = 0;
    }
    
    
    
    public Transportadora(String cod, String nome, double x, double y, String nif, double raio, double preco, String novoEmail, String novaPass, int maxCapacidade, double vel,boolean med,double precoPeso) {
        super(cod, nome, x, y, novoEmail, novaPass);
        this.raio = raio;
        this.nif = nif;
        this.precoKm = preco;
        this.precoKg = precoPeso;
        this.encAceites = new ArrayList<>();
        this.disponivel = true;
        this.classificacao = new ArrayList<>();
        this.maxCapacidade = maxCapacidade;
        this.velocidade = vel;
        this.medicamentos = med;
        this.kmPercorridos = 0;
    }
    
    public Transportadora(String cod, String nome, double x, double y, String nif, double raio, double preco, String novoEmail, String novaPass, List<String> list, int max,int km,double precoPeso) {
        super(cod, nome, x, y, novoEmail, novaPass);
        this.raio = raio;
        this.nif = nif;
        this.precoKm = preco;
        this.precoKg = precoPeso;
        this.encAceites = new ArrayList<>(list);
        this.disponivel = list.isEmpty();
        this.maxCapacidade = max;
        this.classificacao = new ArrayList<>();
        this.medicamentos = false;
        this.kmPercorridos = 0;
        this.velocidade = calculaVelocidadeVol();
        this.kmPercorridos = km;
    }
    


    public Transportadora(Transportadora t) {
        super(t);
        this.nif = t.nif;
        this.raio = t.raio;
        this.precoKm = t.precoKm;
        this.precoKg = t.precoKg;
        this.encAceites = t.getEncAceites();
        this.disponivel = t.disponivel;
        this.maxCapacidade = t.maxCapacidade;
        this.classificacao = t.getClassif();
        this.medicamentos = t.aceitoTransporteMedicamentos();
        this.kmPercorridos = t.getKmPercorridos();
        this.velocidade = calculaVelocidadeVol();
    }

    //GETTERS
    public double getPrecoPeso(){
        return this.precoKg;
    }
    
    public String getNIF() {
        return this.nif;
    }

    public double getRaio() {
        return this.raio;
    }

    public double getPrecoPorKm() {
        return this.precoKm;
    }

    public boolean getDisponibilidade() {
        return this.disponivel;
    }

    public List<String> getEncAceites() {
        return new ArrayList<>(this.encAceites);
    }

    public int getMaxCapacidade() {
        return this.maxCapacidade;
    }

    public List<Integer> getClassif() {
        return new ArrayList<>(this.classificacao);
    }

    public boolean aceitoTransporteMedicamentos() {
        return this.medicamentos;
    }

    public double getKmPercorridos() {
        return this.kmPercorridos;
    }
    
    public double getVelocidade() {
        return this.velocidade;
    } 
    
    //SETTERS
    
    public void setPrecoPeso(double preco){
        this.precoKg=preco;
    }
    public void setNIF(String nif) {
        this.nif = nif;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }

    public void setPrecoPorKm(double preco) {
        this.precoKm = preco;
    }

    public void setDisponibilidade(boolean b) {
        this.disponivel = b;
    }

    public void setEncAceites(List<String> e) {
        this.encAceites = new ArrayList<>(e);
    }

    public void setMaxCapacidade(int max) {
        this.maxCapacidade = max;
    }

    public void setClassif(List<Integer> e) {
        this.classificacao = new ArrayList<>(e);
    }

    public void aceitaMedicamentos(boolean state) {
        this.medicamentos = state;
    }

    public void setKmPercorridos(double km) {
        this.kmPercorridos = km;
    }
    
    public void setVelocidade(double vel) {
        this.velocidade = vel;
    }
    
    //CLONE
    public Transportadora clone() {
        return new Transportadora(this);
    }

    //toSTRING 
    public String toString() {
        StringBuilder sb = new StringBuilder("Transportadora\n");
        sb.append(super.toString());
        sb.append("NIF: '").append(this.nif).append("'\n");
        sb.append("Raio: ").append(this.raio).append("km\n");
        sb.append("Preço por km: ").append(this.precoKm).append("\n");
        sb.append("Preço por kg: ").append(this.precoKg).append("\n");
        sb.append("Encomendas Aceites: ").append(this.encAceites).append("\n");
        sb.append("Classificações: ").append(this.classificacao).append("\n");
        sb.append("Máxima capacidade: ").append(this.maxCapacidade).append("\n");
        sb.append("Transporta encs. médicas?:").append(this.medicamentos).append("\n");
        sb.append("Kms disponível:").append(this.disponivel).append("\n");
        sb.append("Kms percorridos:").append(this.kmPercorridos).append("\n");
        return sb.toString();
    }

    
    public void addClassif(int i) {
        this.classificacao.add(i);
    }
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Transportadora that = (Transportadora) o;
        return Double.compare(that.raio, raio) == 0 &&
                Double.compare(that.precoKm, precoKm) == 0 &&
                nif.equals(that.nif) &&
                Double.compare(that.precoKg, precoKg) == 0 &&
                that.classificacao.containsAll(classificacao) &&
                that.encAceites.containsAll(encAceites) &&
                that.medicamentos==medicamentos &&
                Double.compare(that.velocidade, velocidade) == 0 &&
                that.disponivel==disponivel &&
                that.maxCapacidade==maxCapacidade &&
                Double.compare(that.kmPercorridos, kmPercorridos) == 0;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), nif, raio, precoKm);
    }

    public void addEncomenda(String cod) {
        if (this.encAceites.contains(cod) || this.encAceites.size() == this.maxCapacidade) return;
        else if (this.encAceites.isEmpty()) this.disponivel = true;
        this.encAceites.add(cod);
        if (this.encAceites.size() == this.maxCapacidade) this.disponivel = false;
    }

    public double getAverageClassif() {
        return this.classificacao.stream().mapToInt(val -> val).average().orElse(0.0);
    }

    public double totalPreco(double dist) {
        return this.precoKm * dist;
    }
    
    public void addToKmPercorridos(double km) {
        this.kmPercorridos += km;
    }
}

