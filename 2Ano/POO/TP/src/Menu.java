import java.util.AbstractMap;
import java.util.Scanner;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Menu {
    public static int MenuInicial() {
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------MENU INICIAL-----------\n\n");
        sb.append("1) Iniciar sess�o.\n");
        sb.append("2) Registar nova conta.\n");
        sb.append("3) Carregar logs.\n");
        sb.append("4) Salvar Estado.\n");
        sb.append("5) Carregar Estado.\n");
        sb.append("6) Utilizadores que mais utilizam o sistema (em n�mero de encomendas transportadas).\n");
        sb.append("7) Empresas transportadoras que mais utilizam o sistema (em n�mero de kms percorridos).\n");
        sb.append("0) Sair.\n\n");
        sb.append("Selecione a op��o pretendida: ");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    
    public static String pressEnter(){
        System.out.println("Pressione qualquer tecla para continuar...");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    //double to TimeString
    public static String time(double d){
        int hour = (int) d;
        int min = (int)((d-hour)*60);
        return (hour+":"+ min+" H");
    }
    
    //Tentar encontrar outra forma mais elegante
    public static void clearWindow() {
        
        for (int i = 0;i<100;i++){
            System.out.println();
        }
    }
    
    public static int menuUtilizador(){
        StringBuilder sb = new StringBuilder("-----------MENU UTILIZADOR-----------\n\n");
        sb.append("1) Efetuar uma compra.\n");
        sb.append("2) Solicitar entrega de uma encomenda.\n");
        sb.append("3) Verificar ofertas de transportadora.\n");
        sb.append("4) Hist�rico de encomendas [Por entidade de transporte e per�odo de tempo].\n");
        sb.append("5) Classificar volunt�rio ou transportadora.\n");
        sb.append("6) N�mero de encomendas transportadas.\n");
        sb.append("0) Logout.\n\n");
        sb.append("Selecione a op��o pretendida: ");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    
    public static void maisFreqT(List<AbstractMap.SimpleEntry<String, Double>> l) {
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------Entidades que utilizam mais o sistema-----------\n\n");
        for (AbstractMap.SimpleEntry<String, Double> m : l) {
            String result = String.format("%.2f", m.getValue());
            sb.append(m.getKey()).append("----->").append(result).append("Km percorridos\n");
        }
        System.out.println(sb.toString());
    }
    
    public static void maisFreqU(List<AbstractMap.SimpleEntry<String, Integer>> l) {
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------Utilizadores que mais utilizam mais o sistema-----------\n\n");
        for (AbstractMap.SimpleEntry<String, Integer> m : l) sb.append(m.getKey()+"----->"+m.getValue()+"encomendas transportadas\n");
        System.out.println(sb.toString());
    }
    
    public static String userMenuData(int i){
        StringBuilder sb = new StringBuilder("----------MENU UTILIZADOR-----------\n\n");
        if (i==1)sb.append("Digite um c�digo da entidade:\n ");
        else if (i==2) sb.append("Digite a sua avalia��o(1-10):\n ");
        else if (i==3) sb.append("Digite o c�digo de uma loja:\n ");
        else if (i==4) sb.append("Digite o n�mero de linhas de encomenda:\n ");
        else if (i==5) sb.append("Digite o c�digo de produto:\n ");
        else if (i==6) sb.append("Digite a descri��o:\n ");
        else if (i==7) sb.append("Digite a quantidade (se for decimal separe ponto):\n ");
        else if (i==8) sb.append("Digite o pre�o (se for decimal separe ponto):\n ");
        //else if (i==9) sb.append("Digite o peso:\n ");
        else if (i==10) sb.append("Digite um c�digo de encomenda:\n ");
        else if (i==11) sb.append("Encomenda m�dica? (S/N):\n ");
        else if (i==12) sb.append("Digite a velocidade m�dia:\n");
        
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
    public static void userMenuResult(int i, String res){
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------MENU UTILIZADOR-----------\n\n");
        if (i==1) sb.append("Encomenda ").append(res).append(" solicitada com sucesso!\n"); 
        if (i==2) sb.append("Encomendas por solicitar:\n").append(res).append("\n");
        if (i==3) sb.append("N�mero de encomendas transportadas: ").append(res).append("\n");
        sb.append("Pressione enter para continuar...");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
       
    }
    
    public static int menuVoluntario(boolean disp){
        StringBuilder sb = new StringBuilder("-----------MENU VOLUNT�RIO-----------\n\n");
        sb.append("1) Selecionar encomenda a transportar.\n");
        sb.append("2) Registar entrega de uma encomenda.\n");
        sb.append("3) Verificar classifica��o atual.\n");
        sb.append("4) Hist�rico de encomendas entregues.\n");
        sb.append("0) Logout.\n\n");
        sb.append("Selecione a op��o pretendida: ");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    
    public static String voluntarioMenuData(int i){
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------MENU VOLUNT�RIO-----------\n\n");
        if (i==2) sb.append("Digite um c�digo de encomenda:\n ");
        else if (i==3) sb.append("Digite o c�digo de uma loja:\n ");
        
        
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
    public static void voluntarioMenuResult(int i, String res){
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------MENU VOLUNT�RIO-----------\n\n");
        if (i==1) sb.append("O voluntario esta agora ").append(res).append("\n");
        else if (i==2) sb.append("Encomenda ").append(res).append(" aceite com sucesso!\n ");
        else if (i==3) sb.append("Entrega registada com sucesso!\n").append("Tempo de transporte: ").append(res).append("\n");
        else if (i==4) sb.append("Sua classificacao atual: ").append(res).append("\n ");
        sb.append("Pressione enter para continuar...");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        clearWindow();
    }
    
    public static int volEncInfo(Map<String,Double> encInfo) {
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------MENU VOLUNT�RIO-----------\n\n");
        sb.append("Cod Encomenda------Dura��o Transporte\n");
        for (Map.Entry<String,Double> me : encInfo.entrySet())
        sb.append("  ").append(me.getKey()).append("                ").append(time(me.getValue())).append("\n");
        sb.append("1) Escolher encomenda a transportar;\n0) Retroceder;\nSelecione a op��o pretendida: ");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    
    public static int menuTransportadora(boolean disp){
        StringBuilder sb = new StringBuilder("-----------MENU TRANSPORTADORA-----------\n\n");
        sb.append("1) Pre�o de transporte de uma encomenda.\n");
        sb.append("2) Entregar encomendas.\n");
        sb.append("3) Total faturado num determinado per�odo de tempo.\n");
        sb.append("4) Pedir para efetuar transporte de encomenda.\n");
        sb.append("5) Verificar a sua classifica��o atual.\n");
        sb.append("6) Hist�rico de encomendas transportadas.\n");
        sb.append("0) Logout.\n\n");
        sb.append("Selecione a op��o pretendida: ");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    
    public static String transportadoraMenuData(int i,String info){
        StringBuilder sb = new StringBuilder("-----------MENU TRANSPORTADORA-----------\n\n");
        if (i==1 && info!=null) {
            sb.append("Encomendas poss�veis de serem transportadas pela entidade: "+info+"\n");
            sb.append("Digite um c�digo de encomenda:  ");
        }
        else if (i==1) sb.append("Digite um c�digo de encomenda:  ");
        else if (i==2) sb.append("Digite o c�digo de uma loja:  ");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
    public static String LojaMenuData(int i){
        StringBuilder sb = new StringBuilder("-----------MENU LOJA-----------\n\n");
        if (i==1 ) sb.append("Digite um c�digo de encomenda: ");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
    public static void LojaMenuRes(int i,String res){
        StringBuilder sb = new StringBuilder("-----------MENU LOJA-----------\n\n");
        if (i==1 ) sb.append("Tempo de espera na fila ser�, em principio: "+res+" min");
        System.out.println(sb.toString());
    }
    
    public static void transportadoraMenuResult(int i, String res) {
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------MENU TRANSPORTADORA-----------\n\n");
        if (i==1) sb.append("Pre�o de transporte da encomenda: ").append(res).append("\n ");
        if (i==2) sb.append("Pedido de transporte da encomenda ").append(res).append(" efetuado com sucesso!\n ");
        else if (i==3) sb.append("Entrega registada com sucesso!\n").append("Tempo de transporte e custo: ").append(res).append("\n");
        else if (i==4) sb.append("Total faturado no per�odo indicado: ").append(res).append("\n");
        else if (i==5) sb.append("A sua classifica��o atual: ").append(res).append("\n");
        sb.append("Pressione enter para continuar...");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        clearWindow();
    }
    
     /*public static void TranspInfo(String s) {
        clearWindow();
        StringBuilder sb = new StringBuilder("");
        sb.append("Encomendas passiveis de serem transportadas pela entidade: "+s+"\n");
        System.out.println(sb.toString());
    }*/
    
    
   
    
    public static void mostrarTabelaTransportadora(Map<String,AbstractMap.SimpleEntry<Double, Double>> map){
        StringBuilder sb = new StringBuilder("-----------MENU TRANSPORTADORA-----------\n\n");
        sb.append("\n*****Encomendas Entregues!!*****\n");
        sb.append("Cod Encomenda------Dura��o Transporte------Custo Transporte\n\n");
        for (Map.Entry<String,AbstractMap.SimpleEntry<Double, Double>> me :map.entrySet()){
            String result = String.format("%.2f", me.getValue().getValue());
            sb.append("   "+me.getKey()+"                 "+time(me.getValue().getKey())+"            "+result+"\n");
        }
        sb.append("Pressione enter para continuar...");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        clearWindow();
    }
    
    public static int transpEncInfo(Map<String,List<Double>> encInfo) {
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------MENU TRANSPORTADORA-----------\n\n");
        sb.append("***O tempo na fila � relativo a este instante e pode variar conforme haja atrasos na loja. *****\n");
        sb.append("***Estes custos podem variar conforme a rota que a entidade escolha*****\n");
        sb.append("Cod Encomenda----Distancia-----------Tempo na Fila-----Custo Transporte\n\n");
        for (Map.Entry<String,List<Double>> me :encInfo.entrySet()){
            String result = String.format("%.2f", me.getValue().get(0));
            String result1 = String.format("%.2f", me.getValue().get(2));
            sb.append("   "+me.getKey()+"             "+result+"        "+time(me.getValue().get(1))+"             "+result1+"\n");
        }
        sb.append("1) Escolher encomenda a transportar;\n0) Retroceder;\nSelecione a op��o pretendida: ");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
     
    // devolve encomenda do utilizador associada a uma lista de "pares" em que cada par contem
    //a transportadora que quer transportar, o custo e o tempo de transporte
     public static int apresentaPedidosTransportes(Map<String, List<AbstractMap.SimpleEntry<String, ArrayList <Double>>>> transp){
        StringBuilder sb = new StringBuilder("----------MENU UTILIZADOR-----------\n\n");
        sb.append("***Estes custos podem variar conforme a rota que a entidade escolha*****\n");
        sb.append("***O tempo de transporte global pode variar caso haja atrasos na loja, ou atrasos no deslocamento da entidade *****\n");
        sb.append("Cod Encomenda------Transportadora------Custo Transporte----------Tempo de deslocamento(apenas)\n");
        for (Map.Entry<String, List<AbstractMap.SimpleEntry<String, ArrayList <Double>>>> me : transp.entrySet()){
            for (AbstractMap.SimpleEntry<String, ArrayList <Double>> a : me.getValue()){
            String result = String.format("%.2f", a.getValue().get(0));
            sb.append("   "+me.getKey()+"|         "+a.getKey()+"|          "+result+"|          "+time(a.getValue().get(1))+"\n");
        }
            
        }
        sb.append("1) Escolher uma das op��es:\n ");
        sb.append("0) Nenhuma das op��es:\n ");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
        
    }
     
    public static int dataInfo(int i,boolean b){
       StringBuilder sb = new StringBuilder();
       if (b) sb.append("*****Data de in�cio*****\n ");
       else sb.append("*****Data de fim*****\n ");
        if (i==1) sb.append("Digite o ano: ");
        else if (i==2) sb.append("Digite o m�s: ");
        else if (i==3) sb.append("Digite o dia: ");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
     
     
    public static void printHistorico(List<Encomenda>me,String entidade,LocalDateTime i,LocalDateTime f) {
        StringBuilder sb = new StringBuilder("-----------Hist�rico de Encomendas-----------\n\n");
        sb.append("\n********Entidade "+entidade+"********\n");
        sb.append("\nEncomendas entre "+i.toString()+" e "+f.toString()+"\n");
        for (Encomenda e : me){
            sb.append(e.toString());
            sb.append("\n------//-------\n");
        }
        System.out.println(sb.toString());
        
        
    }

    public static int menuLoja(){
        StringBuilder sb = new StringBuilder("-----------MENU LOJA-----------\n\n");
        sb.append("1) Ver lista de encomendas.\n");
        sb.append("2) Ver quanto falta para a sua encomenda ficar dispon�vel.\n");
        sb.append("3) Despachar encomenda.\n");
        sb.append("0) Logout.\n");
        sb.append("Selecione a op��o pretendida: ");
        System.out.println(sb.toString());
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }


    
    public static String getEmail (boolean b){
        if (b) System.out.println("O email digitado j� existe. Tente novamente. "); 
        System.out.print("Digite um e-mail de registo: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
    public static String getGeneralContaInfo(int i){
        if (i==1) System.out.print("Digite uma password: ");
        else if (i==2) System.out.print("Digite o seu nome: ");
        else if (i==3) System.out.print("Digite a sua coordenada em x: ");
        else if (i==4) System.out.print("Digite a sua coordenada em y: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    public static String getSpecificContaInfo(int i){
        if (i==1) System.out.print("Raio de A��o: ");
        else if (i==2) System.out.print("N�mero m�ximo de encomendas a transportar: ");
        else if (i==3) System.out.print("NIF da transportadora: ");
        else if (i==4) System.out.print("Pre�o por KM: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
    

    public static TipoConta menuRegisto() {
        StringBuilder sb = new StringBuilder();
        sb.append("------------REGISTAR NOVA CONTA------------").append("\n");
        sb.append("Como se deseja registar?\n\n");
        sb.append("1) Utilizador\n");
        sb.append("2) Volunt�rio\n");
        sb.append("3) Loja\n");
        sb.append("4) Transportadora\n\n");
        sb.append("Introduza a op��o pretendida: ");

        System.out.println(sb.toString());

        Scanner scanner = new Scanner(System.in);
        int op��o = scanner.nextInt();

        switch(op��o) {
            case 1: return TipoConta.Utilizador;
            case 2: return TipoConta.Voluntario;
            case 3: return TipoConta.Loja;
            case 4: return TipoConta.Transportadora;
            default: return null; 
        }
    }

    public static AbstractMap.SimpleEntry<String,String> menuLogin(boolean errorMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append("------------INICIAR SESSAO---------").append("\n\n");
        if(errorMessage) sb.append("Erro - Dados inv�lidos! Tente novamente!\n\n");
        sb.append("Introduza os seus dados.\n\n");
        sb.append("Endere�o de e-mail: ");

        System.out.print(sb.toString());

        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine();

        System.out.print("Palavra-passe: ");

        String password = scanner.nextLine();
        return new AbstractMap.SimpleEntry<>(email, password);
    }
    
    public static void errors(int i){
         StringBuilder sb = new StringBuilder();
        if (i==1) sb.append("****Ficheiro n�o encontrado***").append("\n");
        else if (i==2) sb.append("****N�o foi possivel guardar o Estado****").append("\n");
        else if (i==3) sb.append("****Erro ao ler para as estruturas de dados****").append("\n");
        else if (i==4) sb.append("****C�digo inv�lido****").append("\n");
        else if (i==5) sb.append("****Datas inv�lidas****").append("\n");
        else if (i==6) sb.append("****N�o foi possivel carregar o Estado****").append("\n");
        else if (i==7) sb.append("****Entidade indispon�vel****").append("\n");
        else if (i==8) sb.append("****N�o existe encomenda a transportar****").append("\n");
        else if (i==9) sb.append("****A encomenda n�o foi solicitada****").append("\n");
        else if (i==10) sb.append("****N�o existem encomendas a entregar pela transportadora em quest�o****").append("\n");
        else if (i==11) sb.append("****N�o existem encomendas para solicitar****").append("\n");
        else if (i==12) sb.append("****N�o existem encomendas para entregar pela entidade****").append("\n");
        else if (i==13) sb.append("****A entidade n�o pode transportar a encomenda selecionada****").append("\n");
        else if (i==14) sb.append("****N�o existem encomendas dentro dos requisitos obrigat�rios****").append("\n");
        else if (i==15) sb.append("****Nenhuma transportadora pediu para transportar encomendas suas****").append("\n");
        else if (i==16) sb.append("****Credenciais inv�lidas!****").append("\n");
        else if (i==17) sb.append("****Encomenda n�o se encontra na loja!****").append("\n");
        else if (i==18) sb.append("****\nEmail j� existente!****").append("\n");
        sb.append("\nPressione enter para continuar...");
        System.out.print(sb.toString());
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        clearWindow();
    }
    
    
    
    
    

    public static Utilizador menuRegistoUtilizador(String codigo) {
        Scanner scanner = new Scanner(System.in);

        StringBuilder sb = new StringBuilder("------------REGISTAR UTLIZADOR---------\n\n");
        sb.append("Introduza os dados a seguir pedidos.\n\n");
        sb.append("Nome: ");

        System.out.print(sb.toString());
        String nome = scanner.nextLine();

        System.out.print("Endere�o de e-mail: ");
        String email = scanner.nextLine();

        System.out.print("Palavra-passe: ");
        String password = scanner.nextLine();

        System.out.print("Coordenadas (separadas por um espa�o, em formato decimal(virgula)): ");
        Double x = scanner.nextDouble();
        Double y = scanner.nextDouble();
        
        System.out.print("Codigo atribuido a sua conta: "+codigo+"\n");

        return new Utilizador(codigo, nome, x , y, email, password);
    }

    public static Voluntario menuRegistoVoluntario(String codigo) {
        Scanner scanner = new Scanner(System.in);

        StringBuilder sb = new StringBuilder("------------REGISTAR VOLUNTARIO---------\n\n");
        sb.append("Introduza os dados a seguir pedidos.\n\n");
        sb.append("Nome: ");

        System.out.print(sb.toString());
        String nome = scanner.nextLine();

        System.out.print("Endere�o de e-mail: ");
        String email = scanner.nextLine();

        System.out.print("Palavra-passe: ");
        String password = scanner.nextLine();

        System.out.print("Coordenadas (separadas por um espaco, em formato decimal(virgula)): ");
        Double x = scanner.nextDouble();
        Double y = scanner.nextDouble();

        System.out.print("Raio de entrega: ");
        Double raio = scanner.nextDouble();
        
        System.out.print("Velocidade media: ");
        Double vel = scanner.nextDouble();
        
        System.out.print("Pode distribuir encomendas medicas?[S/N]: ");
        String med ="";
        while (!med.equals("S") && !med.equals("s") && !med.equals("n") && !med.equals("N")) med = scanner.nextLine();
        boolean b = (med.equals("s")||med.equals("S"));
        
        System.out.print("Codigo atribuido a sua conta: "+codigo+"\n");
        
        return new Voluntario(codigo, nome, x , y, raio, email, password, vel,b);
    }

    public static Loja menuRegistoLoja(String codigo) {
        Scanner scanner = new Scanner(System.in);

        StringBuilder sb = new StringBuilder("------------REGISTAR LOJA---------\n\n");
        sb.append("Introduza os dados a seguir pedidos.\n\n");
        sb.append("Nome da loja: ");

        System.out.print(sb.toString());
        String nome = scanner.nextLine();

        System.out.print("Endere�o de e-mail: ");
        String email = scanner.nextLine();

        System.out.print("Palavra-passe: ");
        String password = scanner.nextLine();

        System.out.print("Coordenadas (separadas por um espa�o, em formato decimal(virgula)): ");
        Double x = scanner.nextDouble();
        Double y = scanner.nextDouble();
        
        System.out.print("Tempo que cada pessoa demora a ser atendida(teoricamente), em minutos: ");
        Double time = scanner.nextDouble();
        Double tempo = time/60;
        
        System.out.print("Codigo atribuido a sua conta: "+codigo+"\n");
        
        return new Loja(codigo, nome, x , y, email, password,tempo);
    }

    public static Transportadora menuRegistoTransportadora(String codigo) {
        Scanner scanner = new Scanner(System.in);

        StringBuilder sb = new StringBuilder("------------REGISTAR TRANSPORTADORA---------\n\n");
        sb.append("Introduza os dados a seguir pedidos.\n\n");
        sb.append("Nome da transportadora: ");

        System.out.print(sb.toString());
        String nome = scanner.nextLine();

        System.out.print("Endere�o de e-mail: ");
        String email = scanner.nextLine();

        System.out.print("Palavra-passe: ");
        String password = scanner.nextLine();

        System.out.print("Coordenadas (separadas por um espa�o, em formato decimal): ");
        Double x = scanner.nextDouble();
        Double y = scanner.nextDouble();

        System.out.print("N�mero de Identifica��o Fiscal (NIF): ");
        String nif = scanner.next();

        System.out.print("Raio de entrega: ");
        Double raio = scanner.nextDouble();

        System.out.print("Pre�o por Km: ");
        Double precoPorKm = scanner.nextDouble();
        
        System.out.print("Pre�o por Kg: ");
        Double precoPorKg = scanner.nextDouble();

        System.out.print("Capacidade m�xima de entrega (0 equivale a capacidade ilimitada): ");
        int capacidadeMax = scanner.nextInt();
        if(capacidadeMax == 0) capacidadeMax = Integer.MAX_VALUE;
        
        System.out.print("Pode distribuir encomendas m�dicas?[S/N]: ");
        String med ="";
        while (!med.equals("S") && !med.equals("s") && !med.equals("n") && !med.equals("N")) med = scanner.nextLine();
        boolean b = (med.equals("s")||med.equals("S"));
        
        System.out.print("Velocidade m�dia: ");
        Double vel = scanner.nextDouble();
        
        System.out.print("C�digo atribu�do � sua conta: "+codigo+"\n");
        
        return new Transportadora(codigo, nome, x , y, nif, raio, precoPorKm, email, password, capacidadeMax, vel,b,precoPorKg);
    }

    
    
    
    public static void extraNotes(int i){
        if (i==1) System.out.println("\n*****O seu pedido de transporte foi adicionado! Aguarde aprova��o*****\n");
        if (i==2) System.out.println("\n****A entidade ainda n�o efetuou nenhuma entrega para si,logo nao pode avali�-la******\n");
        if (i==3) System.out.println("\n*****Combinacao Invalida*****\n");
        if (i==4) System.out.println("\n*****Encomenda j� despachada*****\n");
        if (i==5) System.out.println("\n*****Encomenda despachada com sucesso!*****\n");
        if (i==6) System.out.println("\n*****Nada a despachar!*****\n");
    }
    
    public static void showExtraInfo(List<String> t,int i){
        if (i==1) System.out.println("Lojas dispon�veis: "+t.toString());
        if (i==2) System.out.println("Entidades poss�veis de avaliar: \n"+t.toString());
        if (i==3) System.out.println("Encomendas na fila: \n"+t.toString());
        if (i==4) System.out.println("Encomendas despachadas: \n"+t.toString());
        
    }
    
    
}