import java.util.*;
/**
 * Escreva a descrição da classe VeiculoPremium aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class VeiculoPremium extends Veiculo implements BonificaKm
{
    private double taxa; // >= 1
    private int pontosKm;
    /**
     * COnstrutor para objetos da classe VeiculoPremium
     */
    public VeiculoPremium() {
       super();
       this.taxa = 0;
    }
    
    public VeiculoPremium (VeiculoPremium v) {
        super(v);
        this.taxa = v.getTaxa();
    }
    
     public VeiculoPremium(String mat, String marc, String mod, int ano, double vel, double preco, double taxa) throws ValorInvalidoException {
        super(mat, marc, mod, ano, vel, preco);
        this.taxa = taxa;
    }
    
    public VeiculoPremium(String mat, String marc, String mod, int ano, double vel, double preco, ArrayList<Integer> classif, int kms, int kmsUltimo, double taxa) throws ValorInvalidoException {
        super(mat, marc, mod, ano, vel, preco, classif, kms, kmsUltimo);
        this.taxa = taxa;
    }
    
    public double getTaxa() {
        return this.taxa;
    }
    
    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }
    
    public int getPontosKm() {
        return this.pontosKm;
    }
    
    public void setPontosKm(int pontos) {
        this.pontosKm = pontos;
    }
    
    public double custoRealKm() {
        return super.getPrecoKm()*this.taxa;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder(); 
        sb.append("---VeiculoPremium---\n");
        sb.append(super.toString());
        sb.append("Taxa: ").append(this.taxa).append("\n");
        return sb.toString();
    }
    
    public VeiculoPremium clone() {
        return new VeiculoPremium(this);
    }
}
