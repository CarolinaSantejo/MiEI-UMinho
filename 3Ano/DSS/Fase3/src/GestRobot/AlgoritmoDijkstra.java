package GestRobot;

import java.lang.reflect.Array;
import java.util.*;

public class AlgoritmoDijkstra {
    //Lista de todos os vértices do grafo que representa o armazem
    private List<Vertice> vertices;
    //Lista de todas as arestas do grafo que representa o armazem
    private List<Aresta> corredores;
    //Set de todos os vertices do grafo que já foram "visitados" durante a execução do algoritmo
    private Set<Vertice> visitados;
    //Set de todos os vertices do grafo que ainda não foram visitados "visitados" durante a execução do algoritmo
    private Set<Vertice> naoVisitados;
    //Map no qual estão as relaçoes de precedencia, ou seja que vértices
    // devemos percorrer no camiho mais curto entre dois pontos
    private Map<Vertice, Vertice> precedentes;
    //Map que indica a distância mais curta entre um dado vértice e o vértice a partir do qual
    // queremos encontrar os  caminhos mais curtos
    private Map<Vertice, Float> distancia;


    //Neste construtor passa-se como parâmentro os vértices e as arestas
    // Todas as outras estruturas começam vazias
    public AlgoritmoDijkstra(Collection<Vertice> vertices, Collection<Aresta> corredores) {
        this.vertices = new ArrayList<>(vertices);
        this.corredores = new ArrayList<>(corredores);
        this.visitados = new HashSet<>();
        this.naoVisitados = new HashSet<>();
        this.distancia = new HashMap<>();
        this.precedentes = new HashMap<>();
    }

    //Método que não retorna nada, mas que irá preencher as estruturas de dados.
    public void executar(Vertice origem) {
        this.distancia.put(origem, (float)0);
        this.naoVisitados.add(origem);

        while (this.naoVisitados.size() > 0) {
            Vertice v = obterMinimo(this.naoVisitados);
            this.visitados.add(v);
            this.naoVisitados.remove(v);
            obterDistanciasMinimas(v);

        }
    }

   // Método que preenche as estruturas distâncias, precedentes e não visitados.
   // Invoca outros método.
    public void obterDistanciasMinimas(Vertice v) {
        List<Vertice> verticesAdjacentes = obterVizinhos(v);
        for (Vertice v1 : verticesAdjacentes) {
            if (obterDistanciaMaisCurta(v1) > obterDistanciaMaisCurta(v)
                    + obterDistancia(v, v1)) {
                this.distancia.put(v1, obterDistanciaMaisCurta(v)
                        + obterDistancia(v, v1));
                this.precedentes.put(v1, v);
                this.naoVisitados.add(v1);
            }
        }
    }

    //Devolve a distância da aresta que une vértices v e v1. Lança exceção caso nao exista tal aresta
    public float obterDistancia(Vertice v, Vertice v1) {
        for (Aresta a : this.corredores) {
            if (a.getVerticeInicial().equals(v)
                    && a.getVerticeFinal().equals(v1)) {
                return a.getDist();
            }
        }
        throw new RuntimeException("Erro");
    }

    //Método que devolve todos os vizinhos não visitados de um dado vértice, ou seja
    // todos os vértices não visitados adjacentes ao node
    public List<Vertice> obterVizinhos(Vertice node) {
        List<Vertice> vizinhos = new ArrayList<Vertice>();
        for (Aresta a : this.corredores) {
            if (a.getVerticeInicial().equals(node)
                    && !foiVisitado(a.getVerticeFinal())) {
                vizinhos.add(a.getVerticeFinal());
            }
        }
        return vizinhos;
    }

    //Este método devolve um boolean e indica se um dado vértice já foi visitado ou não.
    public boolean foiVisitado(Vertice v) {
        return this.visitados.contains(v);
    }


    //Método que devolve, entre uma coleção de vértices,aquele está mais próximo do vértice origem
    public Vertice obterMinimo(Set<Vertice> vertices) {
        Vertice minimo = null;
        for (Vertice v : vertices) {
            if (minimo == null) {
                minimo = v;
            }
            else {
                if (obterDistanciaMaisCurta(v) < obterDistanciaMaisCurta(minimo)) {
                    minimo = v;
                }
            }
        }
        return minimo;
    }

   // Devolve a distancia mínima calculada entre o vértice origem  e um vértice destino.
    public float obterDistanciaMaisCurta(Vertice destino) {
        Float d = this.distancia.get(destino);
        if (d == null) {
            return Integer.MAX_VALUE;
        }
        else {
            return d;
        }
    }

    //A partir das estruturas de dados preenchidas pelo método executar()
    // este método devolve o caminho mias curto entre o vértice origem e um destino
    //Devolve null se não houve caminho
    public List<Vertice> obterCaminhoMaisCurto(Vertice destino) {
        List<Vertice> caminho = new ArrayList<>();
        Vertice aux = destino;

        // Verificar que existe caminho
        if (this.precedentes.get(aux) == null) {
            return null;
        }
        caminho.add(aux);
        while (this.precedentes.get(aux) != null) {
            aux = this.precedentes.get(aux);
            caminho.add(aux);
        }
        // Por a ordem do caminho correta
        Collections.reverse(caminho);
        return caminho;
    }

}
