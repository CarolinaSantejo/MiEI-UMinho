package GestRobot;

import DataBase.ArestaDAO;

import java.util.*;

public class Rota {

	private String codRota; /** Código que identifica a Rota*/
	private Collection<Aresta> caminho; /** Lista de Arestas que definem um caminho a percorrer pelo Robot*/
	private Vertice origem; /** Vertice onde se localiza a palete a recolher*/
	private Vertice destino; /** Vertice onde a Palete irá ser entregue*/
	private String codPalete; /** Código que identifica a Palete que o Robot irá transportar*/


	public Rota(String codRota, Vertice origem, Vertice destino, String codPalete, Collection<Aresta> caminho) {
		this.caminho = caminho;
		this.origem = origem;
		this.destino = destino;
		this.codRota = codRota;
		this.codPalete = codPalete;
	}

	public Rota(String codRota, Vertice origem, Vertice destino, String codPalete) {
		this.codRota = codRota;
		this.origem = origem;
		this.destino = destino;
		this.codPalete = codPalete;
		this.caminho = null;
	}

	public Collection<Aresta> getCaminho() {
		return caminho;
	}

	public void setCaminho(Collection<Aresta> caminho) {
		this.caminho = caminho;
	}

	public Vertice getOrigem() {
		return origem;
	}

	public void setOrigem(Vertice origem) {
		this.origem = origem;
	}

	public Vertice getDestino() {
		return destino;
	}

	public void setDestino(Vertice destino) {
		this.destino = destino;
	}

	public String getCodRota() {
		return codRota;
	}

	public void setCodRota(String codRota) {
		this.codRota = codRota;
	}

	public String getCodPalete() {
		return codPalete;
	}

	public void setCodPalete(String codPalete) {
		this.codPalete = codPalete;
	}

	@Override
	public String toString() {
		return "Rota:" +
				" " + caminho +
				" " + origem +
				" " + destino +
				" " + codRota +
				" " + codPalete;
	}

}