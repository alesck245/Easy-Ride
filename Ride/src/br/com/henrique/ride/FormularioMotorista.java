package br.com.henrique.ride;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.henrique.ride.beans.Carona;
import br.com.henrique.ride.util.ComboBoXListener;
import br.com.henrique.ride.util.MonetaryMask;
import br.com.henrique.ride.util.Preferencias;
import br.com.henrique.ride.util.SincronizarCarona;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class FormularioMotorista extends ActionBarActivity implements
		Button.OnClickListener {

	private ActionBar aba;
	private Button botao;
	private List<String> nomes = new ArrayList<String>();
	private EditText campoVagas;
	private Spinner campoPeriodo;
	private EditText campoDespesas;
	private Spinner spn1;
	private Spinner spn2;
	private Spinner spn3;
	private Spinner spn4;
	private String idUsuario;
	private String origemCidade;
	private String destinoCidade;
	private String vagas;
	private String despesas;
	private String periodo;

	static final int DATE_DIALOG_ID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_formulario_motorista);
		aba = getSupportActionBar();
		aba.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

		populaSpinner();

		AdView ad = (AdView) findViewById(R.id.motorista_adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		ad.loadAd(adRequest);

		campoVagas = (EditText) findViewById(R.id.campoVagas);

		campoDespesas = (EditText) findViewById(R.id.campoDespesas);
		campoDespesas.addTextChangedListener(new MonetaryMask(campoDespesas));

		botao = (Button) findViewById(R.id.date_picker);
		botao.setOnClickListener(this);

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
		vagas = campoVagas.getText().toString();
		despesas = campoDespesas.getText().toString();
		periodo = campoPeriodo.getSelectedItem().toString();
		validaFormulario();

		Carona carona = new Carona(idUsuario, origemCidade, destinoCidade, data);
		carona.setDespesas(despesas);
		carona.setPeriodo(periodo);
		carona.setVagas(Integer.parseInt(vagas));

		// ENVIA A CARONA PRO BANCO DE DADOS
		String url = "http://pussyclass.com.br/Bora/caronaOferecida.php";
		SincronizarCarona sc = new SincronizarCarona(carona, this, url);
		sc.execute();

		return true;

	}

	public boolean validaFormulario() {

		if (vagas.equals(null) || vagas.equals("")) {
			vagas = "4";
		}

		if (despesas.equals(null) || despesas.equals("")) {
			despesas = "R$0,00";
		}
		return true;

	}

	private void populaSpinner() {

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

		String[] periodos = { "Manhã", "Tarde", "Noite" };

		campoPeriodo = (Spinner) findViewById(R.id.campoHorarioSaida);
		ArrayAdapter<String> periodosAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, periodos);
		campoPeriodo.setAdapter(periodosAdapter);

		spn2 = (Spinner) findViewById(R.id.cidadeOrigem);

		spn1 = (Spinner) findViewById(R.id.estadoOrigem);
		spn1.setOnItemSelectedListener(new ComboBoXListener(this, spn2));

		spn4 = (Spinner) findViewById(R.id.cidadeDestino);

		spn3 = (Spinner) findViewById(R.id.estadoDestino);
		spn3.setOnItemSelectedListener(new ComboBoXListener(this, spn4));

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, nomes);
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
		spinnerArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spn1.setAdapter(spinnerArrayAdapter);

		spn3.setAdapter(spinnerArrayAdapter);

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

			// ABAIXO A IMPLEMENTAÇÃO DE ENVIO DA DATA E DEMAIS DADOS
			setaCarona(data);

		}
	};

	@Override
	public void onClick(View v) {

		if (v == botao) {

			if (spn2 == null || spn4 == null) {
				Toast.makeText(this,
						"Os campos de Saíde e de Destino estão vazios",
						Toast.LENGTH_LONG).show();
			}

			else

				showDialog(DATE_DIALOG_ID);

		}
	}

}
