import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.io.*;

public class Encomenda implements Comparable<Encomenda>,Serializable {
    private String codEnc;
    private String codUtil;
    private String codLoja;
    private double peso;
    private Map<String, LinhaEncomenda> produtos;
    private LocalDateTime data;
    private boolean foiSolicitada;
    private boolean foiEntregue;
    private String quemTransportou;
    private boolean encMedica;
    private double distPercorrida;

    public Encomenda(String codEnc, String codUtil, String codLoja, double peso) {
        this.codEnc = codEnc;
        this.codUtil = codUtil;
        this.codLoja = codLoja;
        this.peso = peso;
        this.produtos = new HashMap<>();
        this.data = LocalDateTime.now();
        this.foiSolicitada = false;
        this.foiEntregue = false;
        this.quemTransportou = "";
        this.encMedica = false;
        this.distPercorrida = 0;
    }
    
    public Encomenda(String codEnc, String codUtil, String codLoja, double peso,boolean em) {
        this.codEnc = codEnc;
        this.codUtil = codUtil;
        this.codLoja = codLoja;
        this.peso = peso;
        this.produtos = new HashMap<>();
        this.data = LocalDateTime.now();
        this.foiSolicitada = false;
        this.foiEntregue = false;
        this.quemTransportou = "";
        this.encMedica = em;
        this.distPercorrida = 0;
    }
    

    public Encomenda (String newCodEnc, String newCodUtil, String newCodLoja, double newPeso, HashMap<String, LinhaEncomenda> le,LocalDateTime dt,boolean b,boolean fe,String qt,boolean em){
        this.codEnc = newCodEnc;
        this.codUtil = newCodUtil;
        this.codLoja = newCodLoja;
        this.peso = newPeso;
        this.setProdutos(le);
        this.data = dt;
        this.foiSolicitada = b;
        this.foiEntregue = fe;
        this.quemTransportou = qt;
        this.encMedica = em;
        this.distPercorrida = 0;
    }
    
     public Encomenda (Encomenda e){
        this.codEnc = e.codEnc;
        this.codUtil = e.codUtil;
        this.codLoja = e.codLoja;
        this.peso = e.peso;
        this.produtos = e.getProdutos();
        this.data = e.getData();
        this.foiSolicitada = e.foiSolicitada;
        this.foiEntregue = e.foiEntregue;
        this.quemTransportou = e.quemTransportou;
        this.encMedica = e.aceitoTransporteMedicamentos();
        this.distPercorrida = e.distPercorrida;
    }
    
    
    public String getCodEnc() {return this.codEnc;}
    
    public String getCodUtil() {return this.codUtil;}
    
    public String getCodLoja() {return this.codLoja;}
    
    public double getPeso() {return this.peso;}
    
    public Map<String, LinhaEncomenda> getProdutos() {
        return this.produtos.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e->e.getValue().clone()));
    }
    public LocalDateTime getData(){
        return this.data;
    }
    
    public boolean getFoiSolicitada(){
        return this.foiSolicitada;
    }
    
    
    public boolean getFoiEntregue(){
        return this.foiEntregue;
    }
    public String getQuemTransportou(){
        return this.quemTransportou;
    }
    
    public boolean aceitoTransporteMedicamentos() {
        return this.encMedica;
    }
    
    public double getDistPercorrida() {
        return this.distPercorrida;
    }
    
    public void setCodEnc(String newCod) {this.codEnc = newCod;}
    
    public void setCodUtil(String newCod) {this.codUtil = newCod;}
    
    public void setCodLoja(String newCod) {this.codLoja = newCod;}
    
    public void setPeso(double newPeso) {this.peso = newPeso;}
    
    public void setProdutos(Map<String, LinhaEncomenda> produtos) {
        this.produtos = produtos.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e->e.getValue().clone()));
    }
    
    public void setData(LocalDateTime d){
        this.data = d;
    }
    public void setFoiSolicitada(boolean b){
        this.foiSolicitada=b;
    }
    
    public void setFoiEntregue(boolean b){
        this.foiEntregue=b;
    }
    
    public void setQuemTransportou(String qt){
       this.quemTransportou = qt;
       setData(LocalDateTime.now());
    }
    
    public void aceitaMedicamentos(boolean state) {
       this.encMedica = state;
    }
    
    public void setDistPercorrida(double dist) {
        this.distPercorrida = dist;
    }
    
    public void addProduto(LinhaEncomenda le) {
        this.produtos.put(le.getCodProduto(), le.clone());
    }
    
    public Encomenda clone(){
        return new Encomenda(this);
    }
    
    //To string , para mostrar o historico de forma a caber todas as encoemndas, mostramos aquilo que consideramos mais importante
    public String toString() {
        StringBuilder sb = new StringBuilder(" ");
        sb.append("Codigo da Encomenda: ").append(this.codEnc).append("\n");
        sb.append("Codigo do Utilizador: ").append(this.codUtil).append("\n");
        sb.append("Codigo da Loja: ").append(this.codLoja).append("\n");
        sb.append("Peso: ").append(this.peso).append("\n");
        sb.append("Produtos: ").append(this.produtos.values().toString()).append("\n");
        sb.append("Data: ").append(this.data.toString()).append("\n");
        sb.append("Quem Transportou: ").append(this.quemTransportou).append("\n");
        sb.append("Enc Medica?:").append(this.encMedica).append("\n");
        sb.append("Distancia percorrida:").append(this.distPercorrida).append("\n");
        return sb.toString();
    }
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Encomenda encomenda = (Encomenda) o;
        return Objects.equals(codEnc, encomenda.codEnc) &&
               encomenda.codUtil.equals(this.codUtil) &&
               encomenda.codLoja.equals(this.codLoja) &&
               Double.compare(encomenda.peso, peso) == 0 &&
               encomenda.data.equals(data) &&
               encomenda.encMedica==encMedica &&
               encomenda.quemTransportou.equals(quemTransportou) &&
               encomenda.foiEntregue==foiEntregue &&
               encomenda.foiSolicitada==foiSolicitada &&
               Double.compare(encomenda.distPercorrida,distPercorrida) == 0 &&
               encomenda.produtos.equals(produtos);
               
       }

    public int hashCode() {
        return Objects.hash(codEnc, codUtil, codLoja, peso, produtos);
    }

    public int compareTo(Encomenda e) {
        return this.codEnc.compareTo(e.codEnc);
    } 
    
    
}
