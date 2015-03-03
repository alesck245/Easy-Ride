package br.com.henrique.ride.beans;

import br.com.henrique.ride.util.Image;

public class Motorista {

	private String nome;
	private String telefone;
	private String email;
	private String placa;
	private Image image;
	private String foto;
	private String id;
	private String registroGCM;

	public Motorista(String nome, String telefone, String email, String placa) {

		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.placa = placa;
		this.setImage(new Image());

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegistroGCM() {
		return registroGCM;
	}

	public void setRegistroGCM(String registroGCM) {
		this.registroGCM = registroGCM;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto() {
		this.foto = image.getBitmapBase64();
	}

}
