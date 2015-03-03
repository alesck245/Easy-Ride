package br.com.henrique.ride.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ComboBoXListener extends Thread implements OnItemSelectedListener {

	private Spinner spn;
	private Context contexto;
	private List<String> cidades = new ArrayList<String>();

	public ComboBoXListener(Context contexto, Spinner spn) {
		this.contexto = contexto;
		this.spn = spn;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		if (position > 0) {

			// VERIFICA NAS SHARED PREFERENCES SE AS CIDADES JÁ ESTÃO
			// ARMAZENADAS
			SharedPreferences preferencias = contexto.getApplicationContext()
					.getSharedPreferences("Cidades", Context.MODE_PRIVATE);

			if (preferencias.contains(position + "")) {
				// COLOCAR UM TRY CATCH
				Log.i("GSON", "NAO DEVERIA EXECUTAR");
				Gson gson = new Gson();
				Type type = new TypeToken<List<String>>() {
				}.getType();
				cidades = gson.fromJson(
						preferencias.getString(String.valueOf(position), null),
						type);

				ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
						contexto,
						android.R.layout.simple_spinner_dropdown_item, cidades);
				spn.setAdapter(arrayAdapter);

			} else {

				BuscaCidadeBD preencheSpinner = new BuscaCidadeBD(spn,
						position, contexto);
				preencheSpinner.execute();
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
