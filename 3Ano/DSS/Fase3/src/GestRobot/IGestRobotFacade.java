package GestRobot;


import Exceptions.*;

import java.util.List;
import java.util.Map;

public interface IGestRobotFacade {

	String getRobotDisponivel() throws RobotsNaoDisponiveis;

	void notifica_transporte(String codRobot, String codPalete, Vertice locP);

	void atualiza_estadoRobot(String codRobot, int estado);


	void indica_destino(String codRobot, String codDestino);

	void calcula_rota(String codRobot, int EntregaOuRecolha) throws OrigemIgualDestino;

	void atualiza_LocalizacaoRobot(String codRobot, int od) throws RotaNull;

	boolean haRobots();

	Vertice getVerticeZonaDescarga();

	String getRobotPalete(String codPalete);

	String getPaleteDoRobot(String codRobot) throws RotaNull;

	void atualizaOcupacaoVertice(String codVertice, int e);

	void alteraRota(String codRobot, Rota rota);

	Vertice getLocalizacaoRobot(String codRobot);

	Map<String,String> listagemPaletesInRobot(List<String> paletes);

	String getPrateleiraLivre() throws EspacoInsuficienteNoArmazem;

	List<String> getCaminho(String codRobot);

	boolean validaRobot(String codRobot) throws RobotNaoExiste;
}