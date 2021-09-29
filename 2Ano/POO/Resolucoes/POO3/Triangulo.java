
/**
 * Escreva a descrição da classe Triangulo aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Triangulo
{
    private Ponto pA;
    private Ponto pB;
    private Ponto pC;
    public Triangulo (){
        this.pA = new Ponto();
        this.pB = new Ponto();
        this.pC = new Ponto();
    }
    
    /* Como o diagrama de classe é composição é necessário fazer clone */
    
    public Triangulo (Ponto a, Ponto b, Ponto c){
        this.pA = a.clone();
        this.pB = b.clone();
        this.pC = c.clone();
    }
    
    public Triangulo(Triangulo umTriang){
        this.pA = umTriang.getPA();
        this.pB = umTriang.getPB();
        this.pC = umTriang.getPC();
    }
    
    public Ponto getPA() {
        return this.pA.clone();
    }
    
    public Ponto getPB() {
        return this.pB.clone();
    }
    
    public Ponto getPC() {
        return this.pC.clone();
    }
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        Triangulo t = (Triangulo) o;
        return (this.pA == t.getPA() && this.pB == t.getPB() && this.pC == t.getPC());
      
    }
    
    public String toString() {
        return "Triangulo: --> PA = " + this.pA.toString() + " -  PB = " + this.pA.toString() + " -  PC = " + this.pC.toString();
    }
    
    public Triangulo clone() {
        return new Triangulo(this);    
    }    
    
    public double calculaAreaTriangulo() {
        double a = this.pA.distancia(this.pB);
        double b = this.pA.distancia(this.pC);
        double c = this.pB.distancia(this.pC);
        double p = (a+b+c)/2;
        double area = Math.sqrt(p*(p-a)*(p-b)*(p-c));
        return area;
    }
}
