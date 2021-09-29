import java.util.*;
/**
 * Escreva a descrição da classe Veiculo aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public abstract class Veiculo implements Comparable<Veiculo>, java.io.Serializable
{
    private String matricula;
    private String marca;
    private String modelo;
    private int ano;
    private double velMedia;
    private double precoKm;
    private ArrayList<Integer> classif;
    private int kms;
    private int kmsUltimo; // kms ultima viagem
    
    public Veiculo() {
        this.matricula = "";
        this.marca = "";
        this.modelo = "";
        this.ano = 0;
        this.velMedia = 0;
        this.precoKm = 0;
        this.classif = new ArrayList<>();
        this.kms = 0;
        this.kmsUltimo = 0;
    }
    
    public Veiculo(String mat, String marc, String mod, int ano, double vel, double preco) throws ValorInvalidoException {
        if (ano <= 0 || vel <= 0 || preco <= 0) 
            throw new ValorInvalidoException();
        else {
            this.matricula = mat;
            this.marca = marc;
            this.modelo = mod;
            this.ano = ano;
            this.velMedia = vel;
            this.precoKm = preco;
            this.classif = new ArrayList<>();
            this.kms = 0;
            this.kmsUltimo = 0;
        }
    }
    
    public Veiculo(String mat, String marc, String mod, int ano, double vel, double preco, ArrayList<Integer> classif, int kms, int kmsUltimo) throws ValorInvalidoException {
        if (ano <= 0 || vel <= 0 || preco <= 0 || kms < 0 || kmsUltimo < 0) 
            throw new ValorInvalidoException();
        else {
            this.matricula = mat;
            this.marca = marc;
            this.modelo = mod;
            this.ano = ano;
            this.velMedia = vel;
            this.precoKm = preco;
            this.classif = new ArrayList<Integer>(classif);
            this.kms = kms;
            this.kmsUltimo = kmsUltimo;
        }
    }
    
    public Veiculo(Veiculo v) {
        this.matricula = v.getMatricula();
        this.marca = v.getMarca();
        this.modelo = v.getModelo();
        this.ano = v.getAno();
        this.velMedia = v.getVelMedia();
        this.precoKm = v.getPrecoKm();
        this.classif = v.getClassif();
        this.kms = v.getKms();
        this.kmsUltimo = v.getKmsUltimo();
    }
    
    // GETTERS
    
    public String getMatricula(){
        return this.matricula;
    }
    
    public String getMarca (){
        return this.marca;
    }
    
    public String getModelo(){
        return this.modelo;
    }
    
    public int getAno() {
        return this.ano;
    }
    
    public double getVelMedia() {
        return this.velMedia;
    }
    
    public double getPrecoKm() {
        return this.precoKm;
    }
    
    public ArrayList<Integer> getClassif() {
        return new ArrayList<Integer>(this.classif);
    }
    
    public int getKms() {
        return this.kms;
    }
    
    public int getKmsUltimo() {
        return this.kmsUltimo;
    }
    
    // SETTERS
    
    public void setMatricula(String mat){
        this.matricula = mat;
    }
    
    public void setMarca (String marca){
        this.marca = marca;
    }
    
    public void setModelo(String mod){
        this.modelo = mod;
    }
    
    public void setAno(int ano) {
        this.ano = ano;
    }
    
    public void setVelMedia(double vel) {
        this.velMedia = vel;
    }
    
    public void setPrecoKm(double preco) {
        this.precoKm = preco;
    }
    
    public void setClassif(ArrayList<Integer> classif) {
        this.classif = new ArrayList<Integer>(classif);
    }
    
    public void setKms(int kms) {
        this.kms = kms;
    }
    
    public void setKmsUltimo(int kmsUltimo) {
        this.kmsUltimo = kmsUltimo;
    }
    
    public abstract double custoRealKm();
    
    public abstract Veiculo clone();
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Matricula: ").append(this.matricula).append("\n");
        sb.append("Marca: ").append(this.marca).append("\n");
        sb.append("Modelo: ").append(this.modelo).append("\n");
        sb.append("Ano: ").append(this.ano).append("\n");
        sb.append("Velocidade Média(Km/h): ").append(this.velMedia).append("\n");
        sb.append("Preco(por Km): ").append(this.precoKm).append("\n");
        sb.append("Classificaçoes (0 a 10): ").append(this.classif.toString()).append("\n");
        sb.append("Kms: ").append(this.kms).append("\n");
        sb.append("Kms do ultimo aluguer: ").append(this.kmsUltimo).append("\n");
        return sb.toString();
    }
    
    public int compareTo(Veiculo v) {
        if (this.marca.compareTo(v.getMarca()) == 0)
            return (this.modelo.compareTo(v.getModelo()));
        else 
            return (this.marca.compareTo(v.getMarca()));
    }
}
