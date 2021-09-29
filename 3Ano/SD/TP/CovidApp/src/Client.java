import ClientUI.*;
import Exceptions.VerificandoVazia;
import java.io.IOException;
import java.net.Socket;
import java.util.*;


public class Client {
    private static Scanner scin;
    private static Demultiplexer m;
    private static String userID;
    private static int especialUser;
    private static Menu menu;
    private static Thread aviso;
    private static boolean procuraVazia = false;


    public static void run() throws IOException, InterruptedException {
        System.out.println("\033[1;35mBem vindo ao CovidApp!\033[0m");
        aviso.start();
        menuPrincipal();
        aviso.interrupt();
        m.close();
        System.out.println("\033[1;36m"+"Adeus!"+"\033[0m");
    }

    private static void menuPrincipal() throws InterruptedException {
        menu = new Menu(new String[]{
                "Registar",
                "Login"});

        menu.setHandler(1, Client::efetuarRegisto);
        menu.setHandler(2, Client::efetuarLogin);
        menu.run(0);
    }

    private static void userMenu() throws InterruptedException {
        Menu menu = new Menu(new String[]{
                "Registar Contaminação",
                "Verificar Nº de Pessoas numa Localização",
                "Descarregar Mapa",
                "Informar Localização Vazia",
                "Deslocar para nova Localização",
                "Logout"
        });

        menu.setPreCondition(3, ()->isSpecial());
        // Registar os handlers
        menu.setHandler(1, Client::registarContaminacao);
        menu.setHandler(2, Client::numPessoasLocal);
        menu.setHandler(3, Client::specialUser);
        menu.setHandler(4, Client::getLocalVazio);
        menu.setHandler(5, Client::novoLocal);
        menu.setHandler(6, Client::logout);
        menu.run(1);

    }

    static boolean isSpecial(){
        return especialUser == 1;
    }

    static boolean notProcuraVazia() throws VerificandoVazia {
        if (procuraVazia) throw  new VerificandoVazia("Verificação de uma localização vazia ainda em andamento...");
        return true;
    }

    static void logout() throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                aviso.interrupt();
                m.close();
                //s.close();
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        t.start();
        t.join();
    }

    /**
     * Quando um cliente regista-se tem de dar as suas informações para login.
     * Além disso, necessita também de informar sobre a sua localização actual.
     * É importante referir que no caso do login assume-se que ele se encontra na última posição guardada no programa
     */
    static void efetuarRegisto() throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                especialUser=-1;
                String es = "";
                while (especialUser == -1){
                    System.out.println("É um utilizador especial? ");
                    System.out.println("(Y)Sim                  (N)Não");
                    es = scin.nextLine();
                    es = es.toUpperCase(Locale.ROOT);
                    if (es.equals("Y")) especialUser = 1;
                    else
                    if (es.equals("N")) especialUser = 0;
                    else System.out.println("\033[1;31m" + "Opção incorreta!" + "\033[0m");
                }
                System.out.println("Insira um nome de Utilizador: ");
                String user = scin.nextLine();
                System.out.println("Inserir password: ");
                String pass = scin.nextLine();

                System.out.println("\033[1;36m"+"» Insira a localização onde se encontra. " +"\033[0m");
                System.out.println("Insira a coordenada x: ");
                int x = scin.nextInt();
                System.out.println("Insira a coordenada y: ");
                int y = scin.nextInt();

                m.send(1, (user+" "+pass+" "+es+" "+x+" "+y+" ").getBytes());
                byte[] b = m.receive(1);
                int excecao = Integer.parseInt(new String(b));
                byte[] b1 = m.receive(1);
                //userID = user;
                if (excecao==0){
                    System.out.println("\033[1;36m"+ new String(b1)+"\033[0m");
                    userID = user;
                    userMenu();
                }
                else menu.printExcecao(new String(b1) + "\nRegisto não efetuado.");

            } catch (NullPointerException | IOException | InterruptedException e) {
                menu.printExcecao(e.getMessage());
            }
            catch (InputMismatchException valIncorretos) {
                menu.printExcecao("Valor não é um número inteiro!");
            }
        });
        t.start();
        t.join();
    }

    static void efetuarLogin() throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                System.out.println("Insira nome de Utilizador: ");
                String user = scin.nextLine();
                System.out.println("Inserir password: ");
                String pass = scin.nextLine();
                m.send(2, (user+" "+pass+" ").getBytes());

                // get replies
                byte[] b = m.receive(2);
                int excecao = Integer.parseInt(new String(b));
                byte[] b1 = m.receive(2);
                if (excecao==0) {
                    String[] tokens = new String(b1).split(":");
                    System.out.println("\033[1;36m"+ tokens[0] +"\033[0m");
                    System.out.println(tokens[1]);
                    especialUser = Integer.parseInt(tokens[1]);
                    userID = user;
                    userMenu();
                }
                else menu.printExcecao(new String(b1) + "\nFalha na autenticação.");

            } catch (NullPointerException | IOException | InterruptedException e) {
                menu.printExcecao(e.getMessage());
            }
        });
        t.start();
        t.join();

    }

    static void registarContaminacao() throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                m.send(0,userID.getBytes());
                m.close();
                System.out.println("\033[1;31m" + "Em Isolamento..."+ "\033[0m");
                System.exit(0);
            } catch (NullPointerException | IOException e) {
                menu.printExcecao(e.getMessage());
            }
        });
        t.start();
        t.join();
    }

    static void numPessoasLocal() throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                System.out.println("Insira a coordenada x: ");
                int x = scin.nextInt();
                System.out.println("Insira a coordenada y: ");
                int y = scin.nextInt();

                m.send(3, (x+" "+y+" ").getBytes());

                byte[] b = m.receive(3);
                int excecao = Integer.parseInt(new String(b));
                byte[] b1 = m.receive(3);
                if (excecao == 0) System.out.println("\033[1;36m» O número de pessoas é: " +"\033[0m"+ new String(b1));
                else menu.printExcecao(new String(b1));

            } catch (NullPointerException | IOException | InterruptedException e) {
                menu.printExcecao(e.getMessage());
            }
            catch (InputMismatchException valIncorretos) {
                menu.printExcecao("Valor não é um número inteiro!");
            }
        });
        t.start();
        t.join();
    }

    public static void specialUser() throws InterruptedException{
        Thread t = new Thread(() -> {
            try {
                m.send(5,userID.getBytes());
                byte[] b1 = m.receive(5);
                int size = Integer.parseInt(new String(b1));
                menu.printTituloTabelaMapa();
                List<String[]> tabela = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    byte[] b2 = m.receive(5);
                    String[] tokens = new String(b2).split(" ");
                    tabela.add(tokens);
                }
                menu.printTabelaMapa(tabela);
            } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
            }
        });
        t.start();
        t.join();
    }

    public static void getLocalVazio() throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                notProcuraVazia();
                System.out.println("Insira uma localização. ");
                System.out.println("Insira a coordenada x: ");
                int x = scin.nextInt();
                System.out.println("Insira a coordenada y: ");
                int y = scin.nextInt();

                m.send(4, (x + " " + y + " " + userID + " ").getBytes());

                byte[] b = m.receive(4);
                int excecao = Integer.parseInt(new String(b));
                byte[] b1 = m.receive(4);
                if (excecao == 0) {
                    System.out.println("\033[1;36mLocal Guardado: " + "\033[0m" + new String(b1));
                    procuraVazia = true;
                    Thread t2 = new Thread(() -> {
                        try {
                            byte[] b2 = m.receive(4);
                            System.out.println("\n\033[1;36m» Local Vazio: " + "\033[0m" + new String(b2));
                            procuraVazia = false;
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    t2.start();
                }
                else {
                    menu.printExcecao(new String(b1));
                }
            } catch (NullPointerException | IOException | InterruptedException | VerificandoVazia e) {
                menu.printExcecao(e.getMessage());
            } catch (InputMismatchException valIncorretos) {
                menu.printExcecao("Valor não é um número inteiro!");
            }

        });
        t.start();
        t.join();
    }

    public static void novoLocal() throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                System.out.println("Insira uma localização. ");
                System.out.println("Insira a coordenada x: ");
                int x = scin.nextInt();
                System.out.println("Insira a coordenada y: ");
                int y = scin.nextInt();

                m.send(7, (userID+ " "+x + " " + y + " ").getBytes());

                byte[] b = m.receive(7);
                int excecao = Integer.parseInt(new String(b));
                byte[] b1 = m.receive(7);
                if (excecao == 0) System.out.println("\033[1;36m" + new String(b1) + "\033[0m" );
                else menu.printExcecao(new String(b1));

            } catch (IOException | InterruptedException e) {
                menu.printExcecao(e.getMessage());
            }
            catch (InputMismatchException valIncorretos) {
                menu.printExcecao("Valor não é um número inteiro!");
            }
        });
        t.start();
        t.join();
    }


    public static void main(String[] args) throws Exception {
        scin = new Scanner(System.in);
        Socket s = new Socket("localhost", 12343);
        m = new Demultiplexer(new TaggedConnection(s));
        aviso = new Thread(() -> {
            try {
                while (true) {
                    byte[] b2 = m.receive(6);
                    System.out.println("\n\033[1;31m» " + new String(b2) + "\033[0m");
                }
            } catch (IOException | InterruptedException e) {

            }
        });
        m.start();
        run();

    }
}
