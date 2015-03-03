package br.com.henrique.ride.gcm;

import java.util.Random;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import br.com.henrique.ride.R;
import br.com.henrique.ride.background.NotificacaoCaroneiro;
import br.com.henrique.ride.background.NotificacaoMotorista;

public class BroadCastGCM extends BroadcastReceiver {

	private Context contexto;
	private Bundle extras;

	private String nome;
	private String imagem;

	private String destino;

	private String telefone;

	@Override
	public void onReceive(Context context, Intent intent) {

		contexto = context;
		extras = intent.getExtras();// RECUPERA OS DADOS VINDOS DO GCM

		// CHAMA A NOTIFICAÇÃO ADEQUADA
		if (extras.containsKey("caroneiro")) {

			nome = extras.getString("nome");
			imagem = extras.getString("foto");

			destino = extras.getString("destino");

			gerarNotificacaoMotorista();
		}
		if (extras.containsKey("motorista")) {

			nome = extras.getString("nome");
			imagem = extras.getString("foto");
			destino = extras.getString("destino");
			telefone = extras.getString("telefone");

			gerarNotificacaoCaroneiro();
		}
	}

	private void gerarNotificacaoCaroneiro() {
		Intent notIntent = new Intent(contexto, NotificacaoCaroneiro.class);
		notIntent.putExtras(extras);
		int idn = new Random().nextInt(543254);

		NotificationManager nm = (NotificationManager) contexto
				.getSystemService(Context.NOTIFICATION_SERVICE);
		PendingIntent pi = PendingIntent.getActivity(contexto, idn, notIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				contexto);
		builder.setTicker(nome + " aceitou seu pedido de carona para "
				+ destino);
		builder.setContentTitle(nome);
		builder.setContentText("Tel: " + telefone);
		builder.setLargeIcon(BitmapFactory.decodeResource(
				contexto.getResources(), R.drawable.carrinho));
		builder.setSmallIcon(R.drawable.ic_action_good);
		builder.setContentIntent(pi);
		Uri buz = Uri.parse("android.resource://" + contexto.getPackageName()
				+ "/" + R.raw.buz);
		builder.setSound(buz);

		Notification noti = builder.build();

		nm.notify(imagem, R.id.b_caroneiro, noti);

	}

	public void gerarNotificacaoMotorista() {
		Intent it = new Intent(contexto, NotificacaoMotorista.class);
		it.putExtras(extras);
		int idn = new Random().nextInt(543254);

		NotificationManager nm = (NotificationManager) contexto
				.getSystemService(Context.NOTIFICATION_SERVICE);
		PendingIntent pi = PendingIntent.getActivity(contexto, idn, it,
				PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				contexto);
		builder.setTicker("Você tem uma nova solicitação de carona");
		builder.setContentTitle(nome);
		builder.setContentText(destino);
		builder.setLargeIcon(BitmapFactory.decodeResource(
				contexto.getResources(), R.drawable.carrinho));
		builder.setSmallIcon(R.drawable.ic_action_good);
		builder.setContentIntent(pi);
		Uri buz = Uri.parse("android.resource://" + contexto.getPackageName()
				+ "/" + R.raw.buz);
		builder.setSound(buz);

		Notification noti = builder.build();

		nm.notify(imagem, R.id.b_caroneiro, noti);
	}

}
