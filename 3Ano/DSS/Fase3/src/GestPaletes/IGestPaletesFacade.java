package GestPaletes;

import Exceptions.NaoLevantouPalete;
import Exceptions.PaleteJaExiste;
import Exceptions.QRCodeInvalido;
import Exceptions.SemPaletesParaTransportar;
import GestRobot.Vertice;

import java.util.List;
import java.util.Map;

public interface IGestPaletesFacade {

	Map<String,String> disponibiliza_listagem();


	void registaPalete(String codQR, String codPalete , Vertice loc) throws QRCodeInvalido, PaleteJaExiste;

	/**
	 * 
	 * @param codPalete
	 * @param locPalete
	 */
	void atualiza_localizacaoPalete(String codPalete, Vertice locPalete, int inRobot);

	/**
	 * 
	 * @param codPalete
	 */
	Vertice getLocalizacaoPalete(String codPalete);

	boolean haPaletes();
	boolean existeQRcode(String qr);
	List<String> getPaletesEmRobots();
	String paleteZonaD() throws SemPaletesParaTransportar;
	boolean validaEstadoInRobot(String codPalete, int estado) throws NaoLevantouPalete;
}