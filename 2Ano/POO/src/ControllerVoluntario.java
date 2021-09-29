import java.util.Map;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

public class ControllerVoluntario {
    public static void run(TrazAqui trazAqui){
        boolean exit = false;
        while(!exit){
            int opcao = -1;
            boolean disp = trazAqui.getDisp();
            while(opcao < 0 || opcao > 4) {
                opcao = Menu.menuVoluntario(disp);
            }
            switch(opcao) {
                case 1:
                    if (!disp) Menu.errors(7);
                    else {
                        int op2 = -1;
                        Map<String,Double> tp = trazAqui.transpInfo();
                        if (!tp.isEmpty()) {
                            while(op2 != 1 && op2 != 0)
                                op2 = Menu.volEncInfo(tp);
                            if (op2 == 1) {
                                String codEnc = Menu.voluntarioMenuData(2);
                                if (trazAqui.isValidCodeEnc(codEnc)) {
                                    boolean res = trazAqui.pedirTranspVol(codEnc);
                                    if (res) Menu.voluntarioMenuResult(2, codEnc);
                                    else Menu.errors(9);
                                }
                                else
                                    Menu.errors(4);
                            }
                        }
                        else Menu.errors(12);
                    }
                    break;
                    
                case 2:
                    if (!disp) {
                        double time = trazAqui.entregaEnc();
                        if (time == -1) Menu.errors(8);
                        else Menu.voluntarioMenuResult(3, Menu.time(time));
                    }
                    else {
                        Menu.errors(12);
                    }
                    break;
                    
                case 3:
                     double classific = trazAqui.getAverageClassi();
                     Menu.voluntarioMenuResult(4,String.valueOf(classific));
                    break;
                    
                case 4:
                int a = -1;
                        while (a < 0) a = Menu.dataInfo(1, true);
                        int m = -1;
                        while (m < 0 || m > 12) m = Menu.dataInfo(2, true);
                        int d = -1;
                        while (d < 0 || d > 31) d = Menu.dataInfo(3, true);
                        LocalDateTime inicio1 = LocalDateTime.of(a, m, d, 0, 0, 0);

                        int a1 = -1;
                        while (a1 < 0) a1 = Menu.dataInfo(1, false);
                        int m1 = -1;
                        while (m1 < 0 || m1 > 12) m1 = Menu.dataInfo(2, false);
                        int d1 = -1;
                        while (d1 < 0 || d1 > 31) d1 = Menu.dataInfo(3, false);
                        LocalDateTime fim1 = LocalDateTime.of(a1, m1, d1, 0, 0, 0);

                        if (inicio1.isAfter(fim1)) Menu.errors(4);
                        else{
                            List<Encomenda> historico = trazAqui.getHistorico(TipoConta.Voluntario).stream()
                                    .filter(q -> (q.getData().isBefore(fim1) && q.getData().isAfter(inicio1)))
                                    .sorted(Comparator.comparing(Encomenda::getData).reversed())
                                    .collect(Collectors.toList());
                            Menu.printHistorico(historico,trazAqui.getCod(),inicio1, fim1);
                            
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
