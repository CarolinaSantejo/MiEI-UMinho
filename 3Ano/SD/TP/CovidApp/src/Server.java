
import Exceptions.*;
import GestInfo.GestInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class Server {
    private static GestInfo model;
    private static int locVx=0;
    private static int locVy=0;
    private static Map<String,TaggedConnection> tg = new HashMap<>();




    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(12343);
        model = new GestInfo();

        while(true) {
            Socket s = ss.accept();
            TaggedConnection c = new TaggedConnection(s);

            Runnable worker = () -> {
                try {
                    for (;;) {
                            int cond = 0;
                            TaggedConnection.Frame frame = c.receive();
                            int tag = frame.tag;
                            String data = new String(frame.data);
                        try {
                            if (frame.tag == 0) {
                                model.userInfetado(data);
                                Runnable comunicaInfetado = () -> {
                                    Set<String> contagiados = model.verificaInfetados(data);
                                    System.out.println("Contágios: " + contagiados.toString());
                                    for (String user : contagiados) {
                                        TaggedConnection con = tg.get(user);
                                        try {
                                            con.send(6,"Esteve em contacto com um infetado!!".getBytes());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                new Thread(comunicaInfetado).start();
                            }
                            else if (frame.tag == 1) {
                                String id = model.registaUser(data);
                                //Inicio do TESTE
                                System.out.println();
                                for (int i = 0; i < 2; i++)
                                    for (int j = 0; j < 2; j++)
                                        System.out.println("Matriz i: "+i + " j:" + j + " nº pessoas: "+ model.getMapa().getLocalizacao(i,j).getNpessoas());
                                System.out.println("Replying to: " + id);
                                //Fim do TESTE
                                tg.put(id,c);
                                c.send(frame.tag, String.valueOf(cond).getBytes());
                                c.send(frame.tag, "Registo Concluído!!".getBytes());
                            }

                            else if (frame.tag == 2) {
                                String[] tokens = data.split(" ");
                                if (model.verificaUser(tokens[0],tokens[1])) {
                                    System.out.println("Replying to: " + data);
                                    tg.put(tokens[0],c);
                                    int e = model.isSpecial(tokens[0]);
                                    c.send(frame.tag, String.valueOf(cond).getBytes());
                                    c.send(frame.tag, ("Login Concluído!!"+":"+ e + ":").getBytes());
                                }
                            }

                            else if (frame.tag == 3) {
                                String[] tokens = data.split(" ");
                                if (model.verificaCoordenadas(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]))) {
                                    int n = model.getMapa().getLocalizacao(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])).getNpessoas();
                                    c.send(frame.tag, String.valueOf(cond).getBytes());
                                    c.send(frame.tag, String.valueOf(n).getBytes());
                                }

                            }

                            else if (frame.tag == 4){
                                String[] tokens = data.split(" ");
                                locVx = Integer.parseInt(tokens[0]);
                                locVy = Integer.parseInt(tokens[1]);
                                String user = tokens[2];
                                if (model.verificaCoordenadas(locVx,locVy) && model.verificaUserCoordenadas(locVx,locVy,user)) {
                                    Runnable localizacaoVazia = () -> {
                                        try {
                                            model.getMapa().getLocalVazio(locVx, locVy);
                                        } catch (InterruptedException e) {
                                            System.out.println("Localização Vazia");
                                            try {
                                                c.send(tag, (locVx + " " + locVy).getBytes());
                                            } catch (IOException ioException) {
                                                ioException.printStackTrace();
                                            }
                                        }
                                    };
                                    new Thread(localizacaoVazia).start();
                                    c.send(frame.tag, String.valueOf(cond).getBytes());
                                    c.send(frame.tag, (locVx + " " + locVy).getBytes());
                                }
                            }

                            else if(frame.tag==5) {
                                List<String> res = model.usersMapa();
                                c.send(5,String.valueOf(res.size()).getBytes());
                                for(String loc : res) {
                                    c.send(5,loc.getBytes());
                                }
                            }
                            else if(frame.tag==6){
                                //Não recebe nada.
                                //Apenas envia da tag 0 para a 6 quando há infetados.
                            }
                            else if(frame.tag==7){
                                model.novaLocalizacao(data);
                                //Inicio do TESTE
                                System.out.println();
                                for (int i = 0; i < 2; i++)
                                    for (int j = 0; j < 2; j++)
                                        System.out.println("Matriz i: "+i + " j:" + j + " nº pessoas: "+ model.getMapa().getLocalizacao(i,j).getNpessoas());
                                System.out.println("Novo Local.");
                                //Fim do TESTE
                                c.send(frame.tag, String.valueOf(cond).getBytes());
                                c.send(frame.tag,"» Localização Atualizada!".getBytes());
                            }

                        } catch ( UserJaExiste | PassIncorreta | UsernameNaoExiste | CoordenadasIncorretas | MesmaLocalizacao e) {
                            cond = 1;
                            c.send(frame.tag, String.valueOf(cond).getBytes());
                            c.send(frame.tag, e.getMessage().getBytes());
                        }
                    }
                } catch (IOException e) {
                    e.getMessage();
                }
            };

            new Thread(worker).start();

        }
    }
}

