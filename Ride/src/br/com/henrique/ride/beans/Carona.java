package br.com.henrique.ride.beans;


public class Carona {

	private String idUsuario;
	private String origemCidade;
	private int vagas;
	private String destinoCidade;
	private String despesas;
	private String periodo;
	private String data;
	private int idCarona;

	public Carona(String id, String origemCidade, String destinoCidade,
			String data) {

		this.idUsuario = id;
		this.origemCidade = origemCidade;

		this.destinoCidade = destinoCidade;

		this.data = data;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String id) {
		this.idUsuario = id;
	}

	public String getOrigemCidade() {
		return origemCidade;
	}

	public void setOrigemCidade(String origemCidade) {
		this.origemCidade = origemCidade;
	}

	public String getDestinoCidade() {
		return destinoCidade;
	}

	public void setDestinoCidade(String destinoCidade) {
		this.destinoCidade = destinoCidade;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getIdCarona() {
		return idCarona;
	}

	public void setIdCarona(int idCarona) {
		this.idCarona = idCarona;
	}

	public int getVagas() {
		return vagas;
	}

	public void setVagas(int vagas) {
		this.vagas = vagas;
	}

	public String getDespesas() {
		return despesas;
	}

	public void setDespesas(String despesas) {
		this.despesas = despesas;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

}
