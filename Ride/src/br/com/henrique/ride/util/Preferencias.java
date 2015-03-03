package br.com.henrique.ride.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Preferencias {

	public static String getID(Context contexto) {
		SharedPreferences preferencias = contexto.getSharedPreferences(
				"Cadastro", Context.MODE_PRIVATE);
		
		String idUser = preferencias.getString("id", null);
		if(idUser != null){
		String idU = idUser.replaceAll("\n", "");
		String id = idU.replaceAll(" ", "");
		return id;
		}
		
		return null;
	}

	public static String getCategoria(Context contexto) {
		SharedPreferences preferencias = contexto.getSharedPreferences(
				"Cadastro", Context.MODE_PRIVATE);
		String categoria = preferencias.getString("Categoria", null);
		return categoria;
	}

	public static void inserirPontos(Context contexto, int pontos) {

		SharedPreferences preferencias = contexto.getSharedPreferences(
				"Pontuação", Context.MODE_PRIVATE);
		Editor editorP = preferencias.edit();
		editorP.putInt("pontos", pontos);
		editorP.commit();

	}

	public static int getPontos(Context contexto) {
		SharedPreferences preferencias = contexto.getSharedPreferences(
				"Pontuação", Context.MODE_PRIVATE);
		int p = preferencias.getInt("pontos", 0);
		return p;
	}

}
