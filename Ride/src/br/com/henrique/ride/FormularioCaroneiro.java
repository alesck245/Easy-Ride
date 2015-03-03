package br.com.henrique.ride;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.henrique.ride.beans.Carona;
import br.com.henrique.ride.util.ComboBoXListener;
import br.com.henrique.ride.util.Preferencias;
import br.com.henrique.ride.util.SincronizarCarona;

public class FormularioCaroneiro extends ActionBarActivity implements
		Button.OnClickListener {

	private ActionBar aba;
	private String idUsuario;
	private Spinner spn1;
	private Spinner spn2;
	private List<String> nomes = new ArrayList<String>();
	private Button botao;
	private Spinner spn3;
	private Spinner spn4;
	static final int DATE_DIALOG_ID = 0;
	private String origemCidade;
	private String destinoCidade;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_formulario_caroneiro);
		aba = getSupportActionBar();
		aba.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
		

		populaSpinner();
		
		 AdView ad = (AdView) findViewById(R.id.caroneiro_adView); AdRequest adRequest =
		 new AdRequest.Builder().build(); ad.loadAd(adRequest);
		
		botao = (Button) findViewById(R.id.c_date_picker);
		botao.setOnClickListener(this);

	}

	public void populaSpinner() {

		nomes.add("Estado");
		nomes.add("Acre");
		nomes.add("Alagoas");
		nomes.add("Amazonas");
		nomes.add("Amapá");
		nomes.add("Bahia");
		nomes.add("Ceará");
		nomes.add("Distrito Federal");
		nomes.add("Espírito Santo");
		nomes.add("Goiás");
		nomes.add("Maranhão");
		nomes.add("Minas Gerais");
		nomes.add("Mato Grosso do Sul");
		nomes.add("Mato Grosso");
		nomes.add("Pará");
		nomes.add("Paraíba");
		nomes.add("Pernambuco");
		nomes.add("Piauí");
		nomes.add("Paraná");
		nomes.add("Rio de Janeiro");
		nomes.add("Rio Grande do Norte");
		nomes.add("Rondônia");
		nomes.add("Roraima");
		nomes.add("Rio Grande do Sul");
		nomes.add("Santa Catarina");
		nomes.add("Sergipe");
		nomes.add("São Paulo");
		nomes.add("Tocantins");

		spn2 = (Spinner) findViewById(R.id.scidadeOrigem);

		spn1 = (Spinner) findViewById(R.id.sestadoOrigem);
		spn1.setOnItemSelectedListener(new ComboBoXListener(this, spn2));

		spn4 = (Spinner) findViewById(R.id.scidadeDestino);

		spn3 = (Spinner) findViewById(R.id.sestadoDestino);
		spn3.setOnItemSelectedListener(new ComboBoXListener(this, spn4));

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, nomes);
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
		spinnerArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spn1.setAdapter(spinnerArrayAdapter);

		spn3.setAdapter(spinnerArrayAdapter);
	}

	// CRIA UMA INSTÂNCIA DA CLASSE CARONA E ENVIA AO SERVIDOR

	public boolean setaCarona(String data) {

		idUsuario = Preferencias.getID(this);// PEGA ID DO USUARIO NAS
												// PREFERENCIAS

		// VERIFICA SE OS SPINNERS FORAM POPULADOS
		if (spn2.getSelectedItem() == null || spn4.getSelectedItem() == null) {
			Toast.makeText(this, "Selecione as cidades de origem e destino",
					Toast.LENGTH_LONG).show();
			return false;
		}

		origemCidade = spn2.getSelectedItem().toString();
		destinoCidade = spn4.getSelectedItem().toString();

		Carona carona = new Carona(idUsuario, origemCidade, destinoCidade, data);

		// ENVIA A CARONA PRO BANCO DE DADOS
		String url = "http://pussyclass.com.br/Bora/caronaPedida.php";
		SincronizarCarona sc = new SincronizarCarona(carona, this, url);
		sc.execute();

		return true;

	}

	@Override
	protected void onResume() {

		super.onResume();

		aba.setTitle("Carona");

	}

	// DEFINE O DATE PICKER

	@Override
	protected Dialog onCreateDialog(int id) {
		Calendar calendario = Calendar.getInstance();

		int ano = calendario.get(Calendar.YEAR);
		int mes = calendario.get(Calendar.MONTH);
		int dia = calendario.get(Calendar.DAY_OF_MONTH);

		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, ano, mes, dia);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			String data = String.valueOf(dayOfMonth) + "/"
					+ String.valueOf(monthOfYear + 1) + "/"
					+ String.valueOf(year);

			// ENVIA A DATA E DEMAIS DADOS
			setaCarona(data);
			
		}
	};

	@Override
	public void onClick(View v) {

		if (spn2 == null || spn4 == null) {
			Toast.makeText(this,
					"Os campos de Saída e de Destino estão vazios",
					Toast.LENGTH_LONG).show();
		}

		else {
			if (v == botao) {

				showDialog(DATE_DIALOG_ID);
			}
		}
	}
}