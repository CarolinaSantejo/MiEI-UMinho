
/**
 * Escreva a descrição da classe Aluno aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Aluno
{
    private String numero;
    private int nota;
    private String nome;
    private String curso;
    
    public Aluno() {
        this.numero = "";
        this.nota = 0;
        this.nome = "";
        this.curso = "";
    }
    
    public Aluno(String numero, int nota, String nome, String curso) {
        this.numero = numero;
        this.nota = nota;
        this.nome = nome;
        this.curso = curso;
    }
    
    public Aluno(Aluno umAluno) {
        this.numero = umAluno.getNumero();
        this.nota = umAluno.getNota();
        this.nome = umAluno.getNome();
        this.curso = umAluno.getCurso();
    }
}
