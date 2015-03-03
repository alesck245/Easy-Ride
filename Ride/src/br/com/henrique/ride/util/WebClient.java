package br.com.henrique.ride.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;

public class WebClient {

	HttpClient client;
	HttpPost post;
	Activity contexto;
	private String url;

	public WebClient() {

	}
	
	public WebClient(String url){
		
		this.url = url;
		
	}

	
	public WebClient(Activity contexto, String url) {
		this.contexto = contexto;
		this.url = url;
	}

	public String enviarDados(String dadosJson) throws IOException {

		String dados = dadosJson;

		client = new DefaultHttpClient();
		post = new HttpPost(url);

		post.setHeader("Content-type", "application/json");
		post.setHeader("Accept", "application/json");
		post.setEntity(new StringEntity(dados));

		HttpResponse resposta = client.execute(post);

		HttpEntity respostaRecebida = resposta.getEntity();

		String respostaEmJson = EntityUtils.toString(respostaRecebida);

		return respostaEmJson;

	}

}
