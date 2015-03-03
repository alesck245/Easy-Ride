package br.com.henrique.ride.inicio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import br.com.henrique.ride.R;
import br.com.henrique.ride.util.Preferencias;

public class CadastroProfissaoCaroneiro extends ActionBarActivity implements
		Button.OnClickListener {

	private EditText campoProfissao;
	private EditText campoIdade;
	private ImageButton botao;
	static final int DATE_DIALOG_ID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.layout_cidade_caroneiro);

		// RECUPERA OS DADOS VINDOS DA ACTIVITY ANTERIOR
		Intent dadosCaroneiro = getIntent();
		final String nome = dadosCaroneiro.getStringExtra("nome");
		final String telefone = dadosCaroneiro.getStringExtra("telefone");

		// ACESSA AS VIEWS
		campoProfissao = (EditText) findViewById(R.id.campoProfissaoCaroneiro);
		campoIdade = (EditText) findViewById(R.id.campoIdadeCaroneiro);

		campoIdade.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					showDialog(DATE_DIALOG_ID);
				}

			}
		});

		botao = (ImageButton) findViewById(R.id.b_CidadeCaroneiro);

		// SETA O LISTENER DO BOTÃO
		botao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String profissao = campoProfissao.getText().toString();
				String idade = campoIdade.getText().toString();

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				sdf.setLenient(false);

				try {
					//DATA VÁLIDA
					Date data = sdf.parse(idade);
					
				} catch (ParseException e) {
					// se cair aqui, a data é inválida
					idade = null;
				}

				// Valida o campo Cidade
				if (!profissao.matches("[\\p{L}]+") || nome == null) {
					campoProfissao.setError("Campo Inválido");

				} else if (idade == null) {
					campoIdade.setError("Data inválida");
				} else {

					// Tendo validado o formulário vai para a última parte do
					// cadastro
					Intent irParaCadastroFotoC = new Intent(
							CadastroProfissaoCaroneiro.this,
							CadastroFotoCaroneiro.class);
					irParaCadastroFotoC.putExtra("nome", nome);
					irParaCadastroFotoC.putExtra("telefone", telefone);
					irParaCadastroFotoC.putExtra("profissao", profissao);
					irParaCadastroFotoC.putExtra("idade", idade);
					startActivity(irParaCadastroFotoC);
					overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

				}

			}
		});

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
			campoIdade.setText(data);
		}
	};

	@Override
	public void onClick(View v) {
		if (v == campoIdade)
			showDialog(DATE_DIALOG_ID);
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		if(Preferencias.getID(this) != null){
		finish();
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onStop();
		if(Preferencias.getID(this) != null){
			finish();
			}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(Preferencias.getID(this) != null){
			finish();
			}
	}

}
