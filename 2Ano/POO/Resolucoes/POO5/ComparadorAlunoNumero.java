import java.util.Comparator;
/**
 * Escreva a descrição da classe ComparadorAlunoNumero aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class ComparadorAlunoNumero implements Comparator<Aluno> {
    public int compare(Aluno a1, Aluno a2) {
        return(a2.getNumero().compareTo(a1.getNumero()));
    }
}
