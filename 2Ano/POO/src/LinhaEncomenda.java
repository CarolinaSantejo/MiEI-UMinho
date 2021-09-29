import java.util.Objects;
import java.io.*;

public class LinhaEncomenda implements Serializable {

    private String codProduto;
    private String descricao;
    private double quantidade;
    private double valorUnitario;
    
    public LinhaEncomenda() {
        this.codProduto = "";
        this.descricao = "";
        this.quantidade = 0;
        this.valorUnitario = 0;
    }

    public LinhaEncomenda(String cod, String desc, double quant, double valor) {
        this.codProduto = cod;
        this.descricao = desc;
        this.quantidade = quant;
        this.valorUnitario = valor;
    }

    public LinhaEncomenda(LinhaEncomenda le) {
        this.codProduto = le.getCodProduto();
        this.descricao = le.getDescricao();
        this.quantidade = le.getQuantidade();
        this.valorUnitario = le.getValor();
    }

    //GETTERS

    public String getCodProduto() {
        return this.codProduto;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public double getQuantidade() {
        return this.quantidade;
    }

    public double getValor() {
        return this.valorUnitario;
    }

    //SETTERS

    public void setCodProduto(String cod) {
        this.codProduto = cod;
    }

    public void setDescricao(String desc) {
        this.descricao = desc;
    }

    public void setQuantidade(double quant) {
        this.quantidade = quant;
    }

    public void setValor(double valor) {
        this.valorUnitario = valor;
    }


    //CLONE
    public LinhaEncomenda clone() {
        return new LinhaEncomenda(this);
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinhaEncomenda that = (LinhaEncomenda) o;
        return Double.compare(that.quantidade, quantidade) == 0 &&
                Double.compare(that.valorUnitario, valorUnitario) == 0 &&
                this.codProduto.equals(that.codProduto) &&
                this.descricao.equals(that.descricao);
    }

    public int hashCode() {
        return Objects.hash(codProduto, descricao, quantidade, valorUnitario);
    }

    //toString

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Código de Produto: ").append(this.codProduto).append("\n");
        sb.append("Descrição: ").append(this.descricao).append("\n");
        sb.append("Quantidade encomendada: ").append(this.quantidade).append("\n");
        sb.append("Valor Unitário: ").append(this.valorUnitario).append("\n");
        return sb.toString();
    }
}
