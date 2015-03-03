package br.com.henrique.ride.daos;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.henrique.ride.beans.Carona;

public class CaronaDAO extends SQLiteOpenHelper {

	private static final String BD = "RIDE";
	private static final String TABELA = "CARONA";
	private static final int VERSAO = 1;

	public CaronaDAO(Context context) {
		super(context, BD, null, VERSAO);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = "CREATE TABLE " + TABELA
				+ " (ID INT NOT NULL, DATA DATE NOT NULL, PRIMARY KEY (ID));";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + TABELA);
		onCreate(db);

	}

	public void inserirCarona(Carona carona) {

		SQLiteDatabase db = getWritableDatabase();

		ContentValues valores = new ContentValues();
		valores.put("ID", carona.getIdCarona());
		//valores.put("DATA", CaronaWebView.transformaData(carona.getData()));

		db.insert(TABELA, null, valores);

	}

	public List<Integer> getIDCaronas() {

		List<Integer> listaID = new ArrayList<Integer>();

		String[] coluna = { "ID" };
		String WHERE = "DATA >= NOW()";

		Cursor cursor = getReadableDatabase().query(TABELA, coluna, WHERE,
				null, null, null, null);

		while (cursor.moveToNext()) {
			listaID.add(cursor.getInt(0));
		}

		return listaID;
	}

}
