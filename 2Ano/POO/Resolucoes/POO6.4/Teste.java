import java.util.*;
import java.io.*;
/**
 * Escreva a descrição da classe Teste aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Teste {
   public static Veiculo novoVeiculo(int tipo) throws ValorInvalidoException {
       Scanner ler = new Scanner(System.in);
       String mat;
       String marc;
       String mod;
       int ano;
       double vel;
       double preco;
       System.out.println("Indique a matricula:");
       mat = ler.nextLine();
       System.out.println("Indique a marca:");
       marc = ler.nextLine();
       System.out.println("Indique o modelo:");
       mod = ler.nextLine();
       System.out.println("Indique o ano:");
       ano = ler.nextInt();
       System.out.println("Indique a velocidade media:");
       vel = ler.nextDouble();
       System.out.println("Indique o preco por km:");
       preco = ler.nextDouble();
       if (tipo == 1) return new VeiculoNormal(mat, marc, mod, ano, vel, preco);
       if (tipo == 2) {
           double taxa;
           System.out.println("Indique a taxa:");
           taxa = ler.nextDouble();
           return new VeiculoPremium(mat, marc, mod, ano, vel, preco, taxa);
       }
       if (tipo == 3) {
           try {
               boolean desconto;
               System.out.println("Esta em desconto? (true/false):");
               desconto = ler.nextBoolean();
            
               return new VeiculoOcasiao(mat, marc, mod, ano, vel, preco, desconto);
            }
           catch (InputMismatchException e) {
               System.out.println("Valor invalido!");
            }
       }
       if (tipo == 4) {
           int lotacao;
           int ocupacao;
           System.out.println("Indique a lotacao:");
           lotacao = ler.nextInt();
           System.out.println("Indique a ocupacao:");
           ocupacao = ler.nextInt();
           return new AutocarroInteligente(mat, marc, mod, ano, vel, preco, lotacao, ocupacao);
       }
       return null;
    }
    
   public static DriveIt predf() {
       Veiculo v1,v2,v3,v4,v5;
       DriveIt drive = new DriveIt("Predefinido");
       try {
           v1 = new VeiculoNormal("30JB91","Renault","Megane",2007, 75, 1.33, new ArrayList<>(),0,0);
           v2 = new VeiculoNormal("01SP31","Ferrari","234",2017, 120, 3.33, new ArrayList<>(),0,0);
           v3 = new AutocarroInteligente("00HB53","Opel","Corsa",2000, 55, 1.03, new ArrayList<>(),0,0, 200,150);
           v4 = new VeiculoPremium("78WB81","Mazda","3",2009, 73, 1.45, new ArrayList<>(),0,0, 1.5);
           v5 = new VeiculoOcasiao("07RV56","Fiat","500",2015, 67, 1.23, new ArrayList<>(),0,0, true);
           drive.adiciona(v1);
           drive.adiciona(v2);
           drive.adiciona(v3);
           drive.adiciona(v4);
           drive.adiciona(v5);
           drive.registarAluguer("07RV56",123);
           drive.registarAluguer("00HB53",13);
           drive.registarAluguer("00HB53",10);
           drive.classificarVeiculo("00HB53", 10);
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
       return drive;
   }
    
    public static void main() {
        boolean lido = false;
        Scanner ler = new Scanner(System.in);
        String s;
        Veiculo v;
        DriveIt drive = new DriveIt();        
         while(true) {
            
            if (!lido) {
                System.out.println();
                System.out.println("******** MENU DRIVEIT ********\n");
                System.out.println("Selecione uma das opções:");
                System.out.println("1 - Carregar DriveIt predefinido");
                System.out.println("2 - Carregar de Ficheiro");
                System.out.println("0 - Sair do programa;");
                System.out.println();
                System.out.print("> ");
            }
            else {
                System.out.println();
                System.out.println("******** MENU DRIVEIT ********\n");
                System.out.println("Selecione uma das opções:");
                System.out.println("1 - Carregar DriveIt predefinido");
                System.out.println("2 - Carregar de Ficheiro");
                System.out.println("3 - Ver informacao do DriveIt");
                System.out.println("4 - Existe veiculo?");
                System.out.println("5 - Nº de veiculos");
                System.out.println("6 - Veiculo por matricula ");
                System.out.println("7 - Adiciona veiculo");
                System.out.println("8 - Registar aluguer");
                System.out.println("9 - Classificar veiculo");
                System.out.println("10 - Custo de um veiculo por km");
                System.out.println("11 - Veiculos que dao pontos");
                System.out.println("12 - Guardar em Ficheiro");
                System.out.println("0 - Sair do programa;");
                System.out.println();
                System.out.print("> ");
            }
            
            int opcao = ler.nextInt();
            if (!lido) {
                 switch (opcao) {
                   
                    case 1:
                        drive = predf();
                        System.out.println("DriveIt predefinido carregado.");
                        lido = true;
                        break;
                    case 2:
                        System.out.println("Indique nome do ficheiro:");
                        ler.nextLine();
                        s = ler.nextLine();
                        try {
                            drive = DriveIt.carregaEstado(s);
                            System.out.println("DriveIt carregado com sucesso.");
                            lido = true;
                        }
                        catch(FileNotFoundException e) {
                            System.out.println("Nao foi possivel encontrar ficheiro.");
                        }
                        catch(IOException e) {
                            System.out.println("Nao foi possivel carregar ficheiro.");
                        }
                        catch(ClassNotFoundException e) {
                            System.out.println("Classe nao encontrada");
                        }
                        break;
                    case 0: 
                        System.exit(0);
                 }
            }
            else {
                switch (opcao) {
                   
                    case 1:
                        drive = predf();
                        System.out.println("DriveIt predefinido carregado com sucesso.");
                        lido = true;
                        break;
                    case 2:
                        System.out.println("Indique nome do ficheiro:");
                        ler.nextLine();
                        s = ler.nextLine();
                        try {
                            drive = DriveIt.carregaEstado(s);
                            System.out.println("DriveIt carregado com sucesso.");
                            lido = true;
                        }
                        catch(FileNotFoundException e) {
                            System.out.println("Nao foi possivel encontrar ficheiro.");
                        }
                        catch(IOException e) {
                            System.out.println("Nao foi possivel carregar ficheiro.");
                        }
                        catch(ClassNotFoundException e) {
                            System.out.println("Classe nao encontrada");
                        }
                        break;
                    case 3:
                        System.out.println(drive.toString());
                        break;
                    case 4:
                        System.out.println("Indique matricula do veiculo:");
                        ler.nextLine();
                        s = ler.nextLine();
                        if (drive.existeVeiculo(s)) 
                            System.out.println("O veiculo de matricula " + s + " existe.");
                        else 
                            System.out.println("O veiculo de matricula " + s + " nao existe.");
                        break;
                    case 5:
                        System.out.println("Selecione uma das opções:");
                        System.out.println("1 - Total");
                        System.out.println("2 - Por marca");
                        System.out.println("3 - Por tipo");
                        opcao = ler.nextInt();
                        if (opcao == 1) 
                            System.out.println("Existem no total " + drive.quantos() + " veiculos.");
                        if (opcao == 2) {
                            System.out.println("Indique a marca");
                            ler.nextLine();
                            s = ler.nextLine();
                            System.out.println("Existem da marca "+ s + " " + drive.quantos(s) + " veiculos.");
                        }
                        if (opcao == 3) {
                            System.out.println("Indique o tipo");
                            ler.nextLine();
                            s = ler.nextLine();
                            try {
                                System.out.println("Existem do tipo "+ s + " " + drive.quantosT(s) + " veiculos.");
                            }
                            catch(ClassNotFoundException e) {
                                System.out.println("Classe nao existe.");
                            }
                            
                        }
                        break;
                    case 6:
                        System.out.println("Indique matricula do veiculo:");
                        ler.nextLine();
                        s = ler.nextLine();
                        try {
                            System.out.println(drive.getVeiculo(s).toString());
                        }
                        catch(VeiculoNaoExisteException e) {
                            System.out.println("Veiculo nao existe.");
                        }
                        break;
                    case 7:
                        System.out.println("Selecione uma das opções:");
                        System.out.println("1 - VeiculoNormal");
                        System.out.println("2 - VeiculoPremium");
                        System.out.println("3 - VeiculoOcasiao");
                        System.out.println("4 - AutocarroInteligente");
                        opcao = ler.nextInt();
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
                        System.out.println("Indique matricula do veiculo:");
                        ler.nextLine();
                        s = ler.nextLine();
                        System.out.println("Indique o numero de kms:");
                        opcao = ler.nextInt();
                        try {
                            drive.registarAluguer(s, opcao);
                        }
                        catch (VeiculoNaoExisteException e){
                            System.out.println("Veiculo nao existe.");
                        }
                        break;
                    case 9:
                        System.out.println("Indique matricula do veiculo:");
                        ler.nextLine();
                        s = ler.nextLine();
                        System.out.println("Indique a classificacao (0-10):");
                        opcao = ler.nextInt();
                        try {
                            drive.classificarVeiculo(s, opcao);
                        }
                        catch (VeiculoNaoExisteException e){
                            System.out.println("Veiculo nao existe.");
                        }
                        break;
                    case 10:
                        System.out.println("Indique matricula do veiculo:");
                        ler.nextLine();
                        s = ler.nextLine();                        
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
                        System.out.println("Selecione uma das opções:");
                        System.out.println("1 - Guardar em ficheiro texto");
                        System.out.println("2 - Guardar em fichiro binario");
                        opcao = ler.nextInt();
                        System.out.println("Indique nome do ficheiro:");
                        ler.nextLine();
                        s = ler.nextLine();
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
}
