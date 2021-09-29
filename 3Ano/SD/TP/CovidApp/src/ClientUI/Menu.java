package ClientUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Menu {

    // Interfaces auxiliares

    /** Functional interface para handlers. */
    public interface MenuHandler {
        void execute() throws InterruptedException;
    }

    /** Functional interface para pré-condições. */
    public interface MenuPreCondition {
        boolean validate();
    }

    // Varíável de classe para suportar leitura

    private static Scanner is = new Scanner(System.in);

    // Variáveis de instância
    private int op;
    private List<String> opcoes;                // Lista de opções
    private List<MenuPreCondition> disponivel;  // Lista de pré-condições
    private List<MenuHandler> handlers;         // Lista de handlers

    // Construtor

    /**
     * Constructor for objects of class UI.Menu
     */
    public Menu(String[] opcoes) {
        this.opcoes = Arrays.asList(opcoes);
        this.disponivel = new ArrayList<>();
        this.handlers = new ArrayList<>();
        this.opcoes.forEach(s-> {
            this.disponivel.add(()->true);
            this.handlers.add(()->System.out.println("\nATENÇÃO: Opção não implementada!"));
        });
    }

    // Métodos de instância

    /**
     * Correr o menu.
     *
     * Termina com a opção 0 (zero).
     */
    public void run(int x) throws InterruptedException {
        do {
            show(x);
            op = readOption();
            // testar pré-condição
            if (op>0 && !this.disponivel.get(op-1).validate()) {
                System.out.println("Opção indisponível! Tente novamente.");
            } else if (op>0) {
                // executar handler
                this.handlers.get(op-1).execute();
            }
        } while (op != 0);
        if(x==1) this.handlers.get(5).execute();


    }

    /**
     * Método que regista uma uma pré-condição numa opção do menu.
     *
     * @param i índice da opção (começa em 1)
     * @param b pré-condição a registar
     */
    public void setPreCondition(int i, MenuPreCondition b) {
        this.disponivel.set(i-1,b);
    }

    /**
     * Método para registar um handler numa opção do menu.
     *
     * @param i indice da opção  (começa em 1)
     * @param h handlers a registar
     */
    public void setHandler(int i, MenuHandler h) {
        this.handlers.set(i-1, h);
    }

    // Métodos auxiliares

    /** Apresentar o menu */
    private void show(int x) {
        System.out.println("\033[1;36m"+ "\n**************** MENU ****************"+ "\033[0m");
        if(x==0){
            for (int i=0; i<this.opcoes.size(); i++) {
                System.out.print("\033[1;36m" + (i+1) + "\033[0m");
                System.out.print("\033[1;36m"+" - "+"\033[0m");
                System.out.println(this.disponivel.get(i).validate()?this.opcoes.get(i):"\u001B[31mTemporariamente Indisponível\u001B[0m");
            }
            System.out.println("\033[1;36m"+"\n0 - Sair"+ "\033[0m");
        }
        else{
            for (int i=0; i<(this.opcoes.size()-1); i++) {
                System.out.print("\033[1;36m" + (i+1) + "\033[0m");
                System.out.print("\033[1;36m"+" - "+"\033[0m");
                System.out.println(this.disponivel.get(i).validate()?this.opcoes.get(i):"\u001B[31mTemporariamente Indisponível\u001B[0m");
            }
            System.out.println("\033[1;36m"+"\n0 - Logout"+ "\033[0m");
        }
        System.out.println("\033[1;36m"+ "***************************************"+ "\033[0m");
    }

    /** Ler uma opção válida */
    private int readOption() {
        //Scanner is = new Scanner(System.in);

        System.out.print("\nOpção: ");
        try {
            String line = is.nextLine();
            op = Integer.parseInt(line);
        }
        catch (NumberFormatException e) { // Não foi inscrito um int
            op = -1;
        }
        if (op<0 || op>this.opcoes.size()) {
            System.out.println("\u001B[31mOpção Inválida!!\u001B[0m");
            op = -1;
        }
        return op;
    }

    public void printTituloTabelaMapa() {
        System.out.println("******************* "+"\033[4;37m"+"MAPA"+"\033[0m"+" *********************");
        System.out.println("|  "+"\033[1;36m"+"X"+"\033[0m"+"  |  "+"\033[1;36m"+"Y"+"\033[0m"+
                "  |   "+"\033[1;36m"+"Doentes"+"\033[0m"+"   |    "+"\033[1;36m"+"Não Doentes"+"\033[0m"+"   |");
        System.out.println("**********************************************");
    }

    public void printTabelaMapa(List<String[]> tabela) {
        for (String[] linha : tabela) {
            System.out.println("|  " + linha[0] +"  |  " + linha[1] +"  |" +
                    "      " + linha[2] +"      |" + "         " + linha[3] +"        |");
        }
        System.out.println("**********************************************");
    }

    public void printExcecao(String msg) {
        System.out.println("\033[1;31m" + msg + "\033[0m");
    }

    public void setOp(int op) {
        this.op = op;
    }
}
