package br.com.henrique.ride.util;

import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.henrique.ride.beans.Carona;

public class SincronizarCarona extends AsyncTask<Integer, Double, String> {

	private Carona carona;
	private Context contexto;
	private ProgressDialog progress;
	private ConversorJSON conversor;
	private String url;

	public SincronizarCarona(Carona carona, Context contexto, String url) {

		this.carona = carona;
		this.contexto = contexto;
		this.url = url;
	}

	@Override
	protected void onPreExecute() {

		progress = ProgressDialog.show(contexto, "Aguarde...",
				"Enviando dados", true, false);

	}

	@Override
	protected String doInBackground(Integer... params) {
		
		

		// CADASTRA A CARONA NO SERVIDOR
		try {
			//SETAR URL PARA ENVIO
			WebClient wc = new WebClient(url);
			conversor = new ConversorJSON(carona);
			String dadosJSON = conversor.converteCarona();

			String idCarona = wc.enviarDados(dadosJSON);
			return idCarona;
		} catch (IOException e) {
			e.getStackTrace();
			return null;
		}

	}

	@Override
	protected void onPostExecute(String result) {

		if (result.equals("erro")) {

			progress.dismiss();
			Toast.makeText(contexto, "Erro no Servidor... Tente novamente",
					Toast.LENGTH_LONG).show();

		} else

			// LIBERA O PROGRESS
			progress.dismiss();
		Toast.makeText(
				contexto,
				"Carona Registrada",
				Toast.LENGTH_LONG).show();
	}
}
