import java.time.LocalDateTime;
import java.util.*;
import java.lang.Comparable;

/**
 * Escreva a descrição da classe FBPost aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class FBPost  implements Comparable<FBPost>
{
    private int id;
    private String util;
    private LocalDateTime data;
    private String cont;
    private int likes;
    private List<String> coment;
    
    public FBPost() {
        this.id = 0;
        this.util = "";
        this.data = null;
        this.cont = "";
        this.likes = 0;
        this.coment = new ArrayList<String>();
    
    }
    
    public FBPost (int id, String utilizador, LocalDateTime data, String conteudo, int likes, List<String> coment) {
        this.id = id;
        this.util = utilizador;
        this.data = data;
        this.cont = conteudo;
        this.likes = likes;
        this.coment = coment;
        }
    
    public FBPost (FBPost fb) {
        this.id = fb.getId();
        this.util = fb.getUt();
        this.data = fb.getData();
        this.cont = fb.getCont();
        this.likes = fb.getLikes();
        this.coment = fb.getCom();
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getUt(){
        return this.util;
    }
    
    public LocalDateTime getData(){
        return this.data;
    }
    
    public String getCont(){
        return this.cont;
    }
    
    public int getLikes(){
        return this.likes;
    }
    
    public List getCom(){
        return this.coment;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setUt(String utilizador){
        this.util = util;
    }
    
    public void setData(LocalDateTime data){
        this.data = data;
    }
    
    public void setCont(String cont){
        this.cont = cont;
    }
    
    public void setLikes(int likes){
        this.likes = likes;
    }
    
    public void setCom(List<String> coment){
        for (String c: coment)
            this.coment.add(c);
    }
    
    public void addCom (String coment) {
        this.coment.add(coment);
    }
    
    public void addLike () {
        this.likes++;
    }
    
    public FBPost clone() {
        return new FBPost(this);
    }
    
    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append("Identificador: ").append(this.id).append("\n");
        sb.append("Utilizador: ").append(this.util).append("\n");
        sb.append("Data: ").append(this.data).append("\n");
        sb.append("Conteudo: ").append(this.cont).append("\n");
        sb.append("Nº de Likes: ").append(this.likes).append("\n");
        sb.append("Comentários: ").append(this.coment.toString()).append("\n");
        
        return sb.toString();
    }
    
    public int compareTo (FBPost fb) {
        return this.data.compareTo(fb.getData());
    }
    
    public boolean equals(FBPost p) {
        if (this.id == p.getId()) return true;
        else return false;
        
    }
}
