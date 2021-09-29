package DataBase;
//Aqui define-se o user, a palavra-passe, o driver e a base de dados,
// de maneira a não ser necessário escrever esta informação várias vezes
public class DAOconfig {
    static final String USERNAME = "g19";
    static final String PASSWORD = "Gp19.1234567";
    private static final String DATABASE = "SistemaArmazem";
    private static final String DRIVER = "jdbc:mysql";
    static final String URL = DRIVER+"://localhost:3306/"+DATABASE;
}
