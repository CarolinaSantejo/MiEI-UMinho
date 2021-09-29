package DataBase;

import GestPaletes.QRCode;

import java.sql.*;
import java.util.*;

public class QRCodeDAO implements Map<String, QRCode> {

    private static QRCodeDAO singleton = null;

    //Construtor. É aqui que a tabela dos QRCodes na base de dados é criada.
    // Lança uma excepção caso haja algum problema.
    private QRCodeDAO() {

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS qrCodes (" +
                    "Codigo varchar(10) NOT NULL PRIMARY KEY," +
                    "Produto varchar(45) NOT NULL DEFAULT 'n/d')";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    //Implementação do padrão Singleton
    //devolve a instância única desta classe
    public static QRCodeDAO getInstance() {
        if (QRCodeDAO.singleton == null) {
            QRCodeDAO.singleton = new QRCodeDAO();
        }
        return QRCodeDAO.singleton;
    }

    @Override
    //Este método devolve o número de entradas na tabela na base de dados
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM qrCodes")) {
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
    //Método que devolve se uma dado código de QRCode se encontra registado na base de dados
    //Lança exceção caso ocorra algum problema na procura.
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT Codigo FROM qrCodes WHERE Codigo ='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }


    @Override
    //Método que devolve se uma dado QRCode se encontra registado na base de dados
    public boolean containsValue(Object value) {
        QRCode a = (QRCode) value;
        return this.containsKey(a.getCodQR());
    }

    @Override
    //Método que devolve o QRCode cujo código é o passado como argumento
    //Lança exceção caso haja algum problema na procura na base de dados
    public QRCode get(Object key) {
        QRCode a = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM qrCodes WHERE Codigo='"+key+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                a = new QRCode(rs.getString("Codigo"), rs.getString("Produto"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }


    @Override
    //Método que adiciona uma entrada á tabela dos QRCodes na base de dados.
    // O código é a identificação do objeto na tabela
    //É lançada exceção caso haja algum problema relativo á database
    public QRCode put(String key, QRCode value) {
        QRCode res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // Actualizar o aluno
            stm.executeUpdate(
                    "INSERT INTO qrCodes VALUES ('"+value.getCodQR()+"', '"+value.getProduto()+"') " +
                            "ON DUPLICATE KEY UPDATE Codigo=VALUES(Codigo), Produto=VALUES(Produto)");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    //Método que remove da tabela dos QRcodes na database, o qrCode cujo código é passado como argumento.
    //É necessário por null em todas as referencias do qrCode removido em outras tabelas
    //Lançada exceção caso haja algum problema na base de dados
    public QRCode remove(Object key) {
        QRCode t = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM qrCodes WHERE Codigo='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    @Override
    //Método que adiciona de uma unica vez vários qrCodes á tabela na base de dados
    public void putAll(Map<? extends String, ? extends QRCode> qrcodes) {
        for(QRCode t : qrcodes.values()) {
            this.put(t.getCodQR(), t);
        }

    }

    @Override
    //Método que elimina todas as entradas na tabela qrCodes.
    //É necessário por null em todas as referencias do qrCode removido em outras tabelas
    //É lançada exceção caso haja algum problema com a base de dados
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("UPDATE paletes SET QRCode=NULL");
            stm.executeUpdate("TRUNCATE qrCodes");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

    }

    @Override
    //Método que devolve um set com todos os códigos de qrCodes presentes na base de dados
    //É lançada exceção caso haja algum problema com a base de dados
    public Set<String> keySet() {
        Set<String> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM qrCodes")) {
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
    //Método que devolve uma collection com todos os objetos qrCode presentes na base de dados
    //É lançada exceção caso haja algum problema com a base de dados
    public Collection<QRCode> values() {
        Collection<QRCode> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM qrCodes")) {
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
    //Método que devolve um set com todos "pares" formados pelo código de QRcode e o objeto qrCode correspondente
    //É lançada exceção caso haja algum problema com a base de dados
    public Set<Entry<String, QRCode>> entrySet() {
        Map.Entry<String,QRCode> entry;
        HashSet<Entry<String, QRCode>> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Codigo FROM qrCodes")) {
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