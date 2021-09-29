package GestRobot;

import java.util.Objects;

public class Vertice {

	private String codVertice; /** Código que identifica o Vertice*/
	private String designacao; /** Código que identifica o tipo do Vertice: prateleira, canto ou zona*/
	private int ocupacao;  /** Identifica o número de paletes localizadas no Vertice(prateleira ou zona). Como neste caso cada prateleira pode conter apenas uma palete pode tomar 3 valores diferentes:
	 						0 se o Vertice ainda não possui nenhuma palete
	 						1 se o Vertice está ocupado, ou seja, já possui uma palete
	 						-1 se o Vertice ainda não possui nenhuma palete mas foi reservado para brevemente a receber*/


	public Vertice(String codVertice, String designacao, int ocupacao) {
		this.codVertice = codVertice;
		this.designacao = designacao;
		this.ocupacao = ocupacao;
	}


	public String getCodVertice() {
		return codVertice;
	}

	public void setCodVertice(String codVertice) {
		this.codVertice = codVertice;
	}

	public String getDesignacao() {
		return designacao;
	}

	public void setDesignacao(String designacao) {
		this.designacao = designacao;
	}

	public int getOcupacao() {
		return ocupacao;
	}

	public void setOcupacao(int ocupacao) {
		this.ocupacao = ocupacao;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Vertice vertice = (Vertice) o;
		return codVertice.equals(vertice.codVertice);
	}



	@Override
	public int hashCode() {
		return Objects.hash(codVertice);
	}

	@Override
	public String toString() {
		return "Vertice:" +
				" " + designacao +
				" " + ocupacao +
				" " + codVertice;
	}
}