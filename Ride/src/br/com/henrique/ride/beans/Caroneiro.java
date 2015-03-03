package br.com.henrique.ride.beans;

import br.com.henrique.ride.util.Image;

public class Caroneiro {

	private String nome;
	private String telefone;
	private String profissao;
	private String dataNascimento;
	private Image image;
	private String foto;
	private String id;
	private String registroGCM;

	public Caroneiro(String nome, String telefone, String profissao, String idade) {

		this.nome = nome;
		this.telefone = telefone;
		this.profissao = profissao;
		this.dataNascimento = idade;
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

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String data) {
		this.dataNascimento = data;
	}

	public String getFoto() {
		
		return foto;
	}
	
	public void setFoto(){
		this.foto = image.getBitmapBase64();
	}

	

}
