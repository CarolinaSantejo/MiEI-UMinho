package UI;

import Exceptions.*;
import GestPaletes.*;
import GestRobot.*;


import java.util.*;


public class TextUI {
    // O model tem a 'lógica de negócio'.
    private IGestPaletesFacade modelP;
    private IGestRobotFacade modelR;

    // Scanner para leitura
    private Scanner scin;



    /**
     * Construtor.
     *
     * Cria os menus e a camada de negócio.
     */
    public TextUI() {

        this.modelP = new GestPaletesFacade();
        this.modelR = new GestRobotFacade();
        scin = new Scanner(System.in);
    }

    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */
    public void run() {
        System.out.println("\033[1;35mBem vindo ao Sistema do Armazém!\033[0m");
        this.menuPrincipal();
        System.out.println("\033[1;36m"+"Adeus!"+"\033[0m");
    }

    // Métodos auxiliares - Estados da UI

    /**
     * Estado - Menu Principal
     */
    private void menuPrincipal() {
        Menu menu = new Menu(new String[]{
                "Aceder à Gestão de Paletes",
                "Notificações de Robots"});

    // Registar pré-condições das transições
        //menu.setPreCondition(3, ()->this.modelP.haPaletes() && this.modelR.haRobots());

    // Registar os handlers
        menu.setHandler(1, ()->gestaoDePaletes());
        menu.setHandler(2, ()->gestaoDeRobots());
        //menu.setHandler(3, ()->comunicaOrdemTransporte());

        menu.run();
    }

    /**
     *  Estado - Gestão de Paletes
     */
    private void gestaoDePaletes() {
        Menu menu = new Menu(new String[]{
                "Registar nova palete",
                "Consultar listagem de localizações"
        });

        // Registar pré-condições das transições
        menu.setPreCondition(2, ()->this.modelP.haPaletes() && this.modelR.haRobots());

        // Registar os handlers
        menu.setHandler(1, this::comunicaCodigoQR);
        menu.setHandler(2, this::consultaListagem);

        menu.run();
    }

    /**
     *  Estado - Gestão de Robots
     */
    private void gestaoDeRobots() {
        Menu menu = new Menu(new String[]{
                "Notificar Recolha de Palete",
                "Notificar Entrega de Palete"
        });

        // Registar os handlers
        menu.setHandler(1, this::notificaRecolha);
        menu.setHandler(2, this::notificaEntrega);

        menu.run();
    }

    /**
     *  Estado - Comunica Código QR para o registo de uma nova palete
     */
    void comunicaCodigoQR(){
        try{
            System.out.println("Código QR lido: ");
            String qrCode = scin.nextLine();
            System.out.println("Inserir Código da Palete: ");
            String codPalete = scin.nextLine();
            this.modelP.registaPalete(qrCode,codPalete, modelR.getVerticeZonaDescarga());
            System.out.println("\033[1;36m*\033[0m"+"Registo Concluído!!!");
            System.out.println("\033[1;36m*\033[0m"+"Requisitando transporte...");
            comunicaOrdemTransporte(codPalete);
        }
        catch (QRCodeInvalido | PaleteJaExiste | NullPointerException e) {
            System.out.println("\033[1;31m" + e.getMessage() +"\033[0m");
        }
    }

    /**
     *  Estado - Comunica Ordem de Transporte
     */
    void comunicaOrdemTransporte(String codPalete){
        //considerando que a origem é onde a palete está
        // temos de calcular a rota até à origem (locRobot -> origem) e depois dentro do metodo calcula rota chamamos o
        // alteraRota para dar a rota ao robot
        try {
            String codRobot = modelR.getRobotDisponivel();
            modelR.validaRobot(codRobot);
            String destino = modelR.getPrateleiraLivre();
            modelR.atualizaOcupacaoVertice(destino,-1);
            modelR.notifica_transporte(codRobot,codPalete,modelP.getLocalizacaoPalete(codPalete));
            modelP.atualiza_localizacaoPalete(codPalete,null,2);
            modelR.indica_destino(codRobot,destino);
            System.out.println("\nO Robot " + "\033[1;35m" + codRobot + "\033[0m" + " foi notificado para transportar a palete "+ "\033[1;35m" + codPalete + "\033[0m" + "!! Em breve irá começar o seu percurso.");
            modelR.calcula_rota(codRobot,0);
            System.out.println("\033[1;35m"+ codRobot+ "\033[0m" + ": A Iniciar Percurso...");
            List<String> lis = modelR.getCaminho(codRobot);
            //Collections.reverse(lis);
            System.out.print("\n\033[1;37m"+ "Percurso: " + "\033[0m" );
            System.out.print(lis.get(0));
            for(int i=1 ; i<lis.size(); i++){
                System.out.print(" ⇒ ");
                System.out.print(lis.get(i));
            }
        }
        catch (EspacoInsuficienteNoArmazem | NullPointerException | RobotsNaoDisponiveis | OrigemIgualDestino | RobotNaoExiste e){
            System.out.println("\033[1;31m" + e.getMessage() +"\033[0m");
        }
    }

    /**
     *  Estado - Notifica Recolha da Palete da sua origem
     */

    void notificaRecolha(){
        try{
            System.out.println("Código Robot: ");
            String codRobot = scin.nextLine();
            modelR.validaRobot(codRobot);
            modelR.atualiza_LocalizacaoRobot(codRobot,0); //atualiza a localizacao para a origem (sitio da palete)
            String codPalete = modelR.getPaleteDoRobot(codRobot);
            modelP.atualiza_localizacaoPalete(codPalete,null,1);
            System.out.println("\033[1;35m" + codRobot + "\033[0m" + ": Recolhi a Palete " + "\033[1;35m" + codPalete + "\033[0m");
            modelR.calcula_rota(codRobot,1);
            System.out.println("\033[1;35m" + codRobot + "\033[0m" + ": A Iniciar Percurso...");
            List<String> lis = modelR.getCaminho(codRobot);
            System.out.print("\n\033[1;37m"+ "Percurso: " + "\033[0m" );
            System.out.print(lis.get(0));
            for(int i=1 ; i<lis.size(); i++){
                System.out.print(" ⇒ ");
                System.out.print(lis.get(i));
            }
        }
        catch (RotaNull | NullPointerException | OrigemIgualDestino | RobotNaoExiste re) {
            System.out.println("\033[1;31m" + re.getMessage() +"\033[0m");
        }
    }

    /**
     *  Estado - Notifica Entrega da Palete ao destino
     */

    void notificaEntrega(){
        try{
            System.out.println("Código Robot: ");
            String codRobot = scin.nextLine();
            modelR.validaRobot(codRobot);
            //Vai buscar a palete que o robot transporta
            String codPalete = modelR.getPaleteDoRobot(codRobot);
            try {
                modelP.validaEstadoInRobot(codPalete, 1);
                //Atualiza Localização do Robot
                modelR.atualiza_LocalizacaoRobot(codRobot, 1);
                //Atualiza Localizacao da Palete
                modelP.atualiza_localizacaoPalete(codPalete, modelR.getLocalizacaoRobot(codRobot), -1);
                System.out.println("\033[1;35m" + codRobot + "\033[0m" + ": Entreguei a Palete " + "\033[1;35m" + codPalete + "\033[0m");
                //Atualiza Estado do Robot
                modelR.atualiza_estadoRobot(codRobot, 0);
                //Atualiza Estado do Vertice
                modelR.atualizaOcupacaoVertice(modelP.getLocalizacaoPalete(codPalete).getCodVertice(), 1);
                //Retira Rota do Robot
                modelR.alteraRota(codRobot, null);
                System.out.println("\033[1;35m" + codRobot + "\033[0m" + ": Estou Disponível.");
                String paleteZonaD = modelP.paleteZonaD();
                System.out.println("\033[1;36m*\033[0m"+"Ainda existem paletes na Zona de Descarga!");
                System.out.println("\033[1;35m" + codRobot + "\033[0m" + ": Nova Tarefa.");
                comunicaOrdemTransporte(paleteZonaD);
            }catch (NaoLevantouPalete e){
                System.out.println("\033[1;31m" + e.getMessage() +"\033[0m");
            }

        }
        catch (NullPointerException | RotaNull | SemPaletesParaTransportar | RobotNaoExiste e) {
            System.out.println("\033[1;31m" + e.getMessage() +"\033[0m");
        }
    }

    /**
     *  Estado - Consulta Listagem da Localização das Paletes
     */

    void consultaListagem(){
        try {
            Map<String,String> resP = this.modelP.disponibiliza_listagem();
            Map<String,String> resPR = modelR.listagemPaletesInRobot(modelP.getPaletesEmRobots()); //lista das paletes que estão nos robots
            Tabela tabela = new Tabela("Listagem das Localizações das Paletes",new String[]{
                    "Código da Palete", "Localização"},2,resP);
            tabela.printaLinha("");
            tabela.printaTitulo();
            tabela.printaLinha("");
            tabela.printaSubtitulos();
            tabela.printaLinha("");
            tabela.printRes();
            tabela.setRes(resPR);
            tabela.printRes();
            tabela.printaLinha("");
            tabela.menu(0);
            tabela.run();

        }
        catch (NullPointerException e) {
            System.out.println("\033[1;31m" + e.getMessage() +"\033[0m");
        }
    }
}
