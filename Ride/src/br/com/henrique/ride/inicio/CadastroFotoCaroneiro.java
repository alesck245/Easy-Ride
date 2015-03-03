package br.com.henrique.ride.inicio;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.henrique.ride.R;
import br.com.henrique.ride.beans.Caroneiro;
import br.com.henrique.ride.util.ConversorJSON;
import br.com.henrique.ride.util.Preferencias;
import br.com.henrique.ride.util.Sincronizar;
import br.com.henrique.ride.util.WebClient;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class CadastroFotoCaroneiro extends ActionBarActivity {

	private ImageView imb;
	private Button bConcluir;
	private String nome;
	private String telefone;
	private String profissao;
	private String idade;
	private Caroneiro caroneiro;
	private File file;
	private String respostaServidor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_cadastro_foto_caroneiro);
		getSupportActionBar().hide();

		// RECUPERA OS DADOS DO MOTORISTAS VINDOS DA ACTIVITY ANTERIOR
		Intent dadosCaroneiro = getIntent();
		nome = dadosCaroneiro.getStringExtra("nome");
		telefone = dadosCaroneiro.getStringExtra("telefone");
		profissao = dadosCaroneiro.getStringExtra("profissao");
		idade = dadosCaroneiro.getStringExtra("idade");

		caroneiro = new Caroneiro(nome, telefone, profissao, idade);

		Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_anonimo);

		Bitmap bt = RoundedImageView.getCroppedBitmap(largeIcon, 120);

		bConcluir = (Button) findViewById(R.id.enviarFotoC);
		bConcluir.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (file == null) {
					Toast.makeText(CadastroFotoCaroneiro.this,
							"Toque na imagem para alterar", Toast.LENGTH_LONG)
							.show();
				} else {

					Sincronizar sin = new Sincronizar(
							CadastroFotoCaroneiro.this);
					sin.execute();
				}

			}
		});

		imb = (ImageView) findViewById(R.id.bFotoCaroneiro);
		imb.setImageBitmap(bt);

		// SETA O LISTENER QUE BUSCA A FOTO NA GALERIA
		imb.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(intent, 1);

			}
		});
	}

	// SALVA CADASTRO NAS SHAREDPREFERENCES
	public void salvaLogin() {

		SharedPreferences preferencias = getApplicationContext()
				.getSharedPreferences("Cadastro", MODE_PRIVATE);
		Editor editorP = preferencias.edit();
		editorP.putString("Categoria", "caroneiro");
		editorP.putString("id", respostaServidor);// ATRIBUTO QUE IRÁ
													// IDENTIFICAR O USUÁRIO NO
													// BD
		editorP.commit();

	}

	// MÉTODO QUE ENVIA O CADASTRO
	public String enviaFormulario() {
		try {

			String url = "http://pussyclass.com.br/Bora/cadastroCaroneiro.php";

			GoogleCloudMessaging gcm = GoogleCloudMessaging
					.getInstance(CadastroFotoCaroneiro.this);

			String register = gcm.register("747351160147");
			caroneiro.setRegistroGCM(register);

			ConversorJSON conversorJ = new ConversorJSON(caroneiro);
			WebClient clienteW = new WebClient(this, url);

			String dados = conversorJ.converteCaroneiro();
			respostaServidor = clienteW.enviarDados(dados);

			if (respostaServidor.equals("erro")) {
				return null;
			}

		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}

		return respostaServidor;

	}

	// SETA O RETORNO DA IMAGEM BUSCADA NA GALERIA
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_CANCELED) {
			finish();

		}
		if (resultCode == RESULT_OK) {
			Uri selectedimg = data.getData();
			String[] cols = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedimg, cols, null,
					null, null);
			cursor.moveToFirst();

			int indexCol = cursor.getColumnIndex(cols[0]);
			String imgString = cursor.getString(indexCol);
			cursor.close();

			file = new File(imgString);

			// ARMAZENA E TRATA A IMAGEM DO MOTORISTA
			if (file != null) {
				caroneiro.getImage().setResizedBitmap(file, 240, 240);

			}

			// EXIBE A IMAGEM NO BOTAO
			if (caroneiro.getImage().getBitmap() != null) {
				// imb.setImageBitmap(caroneiro.getImage().getBitmap());
				imb.setImageBitmap(RoundedImageView.getCroppedBitmap(caroneiro
						.getImage().getBitmap(), 120));
			}

		}

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (Preferencias.getID(this) != null) {
			finish();
		}
	}

	@Override
	protected void onPause() {

		super.onPause();
		if (Preferencias.getID(this) != null) {
			finish();
		}
	}

}
