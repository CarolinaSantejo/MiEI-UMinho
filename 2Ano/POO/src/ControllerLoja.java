import java.util.List;

public class ControllerLoja {
    public static void run(TrazAqui trazAqui) {
        boolean exit = false;
        while(!exit){
            int opcao = -1;
            while(opcao < 0 || opcao > 3) {
                opcao = Menu.menuLoja();
            }
            switch(opcao) {
                case 1:
                    List<String> encomendas = trazAqui.getFilaEspera();
                    List<String> str = trazAqui.getEncP();
                    Menu.showExtraInfo(encomendas,3);
                    Menu.showExtraInfo(str,4);
                    break;
                case 2:
                     String enc = Menu.LojaMenuData(1);
                     double time;
                     if (trazAqui.checkEncInStore(enc)||trazAqui.checkEncInStoreDesp(enc)) {
                         time = trazAqui.getTempoEspera(enc);
                         Menu.LojaMenuRes(1,String.valueOf(time));
                        }
                     else Menu.errors(17);   
                break;
                
                case 3:
                List<String> s = trazAqui.getEncP();
                List<String> s1 =trazAqui.getFilaEspera();
                if (s1.isEmpty()) {
                   Menu.extraNotes(6);
                   Menu.pressEnter();
                }
                else{
                Menu.showExtraInfo(s,4);
                Menu.showExtraInfo(s1,3);
               
                String enc1 = Menu.LojaMenuData(1);
                Menu.pressEnter();
                  if (!trazAqui.checkEncInStoreDesp(enc1) && !trazAqui.checkEncInStore(enc1)) Menu.errors(17);
                  else if (trazAqui.checkEncInStoreDesp(enc1)) Menu.extraNotes(4);
                  else {
                      trazAqui.despacharEnc(enc1);
                      Menu.extraNotes(5);
                    }
                }
                break;
                case 0:
                    exit=true;
                    Menu.clearWindow();
                    break;
            }
        }
    }
}
