package br.com.henrique.ride.background;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.henrique.ride.R;
import br.com.henrique.ride.util.ConfirmacaoCarona;
import br.com.henrique.ride.util.Preferencias;
import br.com.henrique.ride.util.WebClient;

import com.google.gson.Gson;

public class NotificacaoMotorista extends ActionBarActivity {

	private String nome = null;
	private String profissao;
	private TextView fulano;
	private String dataNascimento;
	private TextView profissao2;
	private TextView textViewIdade;
	private String destino;
	private String idCaroneiro;
	private String valor;
	private String idCarona;
	private String imagem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_notificacao_motorista);
		
		
		ActionBar ab;
		ab = getSupportActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
		

		String name = getIntent().getStringExtra("nome");
		imagem = getIntent().getStringExtra("foto");
		String ocupation = getIntent().getStringExtra("profissao");
		String birthday = getIntent().getStringExtra("dataNascimento");
		String destiny = getIntent().getStringExtra("destino");
		String idRider = getIntent().getStringExtra("idCaroneiro");
		String value = getIntent().getStringExtra("valor");
		String idRide = getIntent().getStringExtra("idCarona");
		
		// RECUPERA DADOS VINDOS DO GCM
		nome = name;
		profissao = ocupation;
		dataNascimento = birthday;
		destino = destiny;
		idCaroneiro = idRider;
		valor = value;
		idCarona = idRide;
		
		// SETA AS VIEWS COM OS DADOS RECEBIDOS DO GCM
		setaViews();

		Button conceder = (Button) findViewById(R.id.bConfirmarCarona);
		conceder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// CONFIRMA A CONCESSÃO DA CARONA NO SERVIDOR QUE ATUALIZA O
				// CAMPO STATUS DA TABELA CARONA PARA TRUE

				new Thread(new Runnable() {

					@Override
					public void run() {
						String idMotorista = Preferencias
								.getID(NotificacaoMotorista.this);

						ConfirmacaoCarona cc = new ConfirmacaoCarona(
								idCaroneiro, idMotorista, destino, valor,
								idCarona);
						Gson gson = new Gson();
						String json = gson.toJson(cc);

						try {

							WebClient wc = new WebClient(
									"http://pussyclass.com.br/Bora/confirmaCarona.php");

							wc.enviarDados(json);

						} catch (IOException e) {

							finish();
							e.printStackTrace();
						}
					}
				}).start();
				
				Toast.makeText(NotificacaoMotorista.this, "Obrigado",
						Toast.LENGTH_LONG).show();
				
				// ELIMINA A NOTIFICAÇÃO DA ACTIONBAR
				NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				nm.cancel(imagem, R.id.b_caroneiro);
				
				finish();

			}
		});

		// NEGA A CARONA
		Button negar = (Button) findViewById(R.id.bNegarCarona);
		negar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// ELIMINA A NOTIFICAÇÃO DA ACTIONBAR
				NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				nm.cancel(imagem, R.id.b_caroneiro);
				
				finish();

			}
		});

	}

	public void setaViews() {
		try {

			// LÓGICA PARA CALCULAR IDADE
			int anoNascimento = Integer
					.parseInt(dataNascimento.substring(0, 4));
			Calendar now = Calendar.getInstance();
			int anoAtual = now.get(Calendar.YEAR);
			int idade = anoAtual - anoNascimento;

			textViewIdade = (TextView) findViewById(R.id.idade);
			textViewIdade.setText(", " + String.valueOf(idade));

			new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
			.execute(imagem);
						
			fulano = (TextView) findViewById(R.id.fulano);
			fulano.setText(nome);

			profissao2 = (TextView) findViewById(R.id.profissao);
			profissao2.setText(profissao);

		} catch (Exception e) {

			e.printStackTrace();

			finish();
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
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
			
		}
	}

}
