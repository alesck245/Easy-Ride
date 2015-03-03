package br.com.henrique.ride;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import br.com.henrique.ride.util.Preferencias;

public class ActivityPrincipal extends ActionBarActivity {

	private ActionBar ab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_principal);

		final Animation animTranslate = AnimationUtils.loadAnimation(this,
				R.layout.animation_translate);

		ab = getSupportActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

		ImageButton ib = (ImageButton) findViewById(R.id.bCarona);

		ib.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				v.startAnimation(animTranslate);
				Intent intent = new Intent();
				intent.setClass(ActivityPrincipal.this, Info.class);
				startActivity(intent);

			}
		});

	}

	@Override
	protected void onResume() {

		super.onResume();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.activity_principal, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.bonus) {
			String categoria = Preferencias
					.getCategoria(ActivityPrincipal.this);

			if (categoria != null && categoria.equals("caroneiro")) {

				Intent intent = new Intent();
				intent.setClass(ActivityPrincipal.this,
						FormularioCaroneiro.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			} else {

				Intent intent = new Intent();
				intent.setClass(ActivityPrincipal.this,
						FormularioMotorista.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

			}
		}
		return super.onOptionsItemSelected(item);
	}

}
