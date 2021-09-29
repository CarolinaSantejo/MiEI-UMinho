import java.util.Map;
import java.util.AbstractMap;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ControllerTransportadora {
    public static void run(TrazAqui trazAqui){
        boolean exit = false;
        while(!exit){
            int opcao = -1;
            boolean disp = trazAqui.getDisp();
            while(opcao < 0 || opcao > 6) {
                opcao = Menu.menuTransportadora(disp);
            }
            switch(opcao) {
                case 1:
                    Set<String> s  = trazAqui.encsPossibleToTransp();
                    String codEnc = Menu.transportadoraMenuData(1,s.toString());
                    if (trazAqui.isValidCodeEnc(codEnc)) {
                        if (trazAqui.podeTransportarT(codEnc)){
                            double preco = trazAqui.precoTransporte(codEnc);
                            Menu.transportadoraMenuResult(1, String.valueOf(preco));
                        }
                        else Menu.errors(13);
                    }
                    else Menu.errors(4);
                    break;
                case 2:
                
                    if (!trazAqui.getIsEmptyEncsT()) {
                            Map<String,AbstractMap.SimpleEntry<Double, Double>> p = trazAqui.entregaEncs();
                            if (p != null) Menu.mostrarTabelaTransportadora(p);
                            else Menu.errors(10);
                        
                    }
                    else {
                        Menu.errors(12);
                    }
                    break;
                case 3:
                    int ano = -1;
                        while (ano < 0) ano = Menu.dataInfo(1, true);
                        int mes = -1;
                        while (mes < 0 || mes > 12) mes = Menu.dataInfo(2, true);
                        int dia = -1;
                        while (dia < 0 || dia > 31) dia = Menu.dataInfo(3, true);
                        LocalDateTime inicio = LocalDateTime.of(ano, mes, dia, 0, 0, 0);

                        int ano1 = -1;
                        while (ano1 < 0) ano1 = Menu.dataInfo(1, false);
                        int mes1 = -1;
                        while (mes1 < 0 || mes1 > 12) mes1 = Menu.dataInfo(2, false);
                        int dia1 = -1;
                        while (dia1 < 0 || dia1 > 31) dia1 = Menu.dataInfo(3, false);
                        LocalDateTime fim = LocalDateTime.of(ano1, mes1, dia1, 0, 0, 0);

                   if (inicio.isAfter(fim)) Menu.errors(5);
                   else {
                       double fat = trazAqui.faturamentoEntreDatas(inicio,fim);
                       Menu.transportadoraMenuResult(4, String.valueOf(fat));
                    }
                    break;
                        
                        
                case 4:
                  if (!trazAqui.getDisp()) Menu.errors(7);
                  else{
                  Map<String,List<Double>> map = trazAqui.transpInfoT();
                  if (map.isEmpty()) Menu.errors(14);
                  else{
                  int op = -1;
                  while (op!=1 && op!=0) op = Menu.transpEncInfo(map);
                    if (op==1) {
                        String codEnc1 = Menu.transportadoraMenuData(1,null);
                        if (!map.containsKey(codEnc1)) Menu.errors(4);
                        else {
                            trazAqui.addToPedidosTransp(codEnc1);
                            Menu.extraNotes(1);
                            Menu.pressEnter();
                             }
                    }
                }
                    
                 }
                Menu.clearWindow();
                break;
                
                case 5:
                double classific = trazAqui.getAverageClassi();
                Menu.transportadoraMenuResult(5,String.valueOf(classific));
                break;
                
                case 6:
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
                            List<Encomenda> historico = trazAqui.getHistorico(TipoConta.Transportadora).stream()
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
