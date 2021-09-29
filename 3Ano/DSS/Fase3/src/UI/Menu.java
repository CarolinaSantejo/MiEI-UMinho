package UI;

import java.util.*;

/**
 * Esta classe implementa um menu em modo texto.
 *
 * @author José Creissac Campos
 * @version v3.2 (20201215)
 */
public class Menu {

    // Interfaces auxiliares

    /** Functional interface para handlers. */
    public interface MenuHandler {
        void execute();
    }

    /** Functional interface para pré-condições. */
    public interface MenuPreCondition {
        boolean validate();
    }

    // Varíável de classe para suportar leitura

    private static Scanner is = new Scanner(System.in);

    // Variáveis de instância

    private List<String> opcoes;                // Lista de opções
    private List<MenuPreCondition> disponivel;  // Lista de pré-condições
    private List<MenuHandler> handlers;         // Lista de handlers

    // Construtor

    /**
     * Constructor for objects of class Menu
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
    public void run() {
        int op;
        do {
            show();
            op = readOption();
            // testar pré-condição
            if (op>0 && !this.disponivel.get(op-1).validate()) {
                System.out.println("Opção indisponível! Tente novamente.");
            } else if (op>0) {
                // executar handler
                this.handlers.get(op-1).execute();
            }
        } while (op != 0);
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
    private void show() {
        System.out.println("\033[1;36m"+ "\n**************** Menu Sistema ****************"+ "\033[0m");
        for (int i=0; i<this.opcoes.size(); i++) {
            System.out.print("\033[1;33m" + (i+1) + "\033[0m");
            System.out.print("\033[1;33m"+" - "+"\033[0m");
            System.out.println(this.disponivel.get(i).validate()?this.opcoes.get(i):"\u001B[31mTemporariamente Indisponível\u001B[0m");
        }
        System.out.println("\033[1;33m"+"\n0 - Sair"+ "\033[0m");
        System.out.println("\033[1;36m"+ "**********************************************"+ "\033[0m");
    }

    /** Ler uma opção válida */
    private int readOption() {
        int op;
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
}
