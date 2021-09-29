package GestPaletes;


import DataBase.PaleteDAO;
import DataBase.QRCodeDAO;
import Exceptions.NaoLevantouPalete;
import Exceptions.PaleteJaExiste;
import Exceptions.QRCodeInvalido;
import Exceptions.SemPaletesParaTransportar;
import GestRobot.Vertice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestPaletesFacade implements IGestPaletesFacade {

	private Map<String,QRCode> codigosQR; /** Map que associa todos os QRCodes ao correspondente código QR*/
	private Map<String,Palete> paletes; /** Map que associa todos as Paletes ao seu código correspodente*/

	public GestPaletesFacade() {
		this.codigosQR = QRCodeDAO.getInstance();
		this.paletes = PaleteDAO.getInstance();

	}

	/**
	 * Disponibiliza a localização de cada palete que está registada no armazém
	 * @return Um map da designação de uma localização associada ao código da palete aí localizada
	 */
	public Map<String,String> disponibiliza_listagem() {
		Map<String,String> res = new HashMap<>();
		for (Palete p : paletes.values()){
			if (p.getInRobot() == 0 || p.getInRobot() == 2)
				res.put(p.getCodPalete(),p.getLocalizacao().getDesignacao());
		}
		return  res;
	}

	/**
	 * regista a palete no sistema do armazem, inserindo na base de dados, através do
	 * @param codQR , código QR disponibilizado pelo leitor,
	 * @param codPalete , código da Palete a registar,
	 * @param loc , e a lozalização atual da palete, isto é, a zona de descarga , que consiste na zona default do registo
	 * @throws QRCodeInvalido , caso o QRCode não faça parte da base de dados
	 * @throws PaleteJaExiste , caso a palete já tenha sido registada no sistema
	 */
	public void registaPalete(String codQR, String codPalete , Vertice loc) throws QRCodeInvalido, PaleteJaExiste {
		if (!existeQRcode(codQR)) throw new QRCodeInvalido("Produto não faz parte do armazém!");
		if (paletes.containsKey(codPalete)) throw new PaleteJaExiste("Código da palete já existe no armazem!");
		else {
			this.paletes.put(codPalete, new Palete(codigosQR.get(codQR), codPalete, 0, loc));
		}
	}

	/**
	 * Tem como objetivo atualizar a localização da palete no armazém, atualizando também esse valor na base de dados,
	 * para tal necessitamos dos seguintes inputs :
	 * @param codPalete , o código da palete a atualizar
	 * @param locPalete , a nova localização da palete
	 * @param inRobot , se a palete está num robot ou não
	 * De modo a reutilizar código e tendo em conta que uma palete pode estar tanto num vertice ou num Robot (inRobot = 1),
	 * usamos este mesmo método tanto para atualizar a localização num vértice como para atualizar o estado do InRobot
	 */
	public void atualiza_localizacaoPalete(String codPalete, Vertice locPalete, int inRobot) {
		Palete p = paletes.get(codPalete);
		if(inRobot==-1) { // se for -1 atualiza apenas o vertice
			p.setLocalizacao(locPalete);
			p.setInRobot(0); //quando atualizamos um vertice nunca está num robot
		}
		else { // se for 0 ou 1 ou 2 atualiza o estado
			p.setInRobot(inRobot);
		}
		paletes.put(codPalete,p);
	}

	/**
	 * Devolve a localização atual da Palete
	 * @param codPalete , com o código dado
	 * @return do vertice onde se encontra
	 */
	public Vertice getLocalizacaoPalete(String codPalete) {
		Palete p = paletes.get(codPalete);
		return p.getLocalizacao();

	}

	/**
	 * Método que verifica se há paletes no sistema
	 * @return true se há paletes registadas
	 */
	@Override
	public boolean haPaletes() {
		return !this.paletes.isEmpty();
	}

	/**
	 * Este método verifica se existe o QRCode
	 * @param qr com este código , na base de dados
	 * @return true caso exista
	 */
	public boolean existeQRcode(String qr){
		return codigosQR.containsKey(qr);
	}

	/**
	 * Devolve a lista das Paletes que estão neste momento a serem transportadas por Robots
	 * @return Lista do código das paletes que cumprem esta condição
	 */
	public List<String> getPaletesEmRobots(){
        List<String> res = new ArrayList<>();
        for (Palete p : paletes.values()) {
            if (p.getInRobot() == 1) res.add(p.getCodPalete());
        }
        return res;
    }

	/**
	 * Devolve uma palete que ainda esteja na zona de descarga e que ainda não tenha sido atribuida a nenhum robot
	 * @return do código de uma palete que cumpra essa condição
	 * @throws SemPaletesParaTransportar caso a Zona de Descarga se encontre vazia
	 */
	public String paleteZonaD() throws SemPaletesParaTransportar {
		for (Palete p : paletes.values()) {
			if (p.getInRobot() == 0 && p.getLocalizacao().getCodVertice().equals("1")) return p.getCodPalete();
		}
		throw new SemPaletesParaTransportar("Não há paletes por atribuir.");
	}

	/**
	 * Verifica se o estado da palete corresponde ao estado que queremos ver se esta mesma tem
	 * @param codPalete - código da palete
	 * @param estado - o estado que queremos verificar que ela se encontra
	 * @return true caso a palete se encontre no estado que estamos a verificar
	 * @throws NaoLevantouPalete caso seja false, uma vez que usamos o método quando queremos verificar se o robot já tem
	 * palete ou não
	 */
	public boolean validaEstadoInRobot(String codPalete, int estado) throws NaoLevantouPalete {
		if(paletes.get(codPalete).getInRobot()==estado) return true;
		throw new NaoLevantouPalete("Ainda não recolheu a palete! O robot não tem Palete.");
	}

}