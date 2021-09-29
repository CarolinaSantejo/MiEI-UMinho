import java.util.*; 
import java.time.LocalDateTime;
/**
 * Escreva a descrição da classe FBFeed aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class FBFeed
{
    private List<FBPost> posts;
    
    public FBFeed () {
        this.posts = new ArrayList<FBPost> ();
    }
    
    public FBFeed(List<FBPost> posts) {
        setPosts(posts);
    }
    
    public void setPosts (List<FBPost> posts) {
        for(FBPost p: posts)
            this.posts.add(p.clone());
    }
    
    public List<FBPost> getPosts () {
        List<FBPost> posts = new ArrayList<>();
        for(FBPost p: this.posts)
            posts.add(p.clone());
        return posts;
    }
    
    public void addPost (FBPost post) {
        this.posts.add(post);
        Collections.sort(this.posts);
    }
    

    public FBFeed clone() {
        return new FBFeed(getPosts());
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        sb.append("FEED:\n\n");
        for(FBPost p: this.posts) {
            sb.append(p).append("\n ----------- \n\n");
        }
        return sb.toString();
    }
    
    // Alínea (b)
    // i.
    
    public int nrPosts(String user) {
        int count = 0;
        for(FBPost p: this.posts)
            if(p.getUt().equals(user)) count++;
        return count;
    }
    
    //ii.
    
    public List<FBPost> postsOf(String user) {
        List<FBPost> posts = new ArrayList<>();
        for(FBPost p: this.posts)
            if(p.getUt().equals(user)) 
                posts.add(p.clone());
        return posts;
    }
    
    //iii.
    
    public List<FBPost> postsOf(String user, LocalDateTime inicio, LocalDateTime fim) {
        List<FBPost> posts = new ArrayList<>();
        for(FBPost p: this.posts) {
            if(p.getUt().equals(user) 
            && p.getData().compareTo(inicio) >= 0 
            && p.getData().compareTo(fim)<= 0)    
                 posts.add(p.clone());
                }
        return posts;
    
    }
    
    //iv.
    
    public FBPost getPost(int id) {
        FBPost post = new FBPost();
        for(FBPost p: this.posts)
            if(p.getId() == id) { 
                post = p.clone();
                break;
            }
        return post;
    }
    
    //v.
    
    public void coment(FBPost post, String comentario) {
        for(FBPost p: this.posts)
            if(p.equals(post)) { 
                p.addCom(comentario);
                break;
            }
    }
    
    //vi.
    
    public void coment(int id, String comentario) {
        for(FBPost p: this.posts)
            if(p.getId() == id) { 
                p.addCom(comentario);
                break;
            }
    }
    
    //vii.
    
    public void like(FBPost post) {
        for(FBPost p: this.posts)
            if(p.equals(post)) { 
                p.addLike();
                break;
            }
    }
    
    //viii.
    
    public void like(int postid) {
        for(FBPost p: this.posts)
            if(p.getId() == postid) { 
                p.addLike();
                break;
            }
    }
    
    //ix.
    public int maxIndex(List<Integer> list) {
        int m = list.get(0);
        int iMax = 0;
        
        for(int i = 0; i<list.size(); i++) {
            if (list.get(i)>m) {
                m = list.get(i);
                iMax = i;
            }
        }
        return iMax;
    }
    
    List<Integer> top5CommentsEx() {
        List<Integer> listId = new ArrayList<>();
        List<Integer> listC = new ArrayList<>();
        List<Integer> listM = new ArrayList<>();
        int index,max;
        for(FBPost p: this.posts){
            listId.add(p.getId());
            listC.add(p.getCom().size());
        }
        for (int i = 0; i<5; i++) {
            listM.add(listId.get(maxIndex(listC)));
        }
        
        return listM;
    }
    
    
}