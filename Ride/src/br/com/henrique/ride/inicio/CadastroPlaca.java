package br.com.henrique.ride.inicio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import br.com.henrique.ride.R;
import br.com.henrique.ride.util.Preferencias;

public class CadastroPlaca extends ActionBarActivity {

	private String email;
	private String placa;
	private EditText campoEmail;
	private EditText campoPlaca;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_cadastro_placa);
		getSupportActionBar().hide();
		
		//Recupera os dados passados da primeira etapa do cadastro
		Intent dadosMotorista = getIntent();
		final String nome = dadosMotorista.getStringExtra("nome");
		final String telefone = dadosMotorista.getStringExtra("telefone");

		campoEmail = (EditText) findViewById(R.id.campoEmail);
		campoPlaca = (EditText) findViewById(R.id.campoPlaca);
		
		//FORMATA A PLACA
		campoPlaca.addTextChangedListener(Mask.insert("###-####", campoPlaca));

		ImageButton ib = (ImageButton) findViewById(R.id.b_imagem2);
		ib.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				//RECUPERA DADOS DO FORMULÁRIO
				email = campoEmail.getText().toString();
				placa = campoPlaca.getText().toString();
				
				//VALIDA O EMAIL
				if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

					campoEmail.setError("Email Inválido");

					//VALIDA A PLACA
				} else if(!placa.matches("[a-zA-Z]{3}\\-\\d{4}$") || placa == null){
					campoPlaca.setError("Placa Inválida");
				}
				//CHAMA A PRÓXIMA ACTIVITY
				else {

					Intent intent = new Intent(CadastroPlaca.this,
							CadastroFotoMotorista.class);
					intent.putExtra("nome", nome);
					intent.putExtra("telefone", telefone);
					intent.putExtra("email", email);
					intent.putExtra("placa", placa);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
				}
			}
		});
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
