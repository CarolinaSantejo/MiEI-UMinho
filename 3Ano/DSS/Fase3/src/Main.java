import UI.TextUI;

/**
 * @author Grupo 19
 */
public class Main {

    /**
     * O método main cria a aplicação e invoca o método run()
     */
    public static void main(String[] args) {
        try {
            new TextUI().run();
        }
        catch (Exception e) {
            System.out.println("Não foi possível arrancar: "+e.getMessage());
        }
    }


}
