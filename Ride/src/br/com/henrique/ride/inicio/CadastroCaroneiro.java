package br.com.henrique.ride.inicio;

import br.com.henrique.ride.R;
import br.com.henrique.ride.util.Preferencias;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class CadastroCaroneiro extends ActionBarActivity {
	
	private String nome;
	private String telefone;
	private EditText campoNome;
	private EditText campoTelefone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_cadastro_caroneiro);
		getSupportActionBar().hide();
		
		campoNome = (EditText) findViewById(R.id.campoNomeCaroneiro);
		campoTelefone = (EditText) findViewById(R.id.campoTelefoneCaroneiro);
		ImageButton botao = (ImageButton) findViewById(R.id.b_imagemCaroneiro);

		// Formata o campo Telefone
		campoTelefone.addTextChangedListener(Mask.insert("(###)####-####",
				campoTelefone));
		
		botao.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				//RECUPERA DADOS DO FORMULÁRIO
				nome = campoNome.getText().toString();
				telefone = campoTelefone.getText().toString();

				// Valida o campo Nome
				if (!nome.matches("[\\p{L}]+")
						|| nome == null) {
					campoNome.setError("Nome Inválido");

				} else if (telefone.length() < 13) {
					campoTelefone.setError("Telefone Inválido");
				}

				else {
					//Tendo validado o formulário vai para a segunda parte do cadastro
					Intent irParaCadastroCidadeC = new Intent(
							CadastroCaroneiro.this, CadastroProfissaoCaroneiro.class);
					
					irParaCadastroCidadeC.putExtra("nome", nome);
					irParaCadastroCidadeC.putExtra("telefone", telefone);
					
					startActivity(irParaCadastroCidadeC);
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(Preferencias.getID(this) != null){
			finish();
			}
	}

}
