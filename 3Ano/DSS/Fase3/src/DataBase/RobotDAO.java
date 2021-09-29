package DataBase;

import GestRobot.Aresta;
import GestRobot.Robot;
import GestRobot.Rota;
import GestRobot.Vertice;

import java.sql.*;
import java.util.*;


public class RobotDAO implements Map<String, Robot> {
    private static RobotDAO singleton = null;

    //Construtor. É aqui que a tabela dos robots na base de dados é criada.
    // Lança uma excepção caso haja algum problema.
    private RobotDAO() {

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS rotas (" +
                    "CodRota varchar(10) NOT NULL PRIMARY KEY," +
                    "Origem varchar(10) DEFAULT 'n/d', foreign key(Origem) references vertices(Codigo)," +
                    "Destino varchar(10) DEFAULT NULL, foreign key(Destino) references vertices(Codigo)," +
                    "CodPalete varchar(10) DEFAULT NULL, foreign key(CodPalete) references paletes(Codigo))";
            stm.executeUpdate(sql);

            String sql1 = "CREATE TABLE IF NOT EXISTS robots (" +
                    "CodigoRobot varchar(10) NOT NULL PRIMARY KEY," +
                    "Estado int(5) NOT NULL DEFAULT 0," +
                    "Rota varchar(10) DEFAULT NULL, foreign key(Rota) references rotas(CodRota)," +
                    "Localizacao varchar(10) /*NOT NULL*/ DEFAULT '', foreign key(Localizacao) references vertices(Codigo))";
            stm.executeUpdate(sql1);

            /*Esta tabela é responsável pela relação entre rotas e arestas, uma vez que se trata de uma relação de N:N */
            sql = "CREATE TABLE IF NOT EXISTS arestasRotas (" +
                    "CodRota varchar(10) NOT NULL , foreign key(CodRota) references rotas(CodRota)," +
                    "CodAresta varchar(10) NOT NULL , foreign key(CodAresta) references arestas(Codigo)," +
                    "Pos int(2) NOT NULL DEFAULT 0," +
                    "PRIMARY KEY (CodRota,CodAresta))";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    //Implementação do padrão Singleton
    //devolve a instância única desta classe
    public static RobotDAO getInstance() {
        if (RobotDAO.singleton == null) {
            RobotDAO.singleton = new RobotDAO();
        }
        return RobotDAO.singleton;
    }

    @Override
    //Este método devolve o número de entradas na tabela na base de dados
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM robots")) {
            if(rs.next()) {
                i = rs.getInt(1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    @Override
    //Método que devolve se uma tabela está vazia ou não
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    //Método que devolve se o código de um dado robot se encontra registado na base de dados
    //Lança exceção caso ocorra algum problema na procura.
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT CodigoRobot FROM robots WHERE CodigoRobot ='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    //Método que devolve se uma dado robot se encontra registado na base de dados
    public boolean containsValue(Object value) {
        Robot a = (Robot) value;
        return this.containsKey(a.getCodRobot());
    }

    //Método auxiliar da função get. Criado por questões de organização de código
    public Vertice getVertice(String cod) throws SQLException {
        Vertice v = null;
        try(Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet reV = stm.executeQuery("SELECT * FROM vertices WHERE Codigo='"+cod+ "'")) {
            if (reV.next()) {  // A chave existe na tabela
                v = new Vertice(reV.getString("Codigo"), reV.getString("Designacao"), reV.getInt("Ocupacao"));
            }
        }
        return v;
    }

    @Override
    //Método que devolve a aresta cujo código é o passado como argumento
    //Lança exceção caso haja algum problema na procura na base de dados
    public Robot get(Object key) {
        Robot t = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM robots WHERE CodigoRobot ='"+key+"'")) {
            if (rs.next()) {  // A chave existe na tabela

                //Reconstruir a ArestasRota
                Collection<Aresta> caminho = new ArrayList<>();
                String sql = "SELECT * FROM arestasRotas WHERE CodRota ='"+rs.getString("Rota")+"'ORDER BY Pos";
                try (Connection conn3 = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                     Statement stm3 = conn3.createStatement();
                     ResultSet rs3 = stm3.executeQuery(sql)) {
                    while (rs3.next()) {
                        caminho.add(getAresta(rs3.getString("CodAresta"))); //CUIDADO NULL
                    }
                }

                // Reconstruir a Rota
                Rota r = null;
                sql = "SELECT * FROM rotas WHERE CodRota ='"+rs.getString("Rota")+"'";
                try (Connection conn2 = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                     Statement stm2 = conn2.createStatement();
                     ResultSet rsa = stm2.executeQuery(sql)) {
                    if (rsa.next()) {  // Encontrou a rota
                        r = new Rota(rs.getString("Rota"),
                                getVertice(rsa.getString("Origem")),
                                getVertice(rsa.getString("Destino")),
                                rsa.getString("CodPalete"),
                                caminho);
                    } else {
                        // BD inconsistente!! Rota não existe - tratar com excepções.
                    }
                }
                // Reconstruir o Robot com os dados obtidos
                t = new Robot(rs.getString("CodigoRobot"),rs.getInt("Estado"),getVertice(rs.getString("Localizacao")),r);
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    //Método auxiliar da função get. Criado por questões de organização de código
    public Aresta getAresta(String cod) throws SQLException {
        Aresta a = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet reV = stm.executeQuery("SELECT * FROM arestas WHERE Codigo='"+cod+ "'")){
            if(reV.next()){
                a = new Aresta(getVertice(reV.getString("VerticeFinal")), getVertice(reV.getString("VerticeInicial")), reV.getString("Codigo"), reV.getFloat("Distancia"));
            }
        }
        return a;
    }

    /*private Collection<Aresta> getCaminhoRota(String tid, Statement stm) throws SQLException {
        Collection<Aresta> caminho = new HashSet<>();
        String res = get(tid).getRota().getCodRota();
        try (ResultSet rsa = stm.executeQuery("SELECT Codigo FROM arestas WHERE Rota='"+res+"'")) {
            while(rsa.next()) {
                caminho.add(getAresta(rsa.getString("Codigo")));
            }
        }
        return caminho;
    }*/


    @Override
    //Método que adiciona uma entrada á tabela dos robots na base de dados.
    // O código é a identificação do objeto na tabela
    //É lançada exceção caso haja algum problema relativo á database
    public Robot put(String key, Robot robot) {
        Robot res = null;
        Rota rota = robot.getRota();

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            // Atualizar a Rota
            if(rota==null){
                Robot velho = get(key);
                Rota rV = velho.getRota();
                //Retiramos a rota do robot, porque ele já a percorreu
                stm.executeUpdate(
                        "INSERT INTO robots " +
                                "VALUES ('" + robot.getCodRobot() + "', '" +
                                robot.getEstado() + "', " +
                                "NULL" + ", '" +
                                robot.getLocalizacao().getCodVertice() + "') " +
                                "ON DUPLICATE KEY UPDATE Estado=Values(Estado), " +
                                "Rota=Values(Rota), " +
                                "Localizacao=Values(Localizacao)");
                //Apagar todos as arestas que pertençam à rota que queremos eliminar
                stm.executeUpdate("DELETE FROM arestasRotas WHERE CodRota='"+ rV.getCodRota()+"'");
                //Apaga a rota da tabela das rotas
                stm.executeUpdate("DELETE FROM rotas WHERE CodRota='"+ rV.getCodRota()+"'");
            }
            else if(rota.getDestino()==null&&rota.getCaminho()==null){
                stm.executeUpdate(
                        "INSERT INTO rotas " +
                                "VALUES ('" + rota.getCodRota() + "', '" +
                                rota.getOrigem().getCodVertice() + "', " +
                                "NULL" + ", '" +
                                rota.getCodPalete() + "') " +
                                "ON DUPLICATE KEY UPDATE Origem=Values(Origem)," +
                                "Destino=Values(Destino)," +
                                "CodPalete=Values(CodPalete)");

                // Atualizar o Robot
                stm.executeUpdate(
                        "INSERT INTO robots " +
                                "VALUES ('" + robot.getCodRobot() + "', '" +
                                robot.getEstado() + "', '" +
                                rota.getCodRota() + "', '" +
                                robot.getLocalizacao().getCodVertice() + "') " +
                                "ON DUPLICATE KEY UPDATE Estado=Values(Estado), " +
                                "Rota=Values(Rota), " +
                                "Localizacao=Values(Localizacao)");
            }
            else {
                stm.executeUpdate(
                        "INSERT INTO rotas " +
                                "VALUES ('" + rota.getCodRota() + "', '" +
                                rota.getOrigem().getCodVertice() + "', '" +
                                rota.getDestino().getCodVertice() + "', '" +
                                rota.getCodPalete() + "') " +
                                "ON DUPLICATE KEY UPDATE Origem=Values(Origem)," +
                                "Destino=Values(Destino)," +
                                "CodPalete=Values(CodPalete)");

                //Apagar todos as arestas que pertençam à rota que queremos dar update
                Robot velho = get(key);
                Rota rV = velho.getRota();
                stm.executeUpdate("DELETE FROM arestasRotas WHERE CodRota='"+ rV.getCodRota()+"'");
                Collection<Aresta> caminho = rota.getCaminho();
                int i = 1;
                for(Aresta a : caminho){
                    stm.executeUpdate(
                            "INSERT INTO arestasRotas " +
                                    "VALUES ('" + rota.getCodRota() + "', '" +
                                    a.getCodAresta() + "', '" + i +"') " +
                                    "ON DUPLICATE KEY UPDATE CodRota=Values(CodRota)," +
                                    "CodAresta=Values(CodAresta)," + "Pos=Values(Pos)");
                    /*stm.executeUpdate(
                            "UPDATE arestasRotas SET Pos = Pos + 1");*/
                    i++;
                }
                // Atualizar o Robot
                stm.executeUpdate(
                        "INSERT INTO robots " +
                                "VALUES ('" + robot.getCodRobot() + "', '" +
                                robot.getEstado() + "', '" +
                                rota.getCodRota() + "', '" +
                                robot.getLocalizacao().getCodVertice() + "') " +
                                "ON DUPLICATE KEY UPDATE Estado=Values(Estado), " +
                                "Rota=Values(Rota), " +
                                "Localizacao=Values(Localizacao)");
            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    //Método que remove da tabela dos robots na database, o robot cujo código é passado como argumento.
    //É necessário por null em todas as referencias do robot removido em outras tabelas
    //Lançada exceção caso haja algum problema na base de dados
    public Robot remove(Object key) {
        Robot t = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            stm.executeUpdate("DELETE FROM arestasRotas WHERE CodRota='"+ t.getRota().getCodRota()+"'");
            stm.executeUpdate("DELETE FROM robots WHERE CodigoRobot='"+key+"'");
            stm.executeUpdate("DELETE FROM rotas WHERE CodRota='"+ t.getRota().getCodRota()+"'");

        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    @Override
    //Método que adiciona de uma unica vez vários robots á tabela na base de dados
    public void putAll(Map<? extends String, ? extends Robot> map) {
        for(Robot t : map.values()) {
            this.put(t.getCodRobot(), t);
        }
    }

    @Override
    //Método que elimina todas as entradas na tabela robots.
    //É necessário por null em todas as referencias do robot removido em outras tabelas
    //É lançada exceção caso haja algum problema com a base de dados
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.execute("UPDATE paletes SET InRobot=false");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stm.execute("TRUNCATE arestasRotas");
            stm.execute("TRUNCATE rotas");
            stm.executeUpdate("TRUNCATE robots");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    //Método que devolve um set com todos os códigos de robots presentes na base de dados
    //É lançada exceção caso haja algum problema com a base de dados
    public Set<String> keySet() {
        Set<String> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT CodigoRobot FROM robots")) {
            while (rs.next()) {
                col.add(rs.getString("CodigoRobot"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    @Override
    //Método que devolve uma collection com todos os objetos robot presentes na base de dados
    //É lançada exceção caso haja algum problema com a base de dados
    public Collection<Robot> values() {
        Collection<Robot> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT CodigoRobot FROM robots")) { // ResultSet com os ids de todas as turmas
            while (rs.next()) {
                String idt = rs.getString("CodigoRobot"); // Obtemos um id de turma do ResultSet
                Robot t = this.get(idt);                    // Utilizamos o get para construir as turmas uma a uma
                res.add(t);                                 // Adiciona a turma ao resultado.
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    //Método que devolve um set com todos "pares" formados pelo código de robot e o objeto robot correspondente
    //É lançada exceção caso haja algum problema com a base de dados
    public Set<Entry<String, Robot>> entrySet() {
        Map.Entry<String,Robot> entry;
        HashSet<Map.Entry<String, Robot>> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT CodigoRobot FROM robots")) {
            while (rs.next()) {
                entry =  new AbstractMap.SimpleEntry<>(rs.getString("CodigoRobot"),this.get(rs.getString("CodigoRobot")));
                col.add(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }
}