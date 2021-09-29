package GestRobot;

public class Prateleira {

	private String codPrateleira; /** Código que identifica a prateleira */
	private int disponibilidade;  /** 0 - A Prateleira está disponível
	 								  1 - A Prateleira está ocupada
	 								  2 - A Prateleira está reservada para uma palete que se encontra a ser transportada*/

	public Prateleira(String codPrateleira, int disponibilidade) {
		this.codPrateleira = codPrateleira;
		this.disponibilidade = disponibilidade;
	}

	public String getCodPrateleira() {
		return codPrateleira;
	}

	public void setCodPrateleira(String codPrateleira) {
		this.codPrateleira = codPrateleira;
	}

	public int getDisponibilidade() {
		return disponibilidade;
	}

	public void setDisponibilidade(int disponibilidade) {
		this.disponibilidade = disponibilidade;
	}

	@Override
	public String toString() {
		return "Prateleira" +
				" " + codPrateleira +
				" " + disponibilidade;
	}

}