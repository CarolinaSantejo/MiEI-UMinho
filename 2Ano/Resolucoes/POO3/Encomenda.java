import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.*; 
import java.util.*; 
/**
 * Escreva a descrição da classe Encomenda aqui.
 * 
 * @author Carolina Santejo 
 * @version (número de versão ou data)
 */
public class Encomenda
{
    private String nomeC;
    private int nif;
    private String morada;
    private int nEnc;
    private LocalDate data;
    private ArrayList<LinhaEncomenda> encomendas;
    
    public Encomenda(String nomeC, int nif, String morada, int nEnc, LocalDate data) {
        this.nomeC = nomeC;
        this.nif = nif;
        this.morada = morada;
        this.nEnc = nEnc;
        this.data = data;
        this.encomendas = new ArrayList<>();
    }
    
    public Encomenda(String nomeC, int nif, String morada, int nEnc, LocalDate data, ArrayList<LinhaEncomenda> encomendas) {
        this.nomeC = nomeC;
        this.nif = nif;
        this.morada = morada;
        this.nEnc = nEnc;
        this.data = data;
        setEncomendas(encomendas);
    }
    
    public void setNomeC (String nomeC) {
        this.nomeC = nomeC;
    }
    
    public void setNif (int nif) {
        this.nif = nif;
    }
    
    public void setMorada (String morada) {
        this.morada = morada;
    }
    
    public void setNEnc(int nEnc) {
        this.nEnc = nEnc;
    }
    
    public void setData(LocalDate data) {
        this.data = data;
    }
    
    public void setEncomendas (ArrayList<LinhaEncomenda> encomendas){
        for(LinhaEncomenda l: encomendas)
            this.encomendas.add(l.clone());
    }
    
    public String getNomeC (){
        return this.nomeC;
    }
    
    public int getNif (){
        return this.nif;
    }
    
    public String getMorada (){
        return this.morada;
    }
    
    public int getNEnc (){
        return this.nEnc;
    }
    
    public LocalDate getData (){
        return this.data;
    }
    
    public ArrayList<LinhaEncomenda> getEncomendas (){
        return this.encomendas;
    }
    
    public String toString (){
        StringBuilder sb = new StringBuilder();
        sb.append("Nome do Cliente: ").append(this.nomeC).append("\n");
        sb.append("Nif: ").append(this.nif).append("\n");
        sb.append("Morada: ").append(this.morada).append("\n");
        sb.append("Nº de Encomenda: ").append(this.nEnc).append("\n");
        sb.append("Data: ").append(this.data).append("\n");
        sb.append("Lista de Encomendas: \n\n");
        int i = 0;
        for(LinhaEncomenda l : this.encomendas) {
            sb.append("Encomenda ").append(i).append(":\n").append(l.toString()).append("\n"); 
            i++;
        }
        return sb.toString();
    }
    
    // Alínea (b)
    
    public double calculaValorTotal(){
        double valor = 0;
        for(LinhaEncomenda l : this.encomendas) {
            valor += l.calculaValorLinhaEnc();
        }
        return valor;
    }
    
    public double calculaValorDesconto (){
        double valor = 0;
        for(LinhaEncomenda l : this.encomendas) {
            valor += l.getP()*l.getDC()*l.getQ();
        }
        return valor;
        
    }
    
    public int numeroTotalProdutos(){
        int valor = 0;
        for(LinhaEncomenda l : this.encomendas) {
            valor += l.getQ();
        }
        return valor;
    }

    public boolean existeProdutoEncomenda(String refProduto){
        boolean found = false;
        Iterator <LinhaEncomenda> it = this.encomendas.iterator();
        LinhaEncomenda l;
        while(it.hasNext() && !found) {
            l = it.next();
            if ((l.getR()).equals(refProduto)) found = true;
        }       
        return found;
    }
    
    public void adicionaLinha(LinhaEncomenda linha){
        this.encomendas.add(linha);
    }
    
    public void removeProduto(String codProd){
        int i = 0;
        Iterator <LinhaEncomenda> it = this.encomendas.iterator();
        LinhaEncomenda l;
        while(it.hasNext()) {
            l = it.next();
            if ((l.getR()).equals(codProd)) it.remove();
        }
        
    }
}
