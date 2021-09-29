
/**
 * Escreva a descrição da classe Encomenda aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class LinhaEncomenda
{
    private String ref;
    private String desc;
    private double preco;
    private int quant;
    private double imp;
    private double dc;
    
    public LinhaEncomenda(){
        this.ref = "";
        this.desc = "";
        this.preco = 0;
        this.quant = 0;
        this.imp = 0;
        this.dc = 0;
    }
    
    public LinhaEncomenda (String r, String d, double p, int q, double i, double dc) {
        this.ref = r;
        this.desc = d;
        this.preco = p;
        this.quant = q;
        this.imp = i;
        this.dc = dc;
    
    }
    
    public LinhaEncomenda(LinhaEncomenda e){
        this.ref = e.getR();
        this.desc = e.getD();
        this.preco = e.getP();
        this.quant = e.getQ();
        this.imp = e.getI();
        this.dc = e.getDC();
    }
    
    public String getR(){
        return this.ref;
    }
    
    public String getD(){
        return this.desc;
    }
    
    public double getP(){
        return this.preco;
    }
    
    public int getQ(){
        return this.quant;
    }
    
    public double getI(){
        return this.imp;
    }
    
    public double getDC(){
        return this.dc;
    }
    
    public void setR(String referencia){
        this.ref = referencia;
    }
    
    public void setD(String descricao){
        this.desc = descricao;
    }
    
    public void setP(double preco){
        this.preco = preco;
    }
    
    public void setQ(int quantidade){
        this.quant = quantidade;
    }
    
    public void setI(double imposto){
        this.imp = imposto;
    }
    
    public void setDC(double desconto){
        this.dc = desconto;
    }
    
    public double calculaValorLinhaEnc() {
        double valor = this.quant * this.preco;
        valor -= valor*this.dc;
        valor *= 1+this.imp;
        return valor;
    }
    
    public LinhaEncomenda clone() {
        return new LinhaEncomenda(this);
    }
    
    public boolean equals(Object obj) {
        if(obj==this) return true;
        if(obj==null || obj.getClass() != this.getClass()) return false;
        LinhaEncomenda le = (LinhaEncomenda) obj;
        return le.getR().equals(this.ref) &&
              le.getD().equals(this.desc) && 
              le.getP() == this.preco;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Referencia: ").append(this.ref).append("\n");
        sb.append("Descrição: ").append(this.desc).append("\n");
        sb.append("Preço: ").append(this.preco).append("\n");
        sb.append("Quantidade: ").append(this.quant).append("\n");
        sb.append("Imposto: ").append(this.imp).append("\n");
        sb.append("Desconto: ").append(this.dc).append("\n");
        return sb.toString();
    }            
}
