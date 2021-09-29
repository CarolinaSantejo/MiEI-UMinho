import java.util.*;
/**
 * Escreva a descrição da classe VeiculoNormal aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class VeiculoNormal extends Veiculo
{
    public VeiculoNormal() {
        super();
    }
    
    public VeiculoNormal(VeiculoNormal v) {
        super(v);
    }
    
    public VeiculoNormal(String mat, String marc, String mod, int ano, double vel, double preco) throws ValorInvalidoException {
        super(mat, marc, mod, ano, vel, preco);
    }
    
    public VeiculoNormal(String mat, String marc, String mod, int ano, double vel, double preco, ArrayList<Integer> classif, int kms, int kmsUltimo) throws ValorInvalidoException {
        super(mat, marc, mod, ano, vel, preco, classif, kms, kmsUltimo);
    }
    
    public double custoRealKm() {
       double custo = super.getPrecoKm();
       return custo*1.1;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder(); 
        sb.append("---VeiculoNormal---\n");
        sb.append(super.toString());
        return sb.toString();
    }
    
    public VeiculoNormal clone() {
        return new VeiculoNormal(this);
    }
}
