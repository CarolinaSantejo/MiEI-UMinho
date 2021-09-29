import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.*;
import java.awt.geom.Point2D;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class Estado implements Serializable,Randoms {
    private Contas utilizadores;
    private Contas voluntarios;
    private Contas transportadoras;
    private Contas lojas;
    private Encomendas encomendas;
    private Set<AbstractMap.SimpleEntry<String, String>> pedidosTransporte; // -> (codEnc, transp)
    
    
    public Estado() {
        this.utilizadores = new Contas();
        this.voluntarios = new Contas();
        this.transportadoras = new Contas();
        this.lojas = new Contas();
        this.encomendas = new Encomendas();
        this.pedidosTransporte = new HashSet<>();
    }

    public Estado(Estado outro) {
        this.utilizadores = outro.utilizadores;
        this.voluntarios = outro.voluntarios;
        this.transportadoras = outro.transportadoras;
        this.lojas = outro.lojas;
        this.encomendas = outro.encomendas;
        this.pedidosTransporte = outro.getPedidos();
    }
    
    public List<String> getCodLojas(){
        return this.lojas.getContas().values().stream().map(Conta::getCodigo).collect(Collectors.toList());
    }
    
    public void addToPedidosTransp(String t,String enc){
        AbstractMap.SimpleEntry<String, String> a = new AbstractMap.SimpleEntry<>(enc,t);
        this.pedidosTransporte.add(a);
    }

    public List<AbstractMap.SimpleEntry<String, Integer>> utilMaisFreq() {
        List<AbstractMap.SimpleEntry<String, Integer>> le = new ArrayList<>();
        for (Conta t : this.utilizadores.getContas().values()){
            Utilizador t1 = (Utilizador) t;
            AbstractMap.SimpleEntry<String, Integer> a = new AbstractMap.SimpleEntry<>(t1.getCodigo(), t1.getEncTransportadas());
            le.add(a);
        }
        return le.stream()
                 .sorted(Comparator.comparingDouble(AbstractMap.SimpleEntry<String, Integer>::getValue).reversed())
                 .limit(10)
                 .collect(Collectors.toList());
    }

    public List<AbstractMap.SimpleEntry<String, Double>> transpMaisFreq() {
        List<AbstractMap.SimpleEntry<String, Double>> le = new ArrayList<>();
        for (Conta t : this.transportadoras.getContas().values()){
            Transportadora t1 = (Transportadora) t;
            AbstractMap.SimpleEntry<String, Double> a = new AbstractMap.SimpleEntry<>(t1.getCodigo(), t1.getKmPercorridos());
            le.add(a);
        }
        return le.stream()
                 .sorted(Comparator.comparingDouble(AbstractMap.SimpleEntry<String, Double>::getValue).reversed())
                 .limit(10)
                 .collect(Collectors.toList());
    }
    
    

    public Set<AbstractMap.SimpleEntry<String, String>> getPedidos() {
        return this.pedidosTransporte;
    }

    public boolean existeEmail(String s) {
        return this.utilizadores.existeEmail(s) || this.voluntarios.existeEmail(s)
                || this.lojas.existeEmail(s)
                || this.transportadoras.existeEmail(s);
    }

    public String newCode(TipoConta tipoConta) {
        if (tipoConta.equals(TipoConta.Utilizador)) return ("u"+this.utilizadores.newCodeNumber());
        else if (tipoConta.equals(TipoConta.Voluntario)) return ("v"+this.voluntarios.newCodeNumber());
        else if (tipoConta.equals(TipoConta.Loja)) return ("l"+this.lojas.newCodeNumber());
        else return ("t"+this.transportadoras.newCodeNumber());
        

    }
    
    public boolean freeEmail(String email){
        return this.utilizadores.getContaByEmail(email) == null
                && this.transportadoras.getContaByEmail(email) == null
                && this.lojas.getContaByEmail(email) == null
                && this.voluntarios.getContaByEmail(email) == null;
    }

    public String newCodeEnc() {
        return ("e"+this.encomendas.newCodeNumber());

    }

    public void addToEncomendas(Encomenda e) {
        this.encomendas.addEnc(e.clone());
    }

    public void addNewEncToQueue(Encomenda e) {
        String cod = e.getCodLoja();
        Loja l = (Loja) this.lojas.getContaByCode(cod);
        l.addEncomenda(e.clone());
        /*Substituindo a loja antiga pela atual com a encomenda adicionada*/
        this.lojas.addConta(l);
    }
    
    public double faturamentoEntreDatas(Conta transp,LocalDateTime inicio,LocalDateTime fim){
        Transportadora t = (Transportadora) transp;
        double custo = t.getPrecoPorKm();
        double custo1 = t.getPrecoPeso();
        return this.encomendas.getEnc().stream()
                                       .filter(a-> a.getQuemTransportou().equals(t.getCodigo()) 
                                                 && a.getData().isAfter(inicio) 
                                                 && a.getData().isBefore(fim)
                                                 && a.getFoiEntregue())
                                       .mapToDouble(a->a.getDistPercorrida()*custo + a.getPeso()*custo1).reduce(Double::sum).orElse(0);
    }
    
    public Set<String> encsOfUserToTransport(String user){
        return this.encomendas.getEnc().stream()
                                       .filter(a->a.getCodUtil().equals(user) && !a.getFoiSolicitada())
                                       .map(Encomenda::getCodEnc)
                                       .collect(Collectors.toSet());
    }
    
    public Set<String> encsPossibleToTransp(Conta t){
        return this.encomendas.getEnc().stream()
                                       .map(Encomenda::getCodEnc)
                                       .filter(a->podeTransportarT(a,t.getCodigo()))
                                       .collect(Collectors.toSet());
    }
    

    public void solicitaEnc(String e) {
        this.encomendas.solicitaEnc(e);
    }

    public boolean lojaCodeValid(String loja) {
        return this.lojas.getContas().values().stream().anyMatch(a -> a.getCodigo().equals(loja));
    }

    public boolean encCodeValid(String enc) {
        return this.encomendas.getEnc().stream().anyMatch(a -> a.getCodEnc().equals(enc));
    }

    public boolean checkIfEntityWorkedForUser(String code, String user) {
        return this.encomendas.getEnc().stream().anyMatch(a -> (a.getCodUtil().equals(user) && a.getQuemTransportou().equals(code)));
    }
    
    public List<String> entityWorkedForUser(String user) {
        return this.encomendas.getEnc().stream()
                                       .filter(a -> (a.getCodUtil().equals(user) && a.getFoiSolicitada()))
                                       .map(Encomenda::getQuemTransportou)
                                       .collect(Collectors.toList());
    }

    public boolean encFoiSolicitada(String enc) {
        return this.encomendas.getEncomendaByCod(enc)!= null;
    }

    /*public List<Encomenda> getHistoricoUser(String user) {
        return this.encomendas.getEnc().stream().filter(a -> a.getCodUtil().equals(user)).collect(Collectors.toList());
    }*/
    
    public List<Encomenda> getHistorico(TipoConta c,String cod) {
        if (c.equals(TipoConta.Utilizador))return this.encomendas.getEnc().stream().filter(a -> a.getCodUtil().equals(cod)).collect(Collectors.toList());
        else return this.encomendas.getEnc().stream().filter(a -> a.getFoiEntregue()&& a.getQuemTransportou().equals(cod)).collect(Collectors.toList());
        
    }

    public void encomendaParaSerEntregue(String codEnc, String cod) {
        // mudar a data para a data de transporte
        Encomenda e = this.encomendas.getEncomendaByCod(codEnc);
        e.setData(LocalDateTime.now());
        this.encomendas.removeOld(codEnc);
        this.encomendas.addEnc(e);
        this.encomendas.quemTransportou(codEnc, cod);
        this.pedidosTransporte.removeIf(a -> a.getKey().equals(codEnc));
    }
    
    
    
    // devolve encomenda do utilizador associada a uma lista de "pares" em que cada par contem
    //a transportadora que quer transportar, o custo e o tempo de transporte
    public Map<String, List<AbstractMap.SimpleEntry<String, ArrayList <Double>>>> getTranspOptions(String user) {
        Map<String,List<AbstractMap.SimpleEntry<String, ArrayList <Double>>>> res = new HashMap<>();

        for (AbstractMap.SimpleEntry<String, String> a : this.pedidosTransporte) {
            Encomenda e = this.encomendas.getEncomendaByCod(a.getKey());
            if (e.getCodUtil().equals(user)) {
                Conta util = this.utilizadores.getContaByCode(user);
                Transportadora transp = (Transportadora) this.transportadoras.getContaByCode(a.getValue());
                Loja loja = (Loja)this.lojas.getContaByCode(e.getCodLoja());
                double distancia = Point.distance(loja.getGPSx(), loja.getGPSy(), transp.getGPSx(), transp.getGPSy())+ Point.distance(loja.getGPSx(), loja.getGPSy(), util.getGPSx(), util.getGPSy());
                Double custo = transp.totalPreco(distancia)+e.getPeso()*transp.getPrecoPeso();
                //Não se consegue saber qual o tempo na fila que a entidade vai estar até chegar lá, logo nao adiciono tempos de fila de espera
                Double tempo = distancia/transp.getVelocidade();
                ArrayList <Double> arr = new ArrayList<>();
                arr.add(custo);
                arr.add(tempo);
                AbstractMap.SimpleEntry<String, ArrayList<Double>> ans = new AbstractMap.SimpleEntry<>(a.getValue(), arr);
                if (!res.containsKey(a.getKey())) {
                    List<AbstractMap.SimpleEntry<String, ArrayList <Double>>> l = new ArrayList<>();
                    res.put(a.getKey(), l);
                }
                res.get(a.getKey()).add(ans);
            }
        }
        return res;
    }


    public void classifica(int c, String code) {
        Voluntario v = (Voluntario) this.voluntarios.getContaByCode(code);
        Transportadora t = (Transportadora) this.transportadoras.getContaByCode(code);
        if (v != null) {
            v.addClassif(c);
            this.voluntarios.addConta(v);
        } else {
            t.addClassif(c);
            this.transportadoras.addConta(t);
        }
    }

    // Voluntario

    public void alteraDispV(Voluntario v, boolean i) {
        v.setDisponibilidade(i);
        this.voluntarios.addConta(v);
    }

    public double entregaEnc(Voluntario v,String enc) {
        double x,y;
        double dist;
        Encomenda e = this.encomendas.getEncomendaByCod(enc);
        Utilizador u = (Utilizador) this.utilizadores.getContaByCode(e.getCodUtil());
        Loja loja = (Loja)this.lojas.getContaByCode(e.getCodLoja());
        u.addToEncTransp();
        this.utilizadores.addConta(u);
        x = u.getGPSx();
        y = u.getGPSy();
        dist = Point.distance(x, y, v.getGPSx(), v.getGPSy());
        v.setGPS(x,y);
        v.setEncAceite("");
        v.setDisponibilidade(true);
        this.voluntarios.addConta(v);
        e.setFoiEntregue(true);
        e.setDistPercorrida(dist);
        this.encomendas.removeOld(e.getCodEnc());
        this.encomendas.addEnc(e.clone());
        double tempo = (dist/v.getVelocidade())+ ((Loja)this.lojas.getContaByCode(e.getCodLoja())).tempoEspera(e.getCodEnc())+atraso();
        loja.remove(enc);
        this.lojas.addConta(loja.clone());
        return tempo;
    }

    

    public boolean podeTranportar(String enc, String vol) {
        Encomenda e = this.encomendas.getEncomendaByCod(enc);
        String u = e.getCodUtil();
        String l = e.getCodLoja();
        Loja loja = (Loja) this.lojas.getContaByCode(l);
        Utilizador util = (Utilizador) this.utilizadores.getContaByCode(u);
        Voluntario v = (Voluntario) this.voluntarios.getContaByCode(vol);
        return (Point.distance(loja.getGPSx(), loja.getGPSy(), util.getGPSx(), util.getGPSy()) < v.getRaio()
                    && Point.distance(loja.getGPSx(), loja.getGPSy(), v.getGPSx(), v.getGPSy()) < v.getRaio()
                    && ((v.aceitoTransporteMedicamentos() == e.aceitoTransporteMedicamentos())||v.aceitoTransporteMedicamentos() && !e.aceitoTransporteMedicamentos()) 
                    && e.getFoiSolicitada() && !e.getFoiEntregue()
                    && e.getQuemTransportou().equals(""));
    }

    public Map<String, Double> transpInfo(Voluntario v) {
        Map<String, Double> res = new HashMap<>();
        for (Encomenda e : this.encomendas.getEnc()) {
            String l = e.getCodLoja();
            String u = e.getCodUtil();
            Utilizador util = (Utilizador) this.utilizadores.getContaByCode(u);
            Loja loja = (Loja) this.lojas.getContaByCode(l);
            if (podeTranportar(e.getCodEnc(), v.getCodigo())) {
                double dist = Point.distance(loja.getGPSx(), loja.getGPSy(), util.getGPSx(), util.getGPSy()) + Point.distance(loja.getGPSx(), loja.getGPSy(), v.getGPSx(), v.getGPSy());        
                res.put(e.getCodEnc(), (dist/v.getVelocidade())+loja.tempoEspera(e.getCodEnc())+atraso());
            }
        }
        return res;
    }

    // Transportadora
    
    public boolean podeTransportarT(String enc, String transp) {
        Encomenda e = this.encomendas.getEncomendaByCod(enc);
        String u = e.getCodUtil();
        Utilizador util = (Utilizador) this.utilizadores.getContaByCode(u);
        String l = e.getCodLoja();
        Loja loja = (Loja) this.lojas.getContaByCode(l);
        Transportadora t = (Transportadora) this.transportadoras.getContaByCode(transp);
        return ((Point.distance(loja.getGPSx(), loja.getGPSy(), util.getGPSx(), util.getGPSy()) < t.getRaio() 
                && Point.distance(loja.getGPSx(), loja.getGPSy(), t.getGPSx(), t.getGPSy()) < t.getRaio() 
                && ((t.aceitoTransporteMedicamentos() == e.aceitoTransporteMedicamentos())||t.aceitoTransporteMedicamentos() && !e.aceitoTransporteMedicamentos())
                && e.getQuemTransportou().equals(""))
                 && !e.getFoiEntregue()
                 && e.getFoiSolicitada());
    }
    
    public double precoTransporte(String enc, Transportadora t) {
        Encomenda e = this.encomendas.getEncomendaByCod(enc);
        String l = e.getCodLoja();
        String u = e.getCodUtil();
        Utilizador util = (Utilizador) this.utilizadores.getContaByCode(u);
        Loja loja = (Loja) this.lojas.getContaByCode(l);
        double dist = Point.distance(loja.getGPSx(), loja.getGPSy(), util.getGPSx(), util.getGPSy()) + Point.distance(loja.getGPSx(), loja.getGPSy(), t.getGPSx(), t.getGPSy());
        return dist * t.getPrecoPorKm()*(t.getPrecoPeso()*e.getPeso());
    }
    
    public void alteraDispT(String code, int i) {
        Transportadora v = (Transportadora) this.transportadoras.getContaByCode(code);
        if (i == 1) v.setDisponibilidade(true);
        if (i == 2) v.setDisponibilidade(false);
        this.transportadoras.addConta(v);
    }
    
    public void addToTransp(String codEnc,String transportadora){
        Transportadora t = (Transportadora)this.transportadoras.getContaByCode(transportadora);
        t.addEncomenda(codEnc);
        this.transportadoras.addConta(t);
    }
    
    
    
    //Transportadora realiza a rota e devolve uma map com a encomenda,o tempo que demorou e o custo do transporte
    public Map<String,AbstractMap.SimpleEntry<Double, Double>> entregaEncs(Transportadora t) {
        List<String> l = t.getEncAceites();
        double[] distancias = new double[l.size()];
        List<Encomenda> le = new ArrayList<>();
        Map<String,AbstractMap.SimpleEntry<Double, Double>> map = new HashMap<>();
        for (String e : l) {
            Encomenda enc = this.encomendas.getEncomendaByCod(e);
            le.add(enc);
        }
        Comparator<Encomenda> compareByDist = Comparator.comparingDouble((Encomenda o) -> this.lojas.getContaByCode(o.getCodLoja()).calcDist(t.getGPSx(), t.getGPSy()));
        le.sort(compareByDist);
        for(Encomenda e: le) {
            Loja loja = (Loja) this.lojas.getContaByCode(e.getCodLoja());
            double dist = loja.calcDist(t.getGPSx(),t.getGPSy());
            t.setGPS(loja.getGPSx(),loja.getGPSy());
            distancias[le.indexOf(e)] = dist;
            
        }
        Comparator<Encomenda> compareByDistU = Comparator.comparingDouble((Encomenda o) -> this.utilizadores.getContaByCode(o.getCodUtil()).calcDist(t.getGPSx(), t.getGPSy()));
        le.sort(compareByDistU);
        for(Encomenda e: le) {
            Utilizador util = (Utilizador) this.utilizadores.getContaByCode(e.getCodUtil());
            //adiciona uma encomenda transportada
            util.addToEncTransp();
            this.utilizadores.addConta(util);
            double dist = util.calcDist(t.getGPSx(),t.getGPSy());
            t.setGPS(util.getGPSx(),util.getGPSy());
            distancias[le.indexOf(e)] += dist;
        }
        int i = 0;
        double custo = t.getPrecoPorKm();
        double custo1 = t.getPrecoPeso();
        double vel = t.getVelocidade();
        for (Encomenda e : le){
            double distanc = distancias[i++];
            e.setFoiEntregue(true);
            e.setDistPercorrida(distanc);
            t.addToKmPercorridos(distanc);
            this.encomendas.removeOld(e.getCodEnc());
            this.encomendas.addEnc(e.clone());
            double tempo = (distanc/vel)+ ((Loja)this.lojas.getContaByCode(e.getCodLoja())).tempoEspera(e.getCodEnc())+atraso();
            AbstractMap.SimpleEntry<Double, Double> me = new AbstractMap.SimpleEntry<>(distanc/vel,distanc*custo+(e.getPeso()*custo1));
            map.put(e.getCodEnc(),me);
            Loja loja = (Loja) this.lojas.getContaByCode(e.getCodLoja());
            loja.remove(e.getCodEnc());
            this.lojas.addConta(loja);
            
        }
        //apagar todas as encomendas da transportadora
        t.setEncAceites(new ArrayList<>());
        t.setDisponibilidade(true);
        this.transportadoras.addConta(t);
        
        return map;
    }
    
    public Conta getContaFromCredentials(String email, String password) {
        Conta conta = this.utilizadores.getContaByEmail(email);
        if (conta != null && conta.checkPassword(password)) return conta;

        conta = this.voluntarios.getContaByEmail(email);
        if (conta != null && conta.checkPassword(password)) return conta;

        conta = this.transportadoras.getContaByEmail(email);
        if (conta != null && conta.checkPassword(password)) return conta;

        conta = this.lojas.getContaByEmail(email);
        if (conta != null && conta.checkPassword(password)) return conta;

        return null;
    }

    public Map<String, List<Double>> transpInfoT(Transportadora v) {
        Map<String, List<Double>> res = new HashMap<>();
        for (Encomenda e : this.encomendas.getEnc()) {
            String l = e.getCodLoja();
            String u = e.getCodUtil();
            Utilizador util = (Utilizador) this.utilizadores.getContaByCode(u);
            Loja loja = (Loja) this.lojas.getContaByCode(l);
            AbstractMap.SimpleEntry<String, String> p = new AbstractMap.SimpleEntry<>(e.getCodEnc(),v.getCodigo());
            if (podeTransportarT(e.getCodEnc(), v.getCodigo()) && !this.pedidosTransporte.contains(p)) {
                List<Double> list = new ArrayList<>();
                double d = Point.distance(loja.getGPSx(), loja.getGPSy(), v.getGPSx(), v.getGPSy())+Point.distance(loja.getGPSx(), loja.getGPSy(), util.getGPSx(), util.getGPSy());
                list.add(d);
                list.add(loja.tempoEsperaTeorico(e.getCodEnc()));
                list.add(d * v.getPrecoPorKm());
                res.put(e.getCodEnc(), list);
            }
        }
        return res;
    }
    
    public void despacharEnc(Loja l,String cod){
        Encomenda e =this.encomendas.getEncomendaByCod(cod);
        l.despacharEnc(e.getCodEnc());
        l.remove(e.getCodEnc());
    }


    public void addConta(Conta conta) {
        if (conta instanceof Utilizador) this.utilizadores.addConta(conta);
        else if (conta instanceof Voluntario) this.voluntarios.addConta(conta);
        else if (conta instanceof Loja) this.lojas.addConta(conta);
        else this.transportadoras.addConta(conta);
    }

    public Estado clone() {
        return new Estado(this);
    }

    public void saveEstado() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Estado.obj"));
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }


    //carregar de ficheiro objeto
    public void loadEstadoObj(String file) throws IOException, ClassNotFoundException {
        Estado e = loadAux(file);
        this.utilizadores = e.utilizadores;
        this.voluntarios = e.voluntarios;
        this.lojas = e.lojas;
        this.transportadoras = e.transportadoras;
        this.encomendas = e.encomendas;
        this.pedidosTransporte = e.pedidosTransporte;
    }

    public Estado loadAux(String file) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Estado e = (Estado) ois.readObject();
        ois.close();
        return e;

    }

    /*--------------------FUNCOES QUE ESTAVAM NA PARSE-----------------------*/
    //ler do ficheiro log


    public void loadEstadoLogs() {

        List<String> linhas = lerFicheiro("logs.txt");
        List<Voluntario> listaVol = new ArrayList<>();
        List<Transportadora> listaTransportadora = new ArrayList<>();
        List<Loja> listaLoja = new ArrayList<>();
        List<Encomenda> listaEnc = new ArrayList<>();
        List<String> listaAceite = new ArrayList<>();

        for (String linha : linhas) {
            String[] linhaPartida = linha.split(":", 2);
            switch (linhaPartida[0]) {
                case "Utilizador":
                    Conta u = parseUtilizador(linhaPartida[1]);
                    //System.out.println(u.toString());
                    this.utilizadores.addConta(u);
                    break;
                case "Loja":
                    Loja l = parseLoja(linhaPartida[1]);
                    //System.out.println(l.toString());
                    listaLoja.add(l);
                    break;
                case "Voluntario":
                    Voluntario v = parseVoluntario(linhaPartida[1]);
                    //System.out.println(v.toString());
                    listaVol.add(v);
                    break;
                case "Transportadora":
                    Transportadora t = parseTransportadora(linhaPartida[1]);
                    //System.out.println(t.toString());
                    listaTransportadora.add(t);
                    break;
                case "Encomenda":
                    Encomenda enc = parseEnc(linhaPartida[1]);
                    listaEnc.add(enc);
                    break;
                case "Aceite":
                    listaAceite.add(linhaPartida[1]);
                    break;
                default:
                    System.out.println("Linha invalida.");
                    break;
            }
        }


        putEncInQueues(listaLoja, listaEnc);

        //Distribuir aleatoriamente encomendas aceites pelas entidades transportadoras(tendo em atencao o raio)
        distributeEncAceites(listaEnc, listaAceite, listaTransportadora, listaVol, listaLoja);

        listaVol.forEach(a -> this.voluntarios.addConta(a));
        listaTransportadora.forEach(a -> this.transportadoras.addConta(a));
        listaLoja.forEach(a -> this.lojas.addConta(a));
        listaEnc.forEach(a -> this.encomendas.addEnc(a));

        
        System.out.println("----Ficheiros carregados!---");

    }

    //Meio estranha.A ideia é que faça com que algumas transportadoras transportem mais que uma encomenda
    public void distributeEncAceites(List<Encomenda> encomendas, List<String> encAceites, List<Transportadora> t, List<Voluntario> vols, List<Loja> l) {
        for (String s : encAceites) {
            boolean found = false;
            Encomenda e = encomendas.stream().filter(a -> a.getCodEnc().equals(s)).findFirst().orElse(null);
            Loja loja = l.stream().filter(a -> a.getCodigo().equals(e.getCodLoja())).findFirst().orElse(null);
            for (Voluntario vol : vols) {
                double raioV = vol.getRaio();
                Point2D ponto = new Point2D.Double(vol.getGPSx(), vol.getGPSy());
                if (vol.getDisponibilidade() && ponto.distance(loja.getGPSx(), loja.getGPSy()) <= raioV) {
                    e.setQuemTransportou(vol.getCodigo());
                    vol.addEncomenda(s);
                    found = true;
                    break;
                }
            }
            if(!found) {
                for (Conta c : t) {
                    Transportadora tps = (Transportadora) c;
                    double raioT = tps.getRaio();
                    Point2D ponto = new Point2D.Double(tps.getGPSx(), tps.getGPSy());
                    if (ponto.distance(loja.getGPSx(), loja.getGPSy()) <= raioT) {
                        tps.setMaxCapacidade(tps.getEncAceites().size() + 1);
                        e.setQuemTransportou(tps.getCodigo());
                        tps.addEncomenda(s);
                        break;
                    }
                }
            }
        }
    }


    public void putEncInQueues(List<Loja> lj, List<Encomenda> encs) {
        for (Loja loj : lj) {
            List<Encomenda> aux = encs.stream().filter(a -> a.getCodLoja().equals(loj.getCodigo())).collect(Collectors.toList());
            loj.setFilaEspera(aux);
        }
    }


    public Utilizador parseUtilizador(String input) {
        String[] campos = input.split(",");
        String nome = campos[1];
        String codUtilizador = campos[0];
        double x = Double.parseDouble(campos[2]);
        double y = Double.parseDouble(campos[3]);
        return new Utilizador(codUtilizador, nome, x, y);
    }

    public Loja parseLoja(String input) {
        String[] campos = input.split(",");
        String codLoja = campos[0];
        String nomeLoja = campos[1];
        double x = Double.parseDouble(campos[2]);
        double y = Double.parseDouble(campos[3]);
        // dados por omissao
        return new Loja(codLoja, nomeLoja, x, y);
    }

    public Voluntario parseVoluntario(String input) {
        String[] campos = input.split(",");
        String codVol = campos[0];
        String nomeVol = campos[1];
        double x = Double.parseDouble(campos[2]);
        double y = Double.parseDouble(campos[3]);
        double raio = Double.parseDouble(campos[4]);
        //dados por omissao
        return new Voluntario(codVol, nomeVol, x, y, raio);
    }

    public Transportadora parseTransportadora(String input) {
        String[] campos = input.split(",");
        String codTransp = campos[0];
        String nomeTransp = campos[1];
        double x = Double.parseDouble(campos[2]);
        double y = Double.parseDouble(campos[3]);
        String nif = campos[4];
        double raio = Double.parseDouble(campos[5]);
        double preco = Double.parseDouble(campos[6]);
        //dados por omissao
        return new Transportadora(codTransp, nomeTransp, x, y, nif, raio, preco);
    }

    public Encomenda parseEnc(String input) {
        String[] campos = input.split(",");
        String cod = campos[0];
        String nome = campos[1];
        String loja = campos[2];
        double peso = Double.parseDouble(campos[3]);
        Encomenda e = new Encomenda(cod, nome, loja, peso);
        int i = 4;
        while (i < campos.length - 1) {
            String codProd = campos[i++];
            String codDesc = campos[i++];
            double quant = Double.parseDouble(campos[i++]);
            double val = Double.parseDouble(campos[i++]);
            LinhaEncomenda le = new LinhaEncomenda(codProd, codDesc, quant, val);
            e.addProduto(le.clone());
        }
        return e;

    }

    public List<String> lerFicheiro(String nomeFich) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8);
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
        return lines;
    }
}
