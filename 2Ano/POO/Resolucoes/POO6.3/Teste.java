import java.util.*;
/**
 * Escreva a descrição da classe Teste aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Teste
{
    public static void main() throws ClassNotFoundException{
        Veiculo v1 = new VeiculoNormal("30JB91","Renault","Megane",2007, 75, 1.33, new ArrayList<>(),0,0);
        Veiculo v2 = new VeiculoNormal("01SP31","Ferrari","234",2017, 120, 3.33, new ArrayList<>(),0,0);
        Veiculo v3 = new AutocarroInteligente("00HB53","Opel","Corsa",2000, 55, 1.03, new ArrayList<>(),0,0, 150,200);
        Veiculo v4 = new VeiculoPremium("78WB81","Mazda","3",2009, 73, 1.45, new ArrayList<>(),0,0, 1.5);
        Veiculo v5 = new VeiculoOcasiao("07RV56","Fiat","500",2015, 67, 1.23, new ArrayList<>(),0,0, true);
        DriveIt drive = new DriveIt();
        drive.adiciona(v1);
        drive.adiciona(v2);
        drive.adiciona(v3);
        drive.adiciona(v4);
        drive.adiciona(v5);
        drive.registarAluguer("07RV56",123);
        drive.registarAluguer("00HB53",13);
        drive.registarAluguer("00HB53",10);
        drive.classificarVeiculo("00HB53", 10);
        System.out.println(drive.toString());
        System.out.println("Quantidade de VeiculoNormal: " + drive.quantosT("VeiculoNormal"));
        System.out.println("Custo real (veiculo de matricula 00HB53): " + drive.custoRealKm("00HB53"));
        System.out.println("Quantidade da marca Opel: " + drive.quantos("Opel"));
        System.out.println("Veiculos que dao pontos:");
        System.out.println(drive.daoPontos().toString());
    }
    
}
