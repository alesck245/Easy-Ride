package br.com.henrique.ride.util;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.henrique.ride.ActivityPrincipal;
import br.com.henrique.ride.inicio.CadastroFotoMotorista;
import br.com.henrique.ride.inicio.CadastroFotoCaroneiro;

public class Sincronizar extends AsyncTask<Integer, Double, String> {

	private CadastroFotoMotorista finalizaCadastroMotorista;
	private CadastroFotoCaroneiro finalizaCadastroCaroneiro;
	private ProgressDialog progress;

	public Sincronizar(CadastroFotoMotorista finalizaCadastroMotorista) {

		this.finalizaCadastroMotorista = finalizaCadastroMotorista;

	}

	public Sincronizar(CadastroFotoCaroneiro finCadastroFotoCaroneiro) {

		this.finalizaCadastroCaroneiro = finCadastroFotoCaroneiro;

	}

	@Override
	protected void onPreExecute() {

		if (finalizaCadastroMotorista != null) {
			progress = ProgressDialog.show(finalizaCadastroMotorista,
					"Aguarde...", "Enviando dados", true, false);
		} else if (finalizaCadastroCaroneiro != null) {

			progress = ProgressDialog.show(finalizaCadastroCaroneiro,
					"Aguarde...", "Enviando dados", true, false);
		}
	}

	@Override
	protected String doInBackground(Integer... params) {

		if (finalizaCadastroMotorista != null) {

			String retornoM = finalizaCadastroMotorista.enviaFormulario();
			return retornoM;

		}

		else  if (finalizaCadastroCaroneiro != null){

			String retornoC = finalizaCadastroCaroneiro.enviaFormulario();
			return retornoC;

		}
		
		return null;

	}

	@Override
	protected void onPostExecute(String result) {

		if (result == null && finalizaCadastroCaroneiro != null) {
			progress.dismiss();
			Toast.makeText(finalizaCadastroCaroneiro,
					"Servidor não encontrado. Tente novamente",
					Toast.LENGTH_LONG).show();
			

		}

		else if (result == null && finalizaCadastroMotorista != null) {
			progress.dismiss();
			Toast.makeText(finalizaCadastroMotorista,
					"Servidor não encontrado. Tente novamente",
					Toast.LENGTH_LONG).show();
			
		}

		else if (result != null && finalizaCadastroMotorista != null) {
			progress.dismiss();
			Toast.makeText(finalizaCadastroMotorista, "Cadastro Realizado",
					Toast.LENGTH_LONG).show();
			
			finalizaCadastroMotorista.salvaLogin();

			Intent intent = new Intent();
			intent.setClass(finalizaCadastroMotorista, ActivityPrincipal.class);
			finalizaCadastroMotorista.startActivity(intent);
		}

		else if (result != null && finalizaCadastroCaroneiro != null) {
			progress.dismiss();
			Toast.makeText(finalizaCadastroCaroneiro, "Cadastro Realizado",
					Toast.LENGTH_LONG).show();
			
			finalizaCadastroCaroneiro.salvaLogin();
			
			Intent intent = new Intent();
			intent.setClass(finalizaCadastroCaroneiro, ActivityPrincipal.class);
			finalizaCadastroCaroneiro.startActivity(intent);
		}
	}

}
