import java.awt.geom.Point2D;
import java.util.Objects;
import java.io.*;

public class Conta implements Comparable,Serializable {
    private String codigo;
    private String nome;
    private Point2D gps;
    private String email;
    private String password;
    

    public Conta(String cod, String nome, double x, double y) {
        this.codigo = cod;
        this.nome = nome;
        this.gps = new Point2D.Double(x, y);
        this.email = cod;
        this.password = cod;
    }

    public Conta(String cod, String nome, double x, double y, String email, String password) {
        this.codigo = cod;
        this.nome = nome;
        this.gps = new Point2D.Double(x, y);
        this.email = email;
        this.password = password;
    }
    
    
    public Conta(Conta outro){
        this.codigo = outro.codigo;
        this.nome = outro.nome;
        this.gps = new Point2D.Double(outro.getGPSx(), outro.getGPSy());
        this.email = outro.email;
        this.password = outro.password;
    }

    // GETTERS
    public String getCodigo(){
        return this.codigo;
    }
    
    
    public String getNome(){
        return this.nome;
    }
    
    public double getGPSx() { return this.gps.getX(); }
    public double getGPSy() { return this.gps.getY(); }
    
    public String getEmail(){
        return this.email;
    }
    
    public double calcDist(double x, double y){
        return Point.distance(this.gps.getX(), this.gps.getY(), x, y);
    }

    //SETTERS
    
    public void setCodigo(String cod){
        this.codigo = cod;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public void setGPS(double x, double y){
        this.gps.setLocation(x, y);
    }
    
    public void setEmail(String novoEmail){
        this.email = novoEmail ;
    }
    
    public void setPassword(String novaPass){
        this.password = novaPass;
    }
    
    
    //CLONE
    public Conta clone() {
        return new Conta(this);
    }
    
    //EQUALS
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return this.codigo.equals(conta.codigo) &&
                this.nome.equals(conta.nome) &&
                this.gps.equals(conta.gps) &&
                this.email.equals(conta.email) &&
                this.password.equals(conta.password);
    }

    public int hashCode() {
        return Objects.hash(codigo, nome, gps, email, password);
    }

    //ToString
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Codigo da conta: '").append(this.codigo).append("'\n");
        sb.append("Nome da conta: '").append(this.nome).append("'\n");
        sb.append("Coordenadas: ").append(this.gps.toString()).append("\n");
        sb.append("Email: '").append(this.email).append("'\n");
        sb.append("Password: '").append(this.password.replaceAll("\\S","*")).append("'\n");
        return sb.toString();
    }

    public int compareTo(Object o) {
        Conta u = (Conta) o;
        return this.codigo.compareTo(u.codigo);
    }

    public boolean checkPassword (String password){
        return this.password.equals(password);
    }
}
