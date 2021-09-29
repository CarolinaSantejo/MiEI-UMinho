package DataBase;

import GestRobot.Aresta;
import GestRobot.Vertice;


import java.sql.*;
import java.util.*;

public class ArestaDAO implements Map<String,Aresta>{
    private static ArestaDAO singleton = null;

    //Construtor. É aqui que a tabela das arestas na base de dados é criada.
    // Lança uma excepção caso haja algum problema.
    private ArestaDAO() {

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS arestas (" +
                    "Codigo varchar(10) NOT NULL PRIMARY KEY," +
                    "Distancia float(10) NOT NULL DEFAULT 0," +
                    "VerticeInicial varchar(10) DEFAULT 'n/d', foreign key(VerticeInicial) references vertices(Codigo),"+
                    "VerticeFinal varchar(10) DEFAULT 'n/d', foreign key(VerticeFinal) references vertices(Codigo))";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    //Implementação do padrão Singleton
    //devolve a instância única desta classe
    public static ArestaDAO getInstance() {
        if (ArestaDAO.singleton == null) {
            ArestaDAO.singleton = new ArestaDAO();
        }
        return ArestaDAO.singleton;
    }


    @Override
   //Este método devolve o número de entradas na tabela na base de dados
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM arestas")) {
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
    //Método que devolve se o código de uma dada aresta se encontra registada na base de dados
    //Lança exceção caso ocorra algum problema na procura.
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT Codigo FROM arestas WHERE Codigo ='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    //Método que devolve se uma dada aresta se encontra registada na base de dados
    public boolean containsValue(Object value) {
        Aresta a = (Aresta) value;
        return this.containsKey(a.getCodAresta());
    }

    @Override
    //Método que devolve a aresta cujo código é o passado como argumento
    //Lança exceção caso haja algum problema na procura na base de dados
    public Aresta get(Object key) {
        Aresta a = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM arestas WHERE Codigo='"+key+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                Vertice vI = getVertice(rs.getString("VerticeInicial"));
                Vertice vF = getVertice(rs.getString("VerticeFinal"));
                a = new Aresta(vF,vI ,rs.getString("Codigo"), rs.getInt("Distancia"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
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
    //Adiconar entrada á tabela das arestas na base de dados. O código é a identificação do objeto na tabela
    //É lançada exceção caso haja algum problema relativo á database
    public Aresta put(String key, Aresta value) {
        Aresta res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            if(this.containsKey(key)){
                res = this.get(key);
            }
            else {
                // Actualizar o aluno
                stm.executeUpdate(
                        "INSERT INTO arestas VALUES ('" + value.getCodAresta() + "', '" + value.getDist() + "', '" + value.getVerticeInicial().getCodVertice() + "', '" + value.getVerticeFinal().getCodVertice() + "') " +
                                "ON DUPLICATE KEY UPDATE Distancia=VALUES(Distancia), VerticeInicial=VALUES(VerticeInicial), VerticeFinal=VALUES(VerticeFinal)");
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }


    @Override
    //Método que remove da tabela das arestas na database, a aresta cujo código é passado como argumento.
    //É necessário por null em todas as referencias da aresta removida em outras tabelas
    //Lançada exceção caso haja algum problema na base de dados
    public Aresta remove(Object key) {
        Aresta t = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM arestas WHERE Codigo='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }


    @Override
    //Método que adiciona de uma unica vez várias arestas á tabela na base de dados
    public void putAll(Map<? extends String, ? extends Aresta> vertices) {
        for(Aresta t : vertices.values()) {
            this.put(t.getCodAresta(), t);
        }
    }


    @Override
    //Método que elimina todas as entradas na tabela arestas.
    //É necessário por null em todas as referencias da aresta removida em outras tabelas
    //É lançada exceção caso haja algum problema com a base de dados
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.execute("UPDATE robots SET Rota=NULL");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stm.executeUpdate("TRUNCATE rotas");
            stm.executeUpdate("TRUNCATE arestasRotas");
            stm.executeUpdate("TRUNCATE arestas");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

    }

    @Override
    //Método que devolve um set com todos os códigos de arestas presentes na base de dados
    //É lançada exceção caso haja algum problema com a base de dados
    public Set<String> keySet() {
        Set<String> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM arestas")) {
            while (rs.next()) {
                col.add(rs.getString("Codigo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    @Override
    //Método que devolve uma collection com todos os objetos aresta presentes na base de dados
    //É lançada exceção caso haja algum problema com a base de dados
    public Collection<Aresta> values() {
        Collection<Aresta> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM arestas")) {
            while (rs.next()) {
                col.add(this.get(rs.getString("Codigo")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }


    @Override
    //Método que devolve um set com todos "pares" formados pelo código de aresta e o objeto aresta correspondente
    //É lançada exceção caso haja algum problema com a base de dados
    public Set<Map.Entry<String, Aresta>> entrySet() {
        Map.Entry<String,Aresta> entry;
        HashSet<Map.Entry<String, Aresta>> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM arestas")) {
            while (rs.next()) {
                entry =  new AbstractMap.SimpleEntry<>(rs.getString("Codigo"),this.get(rs.getString("Codigo")));
                col.add(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

}