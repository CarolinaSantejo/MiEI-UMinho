import java.util.*;
import java.util.TreeSet;
import java.util.stream.Collectors;
/**
 * Escreva a descrição da classe Turma aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Turma
{
   private String nome;
   private String codigo;
   private Map<String, Aluno> alunos;
   
   public Turma(){
       this.nome = new String();
       this.codigo = new String();
       this.alunos = new HashMap<>();
   }
   
   public Turma(String nome, String codUc, Map<String, Aluno> als){
       this.nome = nome;
       this.codigo = codUc;
       this.alunos = new HashMap();
       for(Aluno a:als)
            this.alunos.put(a.getNumero(), a.clone());
   }

   public String getNome() {
       return this.nome;
   }
   
   public String getCodigo() {
       return this.codigo;
   }
   
   public Map<String, Aluno> getAlunos() {
       Map<String, Aluno> res = new HashMap<>();
       for(Aluno a:this.alunos.values()) //values() devolve uma Collection<Aluno>
            res.put(a.getNumero(), a.clone());     
       return res;
       
       /* utilizando entrySet
        * for(Map.Entry<String, Aluno> entry: this.alunos.entrySet())
        *   res.put(entry.getKey, entry.getValue().clone());
        * return res;
        */
   }
   
   void setNome (String nome) {
       this.nome = nome;
   }
   
   void setCodigo (String codigo) {
       this.codigo = codigo;
   }
   
   void setAlunos (Map<String,Aluno> als) {
       this.alunos = new HashMap<>();
       als.values().forEach(a -> this.alunos.put(a.getNumero(), a.clone()));
       /* for (Aluno a: als.values())
        *   this.alunos.put(a.getNumero(), a.clone());
        */
   }
   
   // ii.
   public void insereAluno(Aluno a) {
       this.alunos.put(a.getNumero(), a.clone());
   }
   
   // iii.
   public Aluno getAluno(String codAluno) {
       return this.alunos.get(codAluno).clone();
   }
   
   // iv.
   public void removeAluno(String codAluno){
       this.alunos.remove(codAluno);
   }
   
   // v.
   public Set<String> todosOsCodigos() {
       return this.alunos.keySet();
    
   }
   
   // vi.
   public int qtsAlunos() {
       return this.alunos.size();
   }
   
   // vii.
   public Set<Aluno> alunosOrdemAlfabetica() {
       Set<Aluno> res = new TreeSet<>();
       for(Aluno a: this.alunos.values())
            res.add(a.clone());
       return res;
   }
   
   public Set<Aluno> alunosOrdemAlfabeticaStreams() {
       return this.alunos.values().stream()
                                  .sorted()
                                  .map(Aluno::clone)
                                  .collect(Collectors.toSet());
   }
   
   // vii.
   /*
   
   public Set<Aluno> alunosOrdemDecrescenteNumero() {
       TreeSet<Aluno> res = new TreeSet<>(new Comparator);
       
       
    }
   */
}
