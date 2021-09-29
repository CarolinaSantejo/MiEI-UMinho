package GestRobot;

import Exceptions.RotaNull;

public class Robot {

	private String codRobot; /** Código que identifica a Rota*/
	private Rota rota; /** Rota atribuida ao Robot. É null se Robot ainda não tiver nenhuma Rota atribuida*/
	private int estado; /** Indica o estado do Robot no momento:
	 						0 se Robot não tem nenhuma Rota atribuida, ou seja, está disponivel para fazer uma nova tarefa;
	 						1 se Robot tem uma Rota atribuida, ou seja, está ocupado a fazer uma tarefa.*/
	private Vertice localizacao; /** Vertice onde o Robot se localiza*/

	public Robot(String codRobot, int estado, Vertice localizacao, Rota rota) {
		this.rota = rota;
		this.codRobot = codRobot;
		this.estado = estado;
		this.localizacao = localizacao;
	}

	public Rota getRota() {
		return rota;
	}

	public void setRota(Rota rota) {
		this.rota = rota;
	}

	public String getCodRobot() {
		return codRobot;
	}

	public void setCodRobot(String codRobot) {
		this.codRobot = codRobot;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Vertice getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(Vertice localizacao) {
		this.localizacao = localizacao;
	}


	@Override
	public String toString() {
		return "Robot:" +
				" " +  codRobot +
				" " + rota.toString() +
				" " + estado +
				" " + localizacao.toString();
	}

	public void atualizaLocalizacao(int od) throws RotaNull {
		Vertice loc;
		if(rota == null) throw new RotaNull("Robot não está a efetuar nenhuma rota");
		if (od == 0) // ou seja queremos atualizar para a origem do caminho
			loc = rota.getOrigem();
		else
			loc = rota.getDestino();
		setLocalizacao(loc);
	}

	public void notifica_transporte(String codPalete, Vertice locP) {
		estado = 1;
		rota = new Rota("R"+codRobot,locP,null,codPalete,null);
	}

	public void indica_destino(Vertice destino) {
		rota.setDestino(destino);
	}
}