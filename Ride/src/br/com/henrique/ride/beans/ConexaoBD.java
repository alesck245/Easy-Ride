package br.com.henrique.ride.beans;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexaoBD extends SQLiteOpenHelper {

	private static final String NOME_BD = "RIDE";
	private static int VERSAO = 1;
	private String TABELA;

	public ConexaoBD(Context context, String tabela) {
		super(context, NOME_BD, null, VERSAO);
		this.TABELA = tabela;

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = "CREATE TABLE " + TABELA + "(nome TEXT NOT NULL, "
				+ "telefone TEXT NOT NULL, PRIMARY KEY (telefone));";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + TABELA);
		onCreate(db);

	}

}
