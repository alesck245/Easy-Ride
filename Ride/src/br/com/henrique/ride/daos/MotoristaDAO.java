package br.com.henrique.ride.daos;

import br.com.henrique.ride.beans.ConexaoBD;
import br.com.henrique.ride.beans.Motorista;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MotoristaDAO {

	private Motorista motorista;
	private ConexaoBD ConexaoBD;
	private SQLiteDatabase db;
	private static final String TABELA = "motorista";

	public MotoristaDAO(Motorista motorista, Context contexto) {

		this.motorista = motorista;
		ConexaoBD = new ConexaoBD(contexto, TABELA);
	}

	public void inserirMotoristaNoBD() {

		db = ConexaoBD.getWritableDatabase();

		ContentValues valores = new ContentValues();
		valores.put("nome", motorista.getNome());
		valores.put("telefone", motorista.getTelefone());
		
		db.insert("motorista", null, valores);

	}
	
	
}
