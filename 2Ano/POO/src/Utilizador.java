import java.io.*;


public class Utilizador extends Conta implements Serializable {
     private int encTransportadas;
    public Utilizador(String codigo, String nome, double x, double y) {
        super(codigo, nome, x, y);
        this.encTransportadas = 0;
    }

    public Utilizador(String codigo, String nome, double x, double y, String email, String password) {
        super(codigo, nome, x, y, email, password);
        this.encTransportadas = 0;
    }
    
    public Utilizador(String codigo, String nome, double x, double y, String email, String password,int encT) {
        super(codigo, nome, x, y, email, password);
        this.encTransportadas = encT;
    }
    

    public Utilizador(Utilizador outro) {
        super(outro);
        this.encTransportadas = outro.encTransportadas;
    }
    
    public int getEncTransportadas(){
        return this.encTransportadas;
    }
    
    public void setEncTransportadas(int t){
        this.encTransportadas = t;
    }
    
    public void addToEncTransp(){
        this.encTransportadas++;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Utilizador\n");
        sb.append(super.toString());
        sb.append("Encomendas transportadas: ").append(this.encTransportadas).append("\n");
        return sb.toString();
    }
    
    
    public Utilizador clone() {
        return new Utilizador(this);
    }

    public boolean equals(Utilizador u) {
        return super.equals(u) && (u.getEncTransportadas()==this.encTransportadas);
    }
    
    
    
}
