package br.com.henrique.ride.background;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.henrique.ride.R;

public class NotificacaoCaroneiro extends ActionBarActivity {

	private String telefone;
	private String nome;
	private TextView fulano;
	private String periodo;
	private TextView mail;
	private TextView phone;
	private String valor;
	private TextView value;
	private String imagem;
	private TextView textVagas;
	private String vagas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_notificacao_caroneiro);

		ActionBar ab = getSupportActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

		nome = getIntent().getStringExtra("nome");
		telefone = getIntent().getStringExtra("telefone");
		imagem = getIntent().getStringExtra("foto");
		periodo = getIntent().getStringExtra("periodo");
		vagas = getIntent().getStringExtra("vagas");

		// SETA AS VIEWS COM OS DADOS RECEBIDOS DO GCM
		setaViews();

	}

	private void setaViews() {

		try {

			new DownloadImageTask((ImageView) findViewById(R.id.foto_Motorista))
					.execute(imagem);
			valor = getIntent().getStringExtra("valor");

			fulano = (TextView) findViewById(R.id.nome_Motorista);
			fulano.setTextAppearance(this,
					android.R.style.TextAppearance_Medium);
			fulano.setText(nome);

			// MAIL VIROU PERÍODO
			mail = (TextView) findViewById(R.id.email_motorista);
			mail.setText(periodo);

			phone = (TextView) findViewById(R.id.tel_Motorista);
			phone.setText(telefone);

			value = (TextView) findViewById(R.id.textValor);
			value.setText(valor);

			textVagas = (TextView) findViewById(R.id.vagas);
			textVagas.setText(vagas);

		} catch (Exception e) {

			Log.i("erro", "notificaC", e);

		}

	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
				in.close();
			} catch (Exception e) {
				Log.e("Error", "na imagem", e);
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);

		}
	}

}
