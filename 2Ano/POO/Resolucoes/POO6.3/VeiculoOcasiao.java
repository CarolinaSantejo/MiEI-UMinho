import java.util.*;
/**
 * Escreva a descrição da classe VeiculoOcasiao aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class VeiculoOcasiao extends Veiculo
{
    private boolean desconto;

    /**
     * Construtor para objetos da classe VeiculoOcasiao
     */
    public VeiculoOcasiao()
    {
        super();
        this.desconto = false;
    }
    
    public VeiculoOcasiao(VeiculoOcasiao v) {
        super(v);
        this.desconto = v.getDesconto();
    }
    
    public VeiculoOcasiao(String mat, String marc, String mod, int ano, double vel, double preco, ArrayList<Integer> classif, int kms, int kmsUltimo, boolean desc) {
        super(mat, marc, mod, ano, vel, preco, classif, kms, kmsUltimo);
        this.desconto = desc;
    }
    
    public boolean getDesconto() {
        return this.desconto;
    }
    
    public void setDesconto (boolean desc) {
        this.desconto = desc;
    }
    
    public double custoRealKm() {
       double custo = super.getPrecoKm();
       custo *= 1.1;
       return (desconto?custo*0.75: custo);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder(); 
        sb.append("---VeiculoOcasiao---\n");
        sb.append(super.toString());
        sb.append("Desconto?: ").append(this.desconto).append("\n");
        return sb.toString();
    }
    
    public VeiculoOcasiao clone() {
        return new VeiculoOcasiao(this);
    }
}
