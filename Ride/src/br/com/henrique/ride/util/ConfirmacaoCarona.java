package br.com.henrique.ride.util;

public class ConfirmacaoCarona {
	
	private String idCaroneiro;
	private String idMotorista;
	private String destino;
	private String valor;
	private String idCarona;
	
	public ConfirmacaoCarona(String idC, String idM, String destino, String valor, String idCarona){
		this.setDestino(destino);
		this.setIdCaroneiro(idC);
		this.setIdMotorista(idM);
		this.setValor(valor);
		this.setIdCarona(idCarona);
	}

	public String getIdCaroneiro() {
		return idCaroneiro;
	}

	public void setIdCaroneiro(String idCaroneiro) {
		this.idCaroneiro = idCaroneiro;
	}

	public String getIdMotorista() {
		return idMotorista;
	}

	public void setIdMotorista(String idMotorista) {
		this.idMotorista = idMotorista;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getIdCarona() {
		return idCarona;
	}

	public void setIdCarona(String idCarona) {
		this.idCarona = idCarona;
	}

}
