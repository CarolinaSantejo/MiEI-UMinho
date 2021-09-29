import java.util.*;
import java.io.*;
/**
 * Escreva a descrição da classe Controller aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Controller
{
    private DriveIt drive;
    private Menu principal, first, opEsc;
    
    public static void main(String[] args) {
        new Controller().run();
    }
    
    private Controller() {
        String[] opcoes = { "Carregar DriveIt predefinido",
                            "Carregar de Ficheiro",
                            "Ver informacao do DriveIt",
                            "Existe veiculo?",
                            "Nº de veiculos",
                            "Veiculo por matricula", 
                            "Adiciona veiculo",
                            "Registar aluguer",
                            "Classificar veiculo",
                            "Custo de um veiculo por km",
                            "Veiculos que dao pontos",
                            "Guardar em Ficheiro" };
        String[] opcoes1 = { "Carregar DriveIt predefinido",
                            "Carregar de Ficheiro" };
        this.principal = new Menu(opcoes);      
        this.first = new Menu(opcoes1);
    }
    
    private void setMenuOp(int op) {
       switch (op) {
           case 2:
            String[] opcoes = {"Indique nome do ficheiro"};
            this.opEsc = new Menu(opcoes);
            break;
           case 4:
            String[] opcoes4 = {"Indique matricula do veiculo:"};
            this.opEsc = new Menu(opcoes4);
            break;
           case 5:
            String[] opcoes5 = {"Total",
                                "Por marca",
                                "Por tipo"};
            this.opEsc = new Menu(opcoes5);                    
            break;
           case 6:
            String[] opcoes6 = {"Indique marca do veiculo:"};
            this.opEsc = new Menu(opcoes6);
            break;                   
           case 7:
            String[] opcoes7 = {"Indique o tipo de veiculo:"};
            this.opEsc = new Menu(opcoes7);
            break;    
           case 8:
            String[] opcoes8 = {"VeiculoNormal",
                                "VeiculoPremium",
                                "VeiculoOcasiao",
                                "AutocarroInteligente"};
            this.opEsc = new Menu(opcoes8);                    
            break;                    
           case 9:
            String[] opcoes9 = {"Indique o numero de kms:"};
            this.opEsc = new Menu(opcoes9);
            break;
           case 10:
            String[] opcoes10 = {"Indique a classificacao (0-10):"};
            this.opEsc = new Menu(opcoes10);
            break;
           case 11:
            String[] opcoes11 = {"Guardar em ficheiro texto",
                                 "Guardar em fichiro binario"};
            this.opEsc = new Menu(opcoes11); 
            break;
           case 12:
            String[] opcoes12 = {"Indique matricula do veiculo:",
                                 "Indique a marca:",
                                 "Indique o modelo:",
                                 "Indique o ano:",
                                 "Indique a velocidade media:",
                                 "Indique o preco por km:",
                                 "Indique a taxa:",
                                 "Esta em desconto? (true/false):",
                                 "Indique a lotacao:",
                                 "Indique a ocupacao:"};
            this.opEsc = new Menu(opcoes12);
            break;
       }
    }
    
    private void run() {
        boolean lido = false;
        Scanner ler = new Scanner(System.in);
        String s;
        Veiculo v;
        int opcao;
        
        while(true) {
        if(!lido) {
            first.executa();
            switch (first.getOpcao()) {
                case 1:
                        predf();
                        System.out.println("DriveIt predefinido carregado.");
                        lido = true;
                        break;
                case 2:
                        setMenuOp(2);
                        s = this.opEsc.exec();
                        try {
                            this.drive = DriveIt.carregaEstado(s);
                            System.out.println("DriveIt carregado com sucesso.");
                            lido = true;
                        }
                        catch(FileNotFoundException e) {
                            System.out.println("Nao foi possivel encontrar ficheiro.");
                            lido = false;
                        }
                        catch(IOException e) {
                            System.out.println("Nao foi possivel carregar ficheiro.");
                            lido = false;
                        }
                        catch(ClassNotFoundException e) {
                            System.out.println("Classe nao encontrada");
                            lido = false;
                        }
                        break;
                case 0: 
                    System.exit(0);
            }
        }
        if(lido) {
            principal.executa();
            switch (principal.getOpcao()) {
                    case 1:
                        predf();
                        System.out.println("DriveIt predefinido carregado.");
                        lido = true;
                        break;
                    case 2:
                        setMenuOp(2);
                        s = this.opEsc.exec();
                        try {
                            this.drive = DriveIt.carregaEstado(s);
                            System.out.println("DriveIt carregado com sucesso.");
                            lido = true;
                        }
                        catch(FileNotFoundException e) {
                            System.out.println("Nao foi possivel encontrar ficheiro.");
                            lido = false;
                        }
                        catch(IOException e) {
                            System.out.println("Nao foi possivel carregar ficheiro.");
                            lido = false;
                        }
                        catch(ClassNotFoundException e) {
                            System.out.println("Classe nao encontrada");
                            lido = false;
                        }
                        break;
                    case 3:
                        System.out.println(drive.toString());
                        break;
                    case 4:
                        setMenuOp(4);
                        s = this.opEsc.exec();
                        if (drive.existeVeiculo(s)) 
                            System.out.println("O veiculo de matricula " + s + " existe.");
                        else 
                            System.out.println("O veiculo de matricula " + s + " nao existe.");
                        break;
                    case 5:
                        setMenuOp(5);
                        opcao = Integer.parseInt(this.opEsc.execLer());
                        if (opcao == 1) 
                            System.out.println("Existem no total " + drive.quantos() + " veiculos.");
                        if (opcao == 2) {
                            setMenuOp(6);
                            s = this.opEsc.exec();
                            System.out.println("Existem da marca "+ s + " " + drive.quantos(s) + " veiculos.");
                        }
                        if (opcao == 3) {
                            setMenuOp(7);
                            s = this.opEsc.exec();
                            try {
                                System.out.println("Existem do tipo "+ s + " " + drive.quantosT(s) + " veiculos.");
                            }
                            catch(ClassNotFoundException e) {
                                System.out.println("Classe nao existe.");
                            }
                            
                        }
                        break;
                    case 6:
                        setMenuOp(4);
                        s = this.opEsc.exec();
                        try {
                            System.out.println(drive.getVeiculo(s).toString());
                        }
                        catch(VeiculoNaoExisteException e) {
                            System.out.println("Veiculo nao existe.");
                        }
                        break;
                    case 7:
                        setMenuOp(8);
                        opcao = Integer.parseInt(this.opEsc.execLer());
                        try {
                            v = novoVeiculo(opcao);
                            if (v != null)  {
                                drive.adiciona(v);
                                System.out.println("Veiculo adicionado com sucesso");
                            }
                            else 
                                System.out.println("Veiculo nao adicionado");
                        }
                        catch(ValorInvalidoException e) {
                            System.out.println("Valor invalido.");
                        }
                        catch(VeiculoJaExisteException e) {
                            System.out.println("Veiculo ja existe.");
                        }
                        break;
                    case 8:
                        setMenuOp(4);
                        s = this.opEsc.exec();
                        setMenuOp(9);
                        opcao = Integer.parseInt(this.opEsc.exec());
                        try {
                            drive.registarAluguer(s, opcao);
                        }
                        catch (VeiculoNaoExisteException e){
                            System.out.println("Veiculo nao existe.");
                        }
                        break;
                    case 9:
                        setMenuOp(4);
                        s = this.opEsc.exec();
                        setMenuOp(10);
                        opcao = Integer.parseInt(this.opEsc.exec());
                        try {
                            drive.classificarVeiculo(s, opcao);
                        }
                        catch (VeiculoNaoExisteException e){
                            System.out.println("Veiculo nao existe.");
                        }
                        break;
                    case 10:
                        setMenuOp(4);
                        s = this.opEsc.exec();                    
                        try {
                            System.out.println("Custo veiculo " + s + ": " + drive.custoRealKm(s));
                        }
                        catch (VeiculoNaoExisteException e){
                            System.out.println("Veiculo nao existe.");
                        }
                        break;
                    case 11:
                        System.out.println("Veiculos que dao pontos:");
                        System.out.println(drive.daoPontos().toString());
                        break;
                    case 12:
                        setMenuOp(5);
                        opcao = Integer.parseInt(this.opEsc.execLer());
                        setMenuOp(2);
                        s = this.opEsc.exec();
                        if (opcao == 1) {
                            try {
                                drive.escreveFicheiroTexto(s);
                                System.out.println("DriveIt guardado com sucesso.");
                            }
                            catch(FileNotFoundException e) {
                                System.out.println("Nao foi possivel encontrar ficheiro.");
                            }                            
                        }
                        else
                        if (opcao == 2){
                            try {
                                drive.guardaEstado(s);
                                System.out.println("DriveIt guardado com sucesso.");
                            }
                            catch(FileNotFoundException e) {
                                System.out.println("Nao foi possivel encontrar ficheiro.");
                            }
                            catch(IOException e) {
                                System.out.println("Nao foi possivel guardar ficheiro em binario.");
                            }
                        }
                        else 
                            System.out.println("DriveIt nao guardado.");
                        break;
                    case 0: 
                        System.exit(0);
                    
                }                
            
        }
      }
    }
    
    public void predf() {
       Veiculo v1,v2,v3,v4,v5;
       
       this.drive = new DriveIt("Predefinido");
       try {
           
           v1 = new VeiculoNormal("30JB91","Renault","Megane",2007, 75, 1.33, new ArrayList<>(),0,0);
           v2 = new VeiculoNormal("01SP31","Ferrari","234",2017, 120, 3.33, new ArrayList<>(),0,0);
           v3 = new AutocarroInteligente("00HB53","Opel","Corsa",2000, 55, 1.03, new ArrayList<>(),0,0, 200,150);
           v4 = new VeiculoPremium("78WB81","Mazda","3",2009, 73, 1.45, new ArrayList<>(),0,0, 1.5);
           v5 = new VeiculoOcasiao("07RV56","Fiat","500",2015, 67, 1.23, new ArrayList<>(),0,0, true);
           this.drive.adiciona(v1);
           this.drive.adiciona(v2);
           this.drive.adiciona(v3);
           this.drive.adiciona(v4);
           this.drive.adiciona(v5);
           this.drive.registarAluguer("07RV56",123);
           this.drive.registarAluguer("00HB53",13);
           this.drive.registarAluguer("00HB53",10);
           this.drive.classificarVeiculo("00HB53", 10);
       }
       catch(ValorInvalidoException e) {
           System.out.println("Valor invalido");
       }
       catch(VeiculoJaExisteException e) {
           System.out.println("Veiculo ja existe.");
       }
       catch (VeiculoNaoExisteException e){
           System.out.println("Veiculo nao existe.");
       }
   }
   
   public Veiculo novoVeiculo(int tipo) throws ValorInvalidoException {
       if (tipo < 1 || tipo > 4) throw new ValorInvalidoException();
       Scanner ler = new Scanner(System.in);
       String mat;
       String marc;
       String mod;
       int ano;
       double vel;
       double preco;
       setMenuOp(12);
       mat = this.opEsc.novoVeiculoMenu(0);
       marc = this.opEsc.novoVeiculoMenu(1);
       mod = this.opEsc.novoVeiculoMenu(2);
       ano = Integer.parseInt(this.opEsc.novoVeiculoMenu(3));
       vel = Double.valueOf(this.opEsc.novoVeiculoMenu(4));
       preco = Double.valueOf(this.opEsc.novoVeiculoMenu(5));
       if (tipo == 1) return new VeiculoNormal(mat, marc, mod, ano, vel, preco);
       if (tipo == 2) {
           double taxa;
           taxa = Double.valueOf(this.opEsc.novoVeiculoMenu(6));
           return new VeiculoPremium(mat, marc, mod, ano, vel, preco, taxa);
       }
       if (tipo == 3) {
           try {
               boolean desconto;
               desconto = Boolean.valueOf(this.opEsc.novoVeiculoMenu(7));
               return new VeiculoOcasiao(mat, marc, mod, ano, vel, preco, desconto);
            }
           catch (InputMismatchException e) {
               System.out.println("Valor invalido!");
            }
       }
       if (tipo == 4) {
           int lotacao;
           int ocupacao;
           lotacao = Integer.parseInt(this.opEsc.novoVeiculoMenu(8));
           ocupacao = Integer.parseInt(this.opEsc.novoVeiculoMenu(9));
           return new AutocarroInteligente(mat, marc, mod, ano, vel, preco, lotacao, ocupacao);
       }
       return null;
    }
    
}
