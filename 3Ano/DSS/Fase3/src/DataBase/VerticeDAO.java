package DataBase;

import GestRobot.Vertice;

import java.sql.*;
import java.util.*;

public class VerticeDAO implements Map<String,Vertice>{
    private static VerticeDAO singleton = null;

    //Construtor. É aqui que a tabela dos vértices na base de dados é criada.
    // Lança uma excepção caso haja algum problema.
    private VerticeDAO() {

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS vertices (" +
                    "Codigo varchar(10) NOT NULL PRIMARY KEY," +
                    "Designacao varchar(45) NOT NULL DEFAULT 'n/d'," +
                    "Ocupacao int(5) NOT NULL DEFAULT 0)";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }



    //Implementação do padrão Singleton
    //devolve a instância única desta classe
    public static VerticeDAO getInstance() {
        if (VerticeDAO.singleton == null) {
            VerticeDAO.singleton = new VerticeDAO();
        }
        return VerticeDAO.singleton;
    }


    @Override
    //Este método devolve o número de entradas na tabela na base de dados
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM vertices")) {
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
    //Método que devolve se o código de um dado vertice se encontra registado na base de dados
    //Lança exceção caso ocorra algum problema na procura.
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT Codigo FROM vertices WHERE Codigo ='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    //Método que devolve se uma dado vertice se encontra registado na base de dados
    public boolean containsValue(Object value) {
        Vertice a = (Vertice) value;
        return this.containsKey(a.getCodVertice());
    }

    @Override
    //Método que devolve o vértice cujo código é o passado como argumento
    //Lança exceção caso haja algum problema na procura na base de dados
    public Vertice get(Object key) {
        Vertice a = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM vertices WHERE Codigo='"+key+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                a = new Vertice(rs.getString("Codigo"), rs.getString("Designacao"), rs.getInt("Ocupacao"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }


    @Override
    //Método que adiciona uma entrada á tabela dos vértices na base de dados.
    // O código é a identificação do objeto na tabela
    //É lançada exceção caso haja algum problema relativo á database
    public Vertice put(String key, Vertice value) {
        Vertice res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            // Actualizar o aluno
            stm.executeUpdate(
                    "INSERT INTO vertices VALUES ('" + value.getCodVertice() + "', '" + value.getDesignacao() + "', '" + value.getOcupacao() + "') " +
                            "ON DUPLICATE KEY UPDATE Codigo=VALUES(Codigo), Designacao=VALUES(Designacao), Ocupacao=VALUES(Ocupacao)");

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }


    @Override
    //Método que remove da tabela dos vértices na database, o vértice cujo código é passado como argumento.
    //É necessário por null em todas as referencias do vértice removido em outras tabelas
    //Lançada exceção caso haja algum problema na base de dados
    public Vertice remove(Object key) {
        Vertice t = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM vertices WHERE Codigo='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }


    @Override
    //Método que adiciona de uma unica vez vários vértices á tabela na base de dados
    public void putAll(Map<? extends String, ? extends Vertice> vertices) {
        for(Vertice t : vertices.values()) {
            this.put(t.getCodVertice(), t);
        }
    }

    @Override
    //Método que elimina todas as entradas na tabela vertices.
    //É necessário por null em todas as referencias do vertice removido em outras tabelas
    //É lançada exceção caso haja algum problema com a base de dados
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("UPDATE paletes SET Localizacao= NULL");
            stm.executeUpdate("UPDATE robots SET Localizacao= NULL");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stm.executeUpdate("TRUNCATE arestasRotas");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stm.executeUpdate("TRUNCATE rotas");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stm.executeUpdate("TRUNCATE arestas");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stm.executeUpdate("TRUNCATE vertices");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

    }

    @Override
    //Método que devolve um set com todos os códigos de vértices presentes na base de dados
    //É lançada exceção caso haja algum problema com a base de dados
    public Set<String> keySet() {
        Set<String> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM vertices")) {
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
    //Método que devolve uma collection com todos os objetos vertice presentes na base de dados
    //É lançada exceção caso haja algum problema com a base de dados
    public Collection<Vertice> values() {
        Collection<Vertice> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM vertices")) {
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
    //Método que devolve um set com todos "pares" formados pelo código de vértice e o objeto vértice correspondente
    //É lançada exceção caso haja algum problema com a base de dados
    public Set<Map.Entry<String, Vertice>> entrySet() {
        Map.Entry<String,Vertice> entry;
        HashSet<Map.Entry<String, Vertice>> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM vertices")) {
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