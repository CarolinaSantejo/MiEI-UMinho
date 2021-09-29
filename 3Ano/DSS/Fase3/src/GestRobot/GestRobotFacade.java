package GestRobot;


import DataBase.*;
import Exceptions.*;

import java.util.*;

public class GestRobotFacade implements IGestRobotFacade {

	private Map<String, Vertice> vertices; /** Map que associa todos os Vertices do armazem ao seu codigo*/
	private Map<String, Aresta> corredores; /** Map que associa todas as Arestas do armazem ao seu codigo*/
	private Map<String, Robot> frota; /** Map que associa todos os Robots do armazem ao seu codigo*/


	public GestRobotFacade() {
		this.vertices = VerticeDAO.getInstance();
		this.corredores = ArestaDAO.getInstance();
		this.frota = RobotDAO.getInstance();

	}

	/**
	 * Método que retorna o código de um Robot que esteja disponivel para efetuar uma nova rota.
	 * @throws RobotsNaoDisponiveis quando todos os robots estão ocupados, ou seja, já tem uma rota atribuida.
	 */

	public String getRobotDisponivel() throws RobotsNaoDisponiveis {
		for (Robot r : frota.values()) {
			if (r.getEstado() == 0)
				return r.getCodRobot();
		}
		throw new RobotsNaoDisponiveis("Não existem robots disponiveis para transportar paletes.");
	}

	/**
	 * Método que atribui uma nova Rota com um Vertice origem e um codigo de Palete a um Robot .
	 */
	public void notifica_transporte(String codRobot, String codPalete, Vertice locP) {
		Robot r = frota.get(codRobot);
		r.notifica_transporte(codPalete,locP);
		frota.put(codRobot,r);
	}

	/**
	 * Método que atualiza atributo estado de um Robot.
	 */
	public void atualiza_estadoRobot(String codRobot, int estado) {
		Robot r = frota.get(codRobot);
		r.setEstado(estado);
		frota.put(codRobot,r);
	}

	/**
	 * Função que atribui um novo Vertice destino à Rota correspondente a um Robot.
	 */
	public void indica_destino(String codRobot, String codDestino) {
		Vertice destino = vertices.get(codDestino);
		Robot r = frota.get(codRobot);
		r.indica_destino(destino);
		frota.put(codRobot,r);
	}

	/**
	 * Método que calcula uma nova lista de Arestas correspondentes ao caminho da Rota de um Robot.
	 * Se o parametro EntregaOuRecolha é 0 é calculado o caminho para a recolha de uma palete e, no caso de ser 1, é calculado o caminho para o Robot se deslocar até ao destino de entrega da palete que transporta.
	 * @throws OrigemIgualDestino quando o Vertice origem é o mesmo que o Vertice destino, logo o Robot já se situa no Vertice pretendido.
	 */

	public void calcula_rota(String codRobot, int EntregaOuRecolha) throws OrigemIgualDestino {
		Robot r = frota.get(codRobot);
		Rota rota = r.getRota();
		Vertice origem;
		Vertice destino;
		if(EntregaOuRecolha==0){
			origem = r.getLocalizacao();
			destino = rota.getOrigem();

		}
		else{
			origem = rota.getOrigem();
			destino = rota.getDestino();
		}
		AlgoritmoDijkstra ad = new AlgoritmoDijkstra(vertices.values(),corredores.values());
		List<Aresta> res = new ArrayList<>();
		List<Vertice> c;
		if (!origem.equals(destino)) {
			ad.executar(origem);
			c = ad.obterCaminhoMaisCurto(destino);
			for(int i = 0; i<c.size()-1; i++){
				for(Aresta a : corredores.values()){
					if(a.getVerticeInicial().equals(c.get(i)) && a.getVerticeFinal().equals(c.get(i+1))) res.add(a);
				}
			}
		}
		else throw new OrigemIgualDestino("Robot já está no vertice pretendido.");
		rota.setCaminho(res);
		frota.put(codRobot,r);
	}

	/**
	 * Método que atualiza a localizacao de um Robot para: Vertice origem da Rota se od=0 e Vertice destino da Rota de od=1.
	 * @throws RotaNull quando o Robot não tem nenhuma Rota atribuida.
	 */
	public void atualiza_LocalizacaoRobot(String codRobot, int od) throws RotaNull {
		Robot r = frota.get(codRobot);
		r.atualizaLocalizacao(od);
		frota.put(codRobot,r);
	}

	/**
	 * Método que verifica se há robots no sistema
	 * @return true se há robots registados
	 */

	public boolean haRobots() {
		return !this.frota.isEmpty();
	}

	/**
	 * Método que retorna o Vertice correspondente à zona de descarga do armazem
	 */

	public Vertice getVerticeZonaDescarga(){
		Vertice v = null;
		for(Vertice ve : vertices.values()){
			if(ve.getDesignacao().equals("Zona D")){
				v = ve;
			}
		}
		return v;
	}

	/**
	 * Método que dado o código de uma Palete retorna o codigo do Robot que a está a transportar
	 */
	public String getRobotPalete(String codPalete) {
		String robot = "n/d";
		for (Robot r : frota.values()) {
			Rota rota = r.getRota();
			if( rota!= null && rota.getCodPalete().equals(codPalete)) {
				robot = r.getCodRobot();
				break;
			}
		}
		return robot;
	}

	/**
	 * Método que dado o código de um Robot retorna o código da Palete que está a transportar
	 * @throws RotaNull quando o Robot não tem nenhuma Rota atribuida, logo não está a transportar nenhuma Palete
	 */

	public String getPaleteDoRobot(String codRobot) throws RotaNull{
		Robot r = frota.get(codRobot);
		Rota rota = r.getRota();
		if (rota!= null)
			return rota.getCodPalete();
		else
			throw new RotaNull("Robot não está a efetuar nenhuma rota!!");
	}

	/**
	 * Método que dado o código de um Vertice, atualiza o seu atributo ocupacao.
	 */

	public void atualizaOcupacaoVertice(String codVertice, int e){
		Vertice v = vertices.get(codVertice);
		v.setOcupacao(e);
		vertices.put(v.getCodVertice(),v);
	}

	/**
	 * Método atribui a Rota dada ao Robot cujo código é fornecido.
	 */

	public void alteraRota(String codRobot, Rota rota){
		Robot r = frota.get(codRobot);
		r.setRota(rota);
		frota.put(codRobot,r);
	}

	/**
	 * Método que retorna o Vertice onde o Robot se localiza
	 */

	public Vertice getLocalizacaoRobot(String codRobot){
		return frota.get(codRobot).getLocalizacao();
	}

	/**
	 *	Método que retorna um Map em que a chave é um código de Palete e o valor o código do Robot que a transporta.
	 */

	public Map<String,String> listagemPaletesInRobot(List<String> paletes) {
		Map<String,String> list = new HashMap<>();
		for (String p : paletes) {
			list.put(p,getRobotPalete(p));
		}
		return list;
	}

	/**
	 * Método que retorna o código de um Vertice correspondente a uma prateleira que esteja livre para receber uma palete, ou seja, a sua ocupação é 0.
	 * @throws EspacoInsuficienteNoArmazem quando todas as prateleiras do armazem ja estiverem ocupadas ou reservadas para receber paletes.
	 */

	public String getPrateleiraLivre() throws EspacoInsuficienteNoArmazem {
		for(Vertice v : vertices.values()){
			if((v.getDesignacao().charAt(0)=='P')&&(v.getOcupacao()==0)) return v.getCodVertice();
		}
		throw new EspacoInsuficienteNoArmazem("Não existem Prateleiras Livres!!");
	}

	/**
	 * Método que dado um código de Robot retorna o caminho correspondente à sua Rota.
	 */

	public List<String> getCaminho(String codRobot){
		Robot r = frota.get(codRobot);
		Rota rota = r.getRota();
		Collection<Aresta> lista = rota.getCaminho();
		List<String> res = new ArrayList<>();
		Aresta b = null;
		for(Aresta a : lista){
			res.add(a.getVerticeInicial().getDesignacao());
			b = a;
		}
		if(b!=null)res.add(b.getVerticeFinal().getDesignacao());
		return res;
	}

	/**
	 * Método que verifica se um Robot existe no armazem
	 * @return true quando o codigo dado corresponde ao código de um Robot do armazem.
	 * @throws RobotNaoExiste quando não existe nenhum Robot no armazem com o código dado.
	 */

	public boolean validaRobot(String codRobot) throws RobotNaoExiste{
		if(frota.containsKey(codRobot)) return true;
		throw new RobotNaoExiste("Robot não existe");

	}

}