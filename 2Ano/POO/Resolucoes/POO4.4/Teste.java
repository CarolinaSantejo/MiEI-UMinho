import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Escreva a descrição da classe Teste aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Teste
{    
    public static void main () {
        LocalDateTime data = LocalDateTime.now();
        LocalDateTime data2 = LocalDateTime.of(2009,11,4,21,30);
        LocalDateTime data3 = LocalDateTime.of(2029,11,4,21,30);
        List<String> coment = new ArrayList<>();
        coment.add("ola");
        coment.add("adeus");
        coment.add("bom dia");
        FBPost p1 = new FBPost(1, "eueu", data, "Eu sou lindo", 15, coment);
        FBPost p2 = new FBPost(2, "eutu", data2, "Eu sou feio", 14346, coment);
        FBPost p3 = new FBPost(3, "eueu", data3, "Eu sou feio e lindo", 146, coment);
        FBFeed posts = new FBFeed();
        posts.addPost(p1);
        posts.addPost(p2); 
        posts.addPost(p3); 
        System.out.println(posts.toString());
        System.out.println("Posts de 'eueu':\n" + posts.postsOf("eueu",data,data3).toString());
        System.out.println("Numero de posts do utilizador: " + posts.nrPosts("eueu"));
 
    }
}