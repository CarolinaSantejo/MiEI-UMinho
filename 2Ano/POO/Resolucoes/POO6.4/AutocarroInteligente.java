import java.util.*;
/**
 * Escreva a descrição da classe AutocarroInteligente aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class AutocarroInteligente extends Veiculo implements BonificaKm
{
   private int lotacao;
   private int ocupacao;
   private int pontosKm;
   
   public AutocarroInteligente() {
       super();
       this.lotacao = 0;
       this.ocupacao = 0;
   }
   
   public AutocarroInteligente(AutocarroInteligente v) {
       super(v); 
       this.lotacao = v.getLotacao();
       this.ocupacao = v.getOcupacao(); 
   }
   
   public AutocarroInteligente(String mat, String marc, String mod, int ano, double vel, double preco, int lotacao, int ocupacao) throws ValorInvalidoException{
       super(mat, marc, mod, ano, vel, preco);
       if (lotacao < ocupacao) 
            throw new ValorInvalidoException();
       else {     
            this.lotacao = lotacao;
            this.ocupacao = ocupacao;
       }
    } 
    
   public AutocarroInteligente(String mat, String marc, String mod, int ano, double vel, double preco, ArrayList<Integer> classif, int kms, int kmsUltimo, int lotacao, int ocupacao) throws ValorInvalidoException{
       super(mat, marc, mod, ano, vel, preco, classif, kms, kmsUltimo);
       if (lotacao < ocupacao) 
            throw new ValorInvalidoException();
       else {
            this.lotacao = lotacao;
            this.ocupacao = ocupacao;
       }
    }
   
   public int getLotacao() {
        return this.lotacao;
    }
    
   public void setLotacao (int lotacao) {
       this.lotacao = lotacao;
   }
   
   public int getOcupacao() {
       return this.ocupacao;
   }
    
   public void setOcupacao (int ocupacao) {
       this.ocupacao = ocupacao;
   }
    
   public int getPontosKm() {
       return (int) custoRealKm();
   }
   
   public void setPontosKm(int pontos) {
       this.pontosKm = pontos;
   }
   
   public double custoRealKm() {
       double ratio = lotacao/ocupacao;
       double factor;
       if (ratio < 0.6)
        factor = 0.5;
       else 
        factor = 0.75;
       
       return super.getPrecoKm()*1.1*factor;
   }
   
   public String toString() {
        StringBuilder sb = new StringBuilder(); 
        sb.append("---AutocarroInteligente---\n");
        sb.append(super.toString());
        sb.append("Lotacao: ").append(this.lotacao).append("\n");
        sb.append("Ocupacao: ").append(this.ocupacao).append("\n");
        return sb.toString();
    }
   
   public AutocarroInteligente clone() {
        return new AutocarroInteligente(this);
    }
}
