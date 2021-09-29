import java.util.*;
import java.io.*;

public class Controller {
    public static void run() {
        TrazAqui trazAqui = new TrazAqui();

        boolean errorMessage = false;
        while(true){
            int opcao = -1;
            while(opcao < 0 || opcao > 7) {
                opcao = Menu.MenuInicial();
            }
       
            switch(opcao) {
                
                case 1:
                        AbstractMap.SimpleEntry<String, String> dados = Menu.menuLogin(errorMessage);
                        if(!trazAqui.login(dados.getKey(), dados.getValue())) Menu.errors(16);
                    else{ 
                    Menu.clearWindow();
                    TipoConta tc = trazAqui.getTipoConta();
                    if (tc.equals(TipoConta.Utilizador)) ControllerUtilizador.run(trazAqui);
                    else if (tc.equals(TipoConta.Voluntario)) ControllerVoluntario.run(trazAqui);
                    else if (tc.equals(TipoConta.Transportadora)) ControllerTransportadora.run(trazAqui);
                    else ControllerLoja.run(trazAqui);
                }
                    break;
                    
                case 2:
                    TipoConta tipoConta = null;
                    while (tipoConta == null) tipoConta = Menu.menuRegisto();

                    Conta conta;

                    if (tipoConta.equals(TipoConta.Utilizador)) conta = Menu.menuRegistoUtilizador(trazAqui.getNewCode(TipoConta.Utilizador));
                    else if (tipoConta.equals(TipoConta.Voluntario)) conta = Menu.menuRegistoVoluntario(trazAqui.getNewCode(TipoConta.Voluntario));
                    else if (tipoConta.equals(TipoConta.Loja)) conta = Menu.menuRegistoLoja(trazAqui.getNewCode(TipoConta.Loja));
                    else conta = Menu.menuRegistoTransportadora(trazAqui.getNewCode(TipoConta.Transportadora));
                     
                    if (!trazAqui.freeEmail(conta.getEmail())) Menu.errors(18);
                    else{
                    trazAqui.registo(conta);

                    if (tipoConta.equals(TipoConta.Utilizador)) ControllerUtilizador.run(trazAqui);
                    else if (tipoConta.equals(TipoConta.Voluntario)) ControllerVoluntario.run(trazAqui);
                    else if (tipoConta.equals(TipoConta.Transportadora)) ControllerTransportadora.run(trazAqui);
                    else ControllerLoja.run(trazAqui);
                }

                    break;
                    
                case 3:
                    trazAqui.carregaLogs();
                    Menu.pressEnter();
                    break;
                    
                case 4:
                    try{trazAqui.salvaEstadoObj();System.out.println("Ficheiros salvos com sucesso!!!\n");}
                    catch (FileNotFoundException e) {Menu.errors(1);}
                    catch (IOException e) {Menu.errors(2);}
                    Menu.pressEnter();
                    break;
                    
                case 5:
                    try {trazAqui.carregaEstadoObj();System.out.println("Ficheiros carregados com sucesso!!!\n");}
                    catch (FileNotFoundException e) {Menu.errors(1);}
                    catch (IOException e) {Menu.errors(6);}
                    catch (ClassNotFoundException e) {Menu.errors(3);}
                    Menu.pressEnter();
                    break;
                case 6:
                    List<AbstractMap.SimpleEntry<String, Integer>> l  = trazAqui.utilMaisFreq();
                    Menu.maisFreqU(l);
                    Menu.pressEnter();
                    break;
                case 7:
                    List<AbstractMap.SimpleEntry<String, Double>> l1  = trazAqui.transpMaisFreq();
                    Menu.maisFreqT(l1);
                    Menu.pressEnter();
                    break;    
                case 0:
                    System.exit(0);
                    break;
                    
            }
        }
    }
}
