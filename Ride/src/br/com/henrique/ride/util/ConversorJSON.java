package br.com.henrique.ride.util;

import br.com.henrique.ride.beans.Carona;
import br.com.henrique.ride.beans.Caroneiro;
import br.com.henrique.ride.beans.Motorista;

import com.google.gson.Gson;

public class ConversorJSON {

	private Motorista motorista;
	private Caroneiro caroneiro;
	private Carona carona;

	public ConversorJSON(Motorista motorista) {
		this.motorista = motorista;
	}

	public ConversorJSON(Caroneiro caroneiro) {
		this.caroneiro = caroneiro;
	}

	public ConversorJSON(Carona carona) {
		this.carona = carona;
	}

	public String converteMotorista() {

		motorista.setFoto();

		Gson gson = new Gson();
		String atributos = gson.toJson(motorista);

		return atributos;

	}

	public String converteCaroneiro() {

		caroneiro.setFoto();

		Gson gson = new Gson();
		String atributos = gson.toJson(caroneiro);

		return atributos;
	}

	public String converteCarona() {

		Gson gson = new Gson();
		String atributos = gson.toJson(carona);

		return atributos;

	}

}
