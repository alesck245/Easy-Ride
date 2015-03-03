package br.com.henrique.ride.inicio;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import br.com.henrique.ride.ActivityPrincipal;
import br.com.henrique.ride.R;
import br.com.henrique.ride.util.Preferencias;

public class BoasVindas extends ActionBarActivity {

	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_boas_vindas);

		ActionBar ab = getSupportActionBar();
		ab.hide();

		if (checkPlayServices()) {

			// VERIFICA SE O USUÁRIO JÁ E CADASTRADO
			SharedPreferences preferencias = getBaseContext()
					.getSharedPreferences("Cadastro", MODE_PRIVATE);

			if (preferencias.contains("id")) {

				Intent intent = new Intent(this, ActivityPrincipal.class);
				startActivity(intent);
			}

			final Animation animTranslate = AnimationUtils.loadAnimation(this,
					R.anim.alpha);

			Button bMotorista = (Button) findViewById(R.id.b_motorista);
			bMotorista.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					v.startAnimation(animTranslate);
					Intent intent = new Intent(BoasVindas.this,
							CadastroMotorista.class);
					startActivity(intent);

				}
			});

			Button bCaroneiro = (Button) findViewById(R.id.b_caroneiro);
			bCaroneiro.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					v.startAnimation(animTranslate);
					Intent intent = new Intent(BoasVindas.this,
							CadastroCaroneiro.class);
					startActivity(intent);

				}
			});
		}
	}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i("TAG", "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	@Override
	protected void onResume() {

		super.onResume();

		if (checkPlayServices()) {

			SharedPreferences preferencias = getBaseContext()
					.getSharedPreferences("Cadastro", MODE_PRIVATE);

			if (preferencias.contains("id")) {

				finish();
			}
		}
	}

	@Override
	protected void onStop() {

		super.onStop();
		if (Preferencias.getID(this) != null) {
			finish();
		}
	}

}
