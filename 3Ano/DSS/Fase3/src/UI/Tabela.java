package UI;

import java.util.*;


public class Tabela {
    private String titulo;
    private String[] subtitulos;
    private int colunas;
    private int linhas;
    private Map<String,String> res;



    public Tabela(){
        this.titulo = " ";
        this.colunas = 0;
        this.subtitulos= new String[this.colunas];
        this.linhas = 0;
        this.res = new HashMap<>();
    }



    public Tabela(String titulo, String[] subtitulos, int colunas, Map<String, String> res) {
        this.titulo = titulo;
        this.subtitulos = subtitulos;
        this.colunas = colunas;
        this.res = res;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setSubtitulos(String[] subtitulos) {
        this.subtitulos = subtitulos;
    }

    public void setColunas(int colunas) {
        this.colunas = colunas;
    }

    public void setRes(Map<String,String> res) {
        this.res = res;
    }

    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }

    public void printaTitulo(){
        System.out.printf("\033[1;35m"+"               %-27s"+ "\033[0m",titulo);
    }

    public void printaSubtitulos(){
        for(String i : subtitulos){
            System.out.printf("\033[1;36m" + "%-50s"+ "\033[0m",i);
        }
    }
    public void printaLinha(String add){
        System.out.print("\n«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»" + add + "\n");
    }

    public void printRes(){
        for(String r : res.keySet()){
            System.out.printf("%-50s",r);
            System.out.print(res.get(r));
            System.out.println();
        }
    }
    public void menu (int escolha){
        if(escolha==0)System.out.print("\033[1;35m"+"Menu(M)"+ "\033[0m");
        else System.out.println("\033[1;35m"+"Menu(M)                                  Opção(O)"+ "\033[0m");
    }

    public void run() {
        System.out.print("\nOpção: ");
        String op;
        Scanner is = new Scanner(System.in);
        do {
            op = is.next();

            if (!(op.equals("M"))) {
                System.out.println("Opção indisponível! Tente novamente.");
            }
        } while (!(op.equals("M")));
    }


}
