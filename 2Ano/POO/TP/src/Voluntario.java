import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class Voluntario extends Conta implements Serializable, TranspVolunt ,Randoms{
    private double raio;
    private String encAceite; //Encomenda que aceitou
    private boolean disponivel;
    private List<Integer> classificacao;
    private boolean medicamentos;
    private double velocidade;

    public Voluntario(String cod, String nome, double x, double y, double raio) {
        super(cod,nome,x,y);
        this.raio = raio;
        this.encAceite = null;
        this.disponivel = true;
        this.classificacao = new ArrayList<>();
        this.medicamentos = false;
        this.velocidade = calculaVelocidadeVol();
    }
    
    public Voluntario(String cod, String nome, double x, double y, double raio, String novoEmail, String novaPassword) {
        super(cod,nome,x,y,novoEmail,novaPassword);
        this.raio = raio;
        this.encAceite = "";
        this.disponivel = true;
        this.classificacao = new ArrayList<>();
        this.medicamentos = false;
        this.velocidade = calculaVelocidadeVol();
    }

    public Voluntario(String cod, String nome, double x, double y, double raio, String novoEmail, String novaPassword,String aceite, List<Integer>list) {
        super(cod,nome,x,y,novoEmail,novaPassword);
        this.raio = raio;
        this.encAceite = aceite;
        this.disponivel = aceite.equals("");
        this.classificacao = new ArrayList<>(list);
        this.medicamentos = false;
        this.velocidade = calculaVelocidadeVol();
    }
    
    
    public Voluntario(String cod, String nome, double x, double y, double raio, String novoEmail, String novaPassword, double vel,boolean med) {
        super(cod,nome,x,y,novoEmail,novaPassword);
        this.raio = raio;
        this.encAceite = "";
        this.disponivel = true;
        this.classificacao = new ArrayList<>();
        this.medicamentos = med;
        this.velocidade = vel;
    }
    
    public Voluntario(Voluntario outro) {
        super(outro);
        this.raio = outro.raio;
        this.encAceite = outro.getEncAceite();
        this.disponivel = outro.disponivel;
        this.classificacao = outro.getClassif();
        this.medicamentos = outro.aceitoTransporteMedicamentos();
        this.velocidade = outro.velocidade;
    }

    // GETTERS
    public double getRaio() {
        return this.raio;
    }
    
    public boolean getDisponibilidade(){
        return this.disponivel;
    }
    
    public String getEncAceite(){
        return this.encAceite;
    }
    
    public List<Integer> getClassif(){
        return new ArrayList(this.classificacao);
    }
    
    public boolean aceitoTransporteMedicamentos() {
        return this.medicamentos;
    }
    
    public double getVelocidade() {
        return this.velocidade;
    } 
    //SETTERS
    public void setRaio(double raio) {
        this.raio = raio;
    }
    
    public void setDisponibilidade(boolean b){
        this.disponivel=b;
    }
    
    public void setEncAceite(String e){
        this.encAceite = e;
    }
    
    public void setClassif(List<Integer> e){
        this.classificacao = new ArrayList(e);
    }
    
    public void aceitaMedicamentos(boolean state) {
        this.medicamentos = state;
    }
    
    public void setVelocidade(double vel) {
        this.velocidade = vel;
    }
    
    //CLONE
    public Voluntario clone() {
        return new Voluntario(this);
    }

    //EQUALS
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Voluntario that = (Voluntario) o;
        return Double.compare(that.raio, raio) == 0 
               && that.encAceite.equals(encAceite)
               && that.disponivel==disponivel
               && that.classificacao.containsAll(classificacao)
               && that.medicamentos==medicamentos
               && velocidade == velocidade;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), raio);
    }

    //ToString 
    
    public String toString() {
        StringBuilder sb = new StringBuilder("Voluntário\n");
        sb.append(super.toString());
        sb.append("Raio: ").append(this.raio).append("km\n");
        sb.append("Velocidade: ").append(this.velocidade).append("\n");
        sb.append("Aceita encs. médicas?: ").append(this.medicamentos).append("\n");
        sb.append("Classificações: ").append(this.classificacao).append("\n");
        sb.append("Encomenda Aceite: ").append(this.encAceite).append("\n");
        sb.append("Disponível?: ").append(this.disponivel).append("\n");
        return sb.toString();
    }
    
    public void addEncomenda (String cod){
       if (this.disponivel) {setEncAceite(cod);this.disponivel = false;}
    }
    
    public void addClassif(int i){
        this.classificacao.add(i);
    }
    
    public double getAverageClassif(){
        return this.classificacao.stream().mapToInt(val -> val).average().orElse(0.0);
    }
}
