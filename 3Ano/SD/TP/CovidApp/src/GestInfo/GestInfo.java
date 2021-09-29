package GestInfo;

import Exceptions.*;

import java.time.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;


public class GestInfo {
    private ReentrantLock lock ;
    private Map<String, User> logs;
    private Map<String, User> infetados;
    private Mapa mapa;

    public GestInfo() {
        this.lock = new ReentrantLock();
        this.logs = new HashMap<>();
        this.infetados = new HashMap<>();
        this.mapa = new Mapa();
    }

    public boolean verificaCoordenadas(int x,int y) throws CoordenadasIncorretas {
        int N = mapa.getN();
        if (x<0 || x>= N || y<0 || y>= N) throw new CoordenadasIncorretas("Coordenadas incorretas!");
        return true;
    }

    public boolean verificaUserCoordenadas(int x,int y,String userID) throws MesmaLocalizacao {
        try {
            lock.lock();
            User u = logs.get(userID);
            if (u.getX() == x && u.getY() == y) throw new MesmaLocalizacao("O utilizador já se encontra nas coordenadas fornecidas!");
            return true;
        }
        finally {
            lock.unlock();
        }
    }

    public User parseLine (String userInput) throws CoordenadasIncorretas {
        String[] tokens = userInput.split(" ");
        int res= 0;
        if (tokens[2].equals("Y")) res=1;
        int x = Integer.parseInt(tokens[3]);
        int y = Integer.parseInt(tokens[4]);
        verificaCoordenadas(x,y);
        return new User(
                tokens[0],
                tokens[1],
                x,
                y,
                res);
    }

    public String registaUser(String data) throws UserJaExiste, CoordenadasIncorretas {
        User u = parseLine(data);
        addUser(u);
        atualizaSomaMapa(u);
        try {
            u.l.lock();
            return u.getUsername();
        }
        finally {
            u.l.unlock();
        }

    }

    public void addUser(User u) throws UserJaExiste {
        try {
            lock.lock();
            if (logs.containsKey(u.getUsername())) throw new UserJaExiste("Utilizador já esta registado");
            logs.put(u.getUsername(), u);
        }
        finally {
            lock.unlock();
        }
    }

    public void removeUser(String id){
        lock.lock();
        User u = logs.get(id);
        logs.remove(id);
        lock.unlock();
        mapa.getLocalizacao(u.getX(),u.getY()).retirar();

    }

    public boolean verificaUser(String userID, String pass) throws UsernameNaoExiste, PassIncorreta {
        try {
            lock.lock();
            if (!logs.containsKey(userID)) throw new UsernameNaoExiste("Nome de utilizador não existe");
            String passW = logs.get(userID).getPassword();
            if (!passW.equals(pass)) throw new PassIncorreta("Password incorreta");
            return true;
        }
        finally {
            lock.unlock();
        }
    }

    public int isSpecial(String userID){
        try{
            lock.lock();
            User u = logs.get(userID);
            return u.getFlagEspecial();
        }finally {
            lock.unlock();
        }
    }

    public User getUser(String userId){
        try {
            lock.lock();
            return logs.get(userId);
        }
        finally {
            lock.unlock();
        }
    }

    public void novaLocalizacao(String data) throws MesmaLocalizacao, CoordenadasIncorretas {
        String[] tokens = data.split(" ");
        lock.lock();
        User u = logs.get(tokens[0]);
        String userID = u.getUsername();
        lock.unlock();
        if (verificaUserCoordenadas(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),userID) && verificaCoordenadas(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]))) {
            atualizaReduzMapa(u);
            u.setX(Integer.parseInt(tokens[1]));
            u.setY(Integer.parseInt(tokens[2]));
            atualizaSomaMapa(u);
        }
    }

    /**
     * As operações somar() e retirar() já têm locks e unlocks nas suas classes.
     * Logo não é preciso chamar o lock e o unlock.
     */
    public void atualizaSomaMapa(User u) {
        LocalTime clock = LocalTime.now();
        u.addHistorico(mapa.getLocalizacao(u.getX(), u.getY()), clock);
        mapa.getLocalizacao(u.getX(), u.getY()).somar();
    }

    public void atualizaReduzMapa(User u) {
        mapa.getLocalizacao(u.getX(), u.getY()).retirar();
    }


    public Set<String> verificaInfetados(String userID) {
        try {
            lock.lock();
            Set<String> res = new TreeSet<>();

            User inf = infetados.get(userID);
            Map<LocalTime, Localizacao> h = inf.getHistorico();
            Set<LocalTime> locs = h.keySet();
            List<LocalTime> mainList = new ArrayList<>(locs);

            //Caso o infetado só tenha estado num sítio
            if(mainList.size()==1){
                LocalTime inicio = mainList.get(0);
                for (User u : logs.values()) {
                    Map<LocalTime, Localizacao> hUser = u.getHistorico();
                    //Caso a contaminação tenha decorrido no último lugar onde o user não infetado esteve
                    if(u.getX()== h.get(inicio).getX() && u.getY()==h.get(inicio).getY()){
                        res.add(u.getUsername());
                    }
                    //Caso contrário
                    else {
                        for (LocalTime lt : hUser.keySet()) {
                            if ((lt.isAfter(inicio)) || lt.equals(inicio)) {
                                Localizacao loc = hUser.get(lt);
                                if (loc != null && loc.getX() == h.get(inicio).getX() && loc.getY() == h.get(inicio).getY()) {
                                    res.add(u.getUsername());
                                }
                            }
                        }
                    }
                }
            }
            //Caso o infetado tenha estado em mais de um só sítio
            else {
                for (int i = 0; i < (mainList.size() - 1); i++) {
                    LocalTime inicio = mainList.get(i);
                    LocalTime fim = mainList.get(i + 1);
                    for (User u : logs.values()) {
                        Map<LocalTime, Localizacao> hUser = u.getHistorico();
                        //Caso a contaminação tenha decorrido no último lugar onde o user não infetado esteve
                        if(u.getX()== h.get(inicio).getX() && u.getY()==h.get(inicio).getY() ||
                                u.getX()== h.get(fim).getX() && u.getY()==h.get(fim).getY()) {
                            res.add(u.getUsername());
                        }
                        //Caso contrário
                        else {
                            for (LocalTime lt : hUser.keySet()) {
                                if (((lt.isAfter(inicio)) && (lt.isBefore(fim))) || lt.equals(inicio) || (lt.equals(fim))) {
                                    Localizacao loc = hUser.get(lt);
                                    if (loc != null && ((loc.getX() == h.get(inicio).getX() && loc.getY() == h.get(inicio).getY()) ||
                                            (loc.getX() == h.get(fim).getX() && loc.getY() == h.get(fim).getY()))) {
                                        res.add(u.getUsername());
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return res;
        }
        finally {
            lock.unlock();
        }
    }

    public void addInfetado(User u){
        try {
            lock.lock();
            infetados.put(u.getUsername(), u);
        }
        finally {
            lock.unlock();
        }
    }

    public void userInfetado(String userID){
        User u = getUser(userID);
        u.setInfetado(1);
        removeUser(userID);
        addInfetado(u);
    }

    public Mapa getMapa(){
        try {
            mapa.lock.lock();
            return mapa;
        }
        finally {
            mapa.lock.unlock();
        }
    }


    public Map<Localizacao,List<Integer>> mapaEspecial(){
        Map<Localizacao,List<Integer>> res = new HashMap<>();
        int n = mapa.getN();
        Localizacao[][] localizacoes = mapa.getMapa();

        for (int i = 0;i < n;i++) {
            for (int j = 0; j < n; j++){
                List<Integer> lista = new ArrayList<>();
                lista.add(getNumLocal(localizacoes[i][j], 0));
                lista.add(getNumLocal(localizacoes[i][j],1));
                res.put(localizacoes[i][j],lista);
            }
        }
        return res;
    }

    // tag -> 0 significa que queremos os infetados que visitaram a localizaçao
    // tag -> 1 significa que queremos apenas os que visitaram a localização

    public int getNumLocal(Localizacao l, int tag){
        int n = 0;
        try {
            lock.lock();
            if (tag == 0) {
                for (User u : infetados.values())
                    if (u.getHistorico().values().stream().anyMatch(x -> x.getX() == l.getX() && x.getY() == l.getY())) n++;
            } else {
                for (User u : logs.values())
                    if (u.getHistorico().values().stream().anyMatch(x -> x.getX() == l.getX() && x.getY() == l.getY())
                            || u.getX() == l.getX() && u.getY() == l.getY()) n++;
            }

            return n;
        }
        finally {
            lock.unlock();
        }
    }

    public List<String> usersMapa(){
        Map<Localizacao,List<Integer>> mapa = mapaEspecial();
        List<String> lres = new ArrayList<>();
        for(Localizacao loc : mapa.keySet()) {
            List<Integer> list = mapa.get(loc);
            String res = loc.getX() + " " + loc.getY()+" " + list.get(0) + " " + list.get(1) + " ";
            lres.add(res);
        }
        return lres;
    }

    public String toString() {
        return "logs=" + logs +
                '}' + "\n" + "infet.=" + infetados + '}';
    }
}
