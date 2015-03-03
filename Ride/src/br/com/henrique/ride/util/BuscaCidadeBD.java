package br.com.henrique.ride.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BuscaCidadeBD extends AsyncTask<Integer, Double, String> {

	private Spinner spn;
	private int idEstado;
	private Context contexto;
	private List<String> cidades;
	private ProgressDialog progress;

	public BuscaCidadeBD(Spinner spinner, int posicao, Context contexto) {
		this.spn = spinner;
		this.idEstado = posicao;
		this.contexto = contexto;
	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();
		progress = ProgressDialog.show(contexto, "", "", true, true);

	}

	@Override
	protected String doInBackground(Integer... params) {
		
		String url = "http://pussyclass.com.br/Bora/selecionaCidades.php";

		cidades = new ArrayList<String>();
		Gson gson = new Gson();
		WebClient wc = new WebClient(url);
		
		try {
			String cidadesJSON = wc.enviarDados(String.valueOf(idEstado));

			Type type = new TypeToken<List<String>>() {
			}.getType();

			cidades = gson.fromJson(cidadesJSON, type);
			armazenaCidades(cidadesJSON);// ARMAZENA NAS SHARED PREFERENCES
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return "ok";
	}

	@Override
	protected void onPostExecute(String result) {

		super.onPostExecute(result);

		if (result == null) {
			Toast.makeText(contexto, "Verifique sua conexão", Toast.LENGTH_LONG)
					.show();
			progress.dismiss();
		} else {

			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
					contexto, android.R.layout.simple_spinner_dropdown_item,
					cidades);
			spn.setAdapter(arrayAdapter);

			progress.dismiss();
		}
	}

	public void armazenaCidades(String cidadesJSON) {

		SharedPreferences preferencias = contexto.getApplicationContext()
				.getSharedPreferences("Cidades", Context.MODE_PRIVATE);

		Editor editorP = preferencias.edit();
		editorP.putString(String.valueOf(idEstado), cidadesJSON);
		editorP.commit();
	}

}
