import java.util.*;
import java.util.stream.Collectors; 
import java.util.stream.Stream;
import java.io.*;

/**
 * Escreva a descrição da classe DriveIt aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class DriveIt implements Serializable
{
    // variaveis de classe
    private static Map<String, Comparator<Veiculo>> comparadores = new HashMap<>();
    
    public static Comparator<Veiculo> getComparador(String criterio){
        return comparadores.get(criterio);
    }
    
    public static void addComparador (String criterio, Comparator<Veiculo> c) {
        comparadores.put(criterio,c);
    }
    
    private String nome;
    private Map<String, Veiculo> veiculos;
    
    public DriveIt () {
        this.nome = "";
        this.veiculos = new HashMap<>();
    }
    
    public DriveIt (String nome) {
        this.nome = nome;
        this.veiculos = new HashMap<>();
    }
    
    public DriveIt (String nome, Map<String, Veiculo> veiculos) {
        setNome(nome);
        setVeiculos(veiculos);
    }
    
    // SETTERS
    
    public void setNome (String nome){
        this.nome = nome;
    }
    
    public void setVeiculos (Map<String, Veiculo> veicls){
        this.veiculos = new HashMap<>();
        for(Veiculo v:veicls.values())
            this.veiculos.put(v.getMatricula(), v.clone());
    }
    
    
    //GETTERS
    
    public String getNome() {
        return this.nome;
    }
     
    public Map<String, Veiculo>  getVeiculos (){
        Map<String, Veiculo> veiculos = new HashMap<>();
        for(Veiculo v:this.veiculos.values())
            veiculos.put(v.getMatricula(), v.clone());        
        return veiculos;
    }
    
    
    public String toString () {
        //Iterator it = this.aluguer.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        /*while(it.hasNext()) {
            Map.Entry al = (Map.Entry) it.next();
            sb.append(al.getKey).append("\n");
        }*/
        sb.append("Nome: ").append(this.nome).append("\n");
        sb.append("Lista de Veículos:\n");
        sb.append(this.veiculos.values().toString()).append("\n");
        /*
        for (Veiculo v : this.veiculos.values()) 
            sb.append(v.toString()).append("\n");*/
        return sb.toString();
    }
    
   
    // (a)
    
    public boolean existeVeiculo (String mat) {
        return this.veiculos.containsKey(mat);
    }
    
    // (b)
    
    public int quantos() {
        return this.veiculos.size();
    }
    
    // (c)
    
    public int quantos(String marca) {
        int count = 0;
        for(Veiculo v: this.veiculos.values())
            if (v.getMarca().equals(marca)) count++;
        return count;
    }
    
    // (d)
    
    public Veiculo getVeiculo(String mat) throws VeiculoNaoExisteException {
        if (!this.veiculos.containsKey(mat))
            throw new VeiculoNaoExisteException(mat);
            
        else 
            return this.veiculos.get(mat).clone();
    }
    
    // (e)
    
    public void adiciona(Veiculo v) throws VeiculoJaExisteException {
        if (this.veiculos.containsKey(v.getMatricula()))
            throw new VeiculoJaExisteException(v.getMatricula());
        else
            this.veiculos.put(v.getMatricula(), v.clone());
    }
    
    // (f)
    
    public List<Veiculo> getVeiculosList() {
        List<Veiculo> lista = new ArrayList<>();
        lista = this.veiculos.values().stream().map(Veiculo :: clone).collect(Collectors.toList());
        /*
        for (Veiculo v: this.veiculos.values())
            lista.add(v.clone());*/
        return lista;
    }
    
    // (g)
    
    public void adiciona(Set<Veiculo> vs) throws VeiculoJaExisteException {
        for (Veiculo v: vs)
            this.adiciona(v);
    }
    
   
   // (h)
   
    public void registarAluguer(String codVeiculo, int numKms) throws VeiculoNaoExisteException {
       if (this.veiculos.containsKey(codVeiculo)) {
           Veiculo v = this.veiculos.get(codVeiculo);
           int kms = v.getKms();
           kms += numKms;
           v.setKms(kms);
           v.setKmsUltimo(numKms);
       }
       else throw new VeiculoNaoExisteException(codVeiculo);
    }
   
   // (i)
   
    public void classificarVeiculo(String cod, int classificacao) throws VeiculoNaoExisteException {
       ArrayList<Integer> classif = new ArrayList<>();
       if (this.veiculos.containsKey(cod)) {
           classif = this.veiculos.get(cod).getClassif();
           classif.add(classificacao);
           this.veiculos.get(cod).setClassif(classif);
       }
       else throw new VeiculoNaoExisteException(cod);
    }
   
   // (j)
   
    public double custoRealKm(String cod) throws VeiculoNaoExisteException {
       if (this.veiculos.containsKey(cod)) {    
            double custo = this.veiculos.get(cod).getPrecoKm();
           custo += custo*0.1;
           return custo;
       }
       else throw new VeiculoNaoExisteException(cod);
    }
   
   // (2)
   
    public void emPromocao () {
       /*    Iterator it = this.veiculos.entrySet().iterator();
       while(it.hasNext()) {
           Map.Entry v = (Map.Entry) it.next();
           if (v instanceof VeiculoOcasiao) ((VeiculoOcasiao)v).setDesconto(true);
       }
       */
        this.veiculos.values().stream().filter(v -> v instanceof VeiculoOcasiao)
                                       .forEach(v -> ((VeiculoOcasiao)v).setDesconto(true));
    }
   
   // (3)
   
    public int quantosT(String tipo) throws ClassNotFoundException {
       Class c = Class.forName(tipo);
       return (int) this.veiculos.values().stream().filter(v -> v.getClass().equals(c)).count();
    }
   
   // FASE 2
   
   // 2
   // (a)
   
    public Set<Veiculo> ordenaVeiculos() {
       TreeSet<Veiculo> res = new TreeSet<>();
       this.veiculos.values().stream().forEach(v -> res.add(v.clone()));
       return res;
    }
   
   // (b)
   
    public List<Veiculo> ordenaVeiculosList() {
       return this.veiculos.values().stream().map(Veiculo :: clone)
                 .sorted().collect(Collectors.toList());
    }
   
   // (c)
   
    public Set<Veiculo> ordenarVeiculos(Comparator<Veiculo> c) {
       TreeSet<Veiculo> res = new TreeSet<>(c);
       this.veiculos.values().stream().forEach(v -> res.add(v.clone()));
       return res;
    }
   
   // (e)
    public Iterator<Veiculo> ordenarVeiculo(String criterio) {
        Comparator<Veiculo> c = this.getComparador(criterio);
        TreeSet<Veiculo> res = new TreeSet<>(c);
        Iterator it = res.iterator(); 
        return it;
    }
   
    
   // FASE 3
   
    public List<BonificaKm> daoPontos() {
       return this.veiculos.values().
                   stream().
                   filter(v -> v instanceof BonificaKm).
                   map (v -> (BonificaKm) v.clone()).
                   collect(Collectors.toList());
    
    }
   
   // FASE 4
   
   // 1.
    public void escreveFicheiroTexto(String nomeFicheiro) throws FileNotFoundException {
        PrintWriter f = new PrintWriter(nomeFicheiro);
        for(Veiculo v: this.veiculos.values())
            f.println(v.toString());
        
        f.flush();
        f.close();
    }
    
   // 2.
    public void guardaEstado(String nomeFicheiro) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(nomeFicheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        
        oos.writeObject(this);
        oos.close();
        fos.close();
    
    }
    
    public static DriveIt carregaEstado(String nomeFicheiro) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(nomeFicheiro);
        ObjectInputStream ois = new ObjectInputStream(fis);
        
        DriveIt novoDrive = (DriveIt) ois.readObject();
        ois.close();
        fis.close();
        return novoDrive;
    }
}
