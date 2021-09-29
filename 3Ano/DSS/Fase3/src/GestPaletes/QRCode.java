package GestPaletes;

public class QRCode {

	private String codQR; /** Código QR */
	private String produto; /** Designação do Produto */

	public QRCode(String codQR, String produto) {
		this.codQR = codQR;
		this.produto = produto;
	}


	public String getCodQR() {
		return codQR;
	}

	public void setCodQR(String codQR) {
		this.codQR = codQR;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	@Override
	public String toString() {
		return "QrCode:" +
				" " + codQR +
				" " + produto;
	}

}