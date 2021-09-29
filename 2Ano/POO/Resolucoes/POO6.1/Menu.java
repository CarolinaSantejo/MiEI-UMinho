import java.util.*;
import java.io.*;

/**
 * Escreva a descrição da classe Menu aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Menu
{
    private List<String> opcoes;
    private int op;
    
    public Menu(String[] opcoes) {
        this.opcoes = Arrays.asList(opcoes);
        this.op = 0;
    }
    
    public void executa() {
        do {
            showMenu();
            this.op = lerOpcao();
        } while (this.op == -1);
        
    }
    
    public String execLer() {
        Scanner ler = new Scanner(System.in);
        showOpcoes();
        String s = ler.nextLine();
        return s;
    }
    
    public String exec() {
        Scanner ler = new Scanner(System.in);
        show();
        String s = ler.nextLine();
        return s;
    }
    
    public void showMenu() {
        System.out.println("******** MENU DRIVEIT ********\n");
        for (int i = 0; i<this.opcoes.size(); i++) {
            System.out.print(i+1);
            System.out.print(" - ");
            System.out.println(this.opcoes.get(i));
        }
        System.out.println("0 - Sair do programa");
    }
    
    public void showOpcoes() {
        System.out.println("Selecione uma das opções:");
        for (int i = 0; i<this.opcoes.size(); i++) {
            System.out.print(i+1);
            System.out.print(" - ");
            System.out.println(this.opcoes.get(i));
        }
        System.out.println("0 - Sair do programa");
    }
    
    public void show() {
        for (int i = 0; i<this.opcoes.size(); i++) {
            System.out.println(this.opcoes.get(i));
        }
    }
    
    public String novoVeiculoMenu(int op) {
        Scanner ler = new Scanner(System.in);
        System.out.println(this.opcoes.get(op));
        String s = ler.nextLine();
        return s;
    }
    
    public int lerOpcao() {
        int op;
        Scanner ler = new Scanner(System.in);
        
        System.out.print("> ");
        try {
            op = ler.nextInt();
        }
        catch (InputMismatchException e) {
            op = -1;   
        }
        if (op<0 || op>this.opcoes.size()) {
            System.out.println("Opcao invalida!");
            op = -1;   
        }
        return op;
    }
    
    public int getOpcao() {
        return this.op;
    }
}
