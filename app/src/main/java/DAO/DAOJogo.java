package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import core.DBCore;
import entidades.Jogo;
import entidades.TipoJogo;

/**
 * Created by rafikrafael on 19/04/2018.
 */

public class DAOJogo {

    SQLiteDatabase db;

    public DAOJogo(Context context){
        db = new DBCore(context).getWritableDatabase();
    }

    public List<Jogo> getAll(String orderBy) {
        List<Jogo> jogos = new ArrayList<Jogo>();
        String[] colunas = {"id","tipoJogo","numeros", "numeroConcurso", "dataConcurso", "qtdeAcertos", "resultadoConcurso"};
        Cursor cursor = db.query("jogos", colunas,null,
                null, null, null, orderBy);
        cursor.moveToFirst();
        for(int x=0; x<cursor.getCount();x++){
            Jogo jogo = new Jogo();
            jogo.setId(cursor.getLong(0));
            jogo.setTipoJogo(TipoJogo.getTipoJogoFromInt(cursor.getInt(1)));
            String[] numeros;
            if (cursor.getString(2).contains(",")) {
                numeros = cursor.getString(2).split(",");
            } else {
                numeros = cursor.getString(2).split(" - ");
            }
            for (String numero: numeros) {
                if (!numero.equals("")) jogo.addNumeros(Integer.parseInt(numero.trim()));
            }
            jogo.setNumeroConcurso(cursor.getString(3));

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            try {
                date = (Date) formatter.parse(cursor.getString(4));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            jogo.setQtdeAcertos(cursor.getInt(5));
            jogo.setResultado(cursor.getString(6));

            jogo.setDataConcurso(date);
            jogos.add(jogo);
            cursor.moveToNext();
        }
        return jogos;

    }

    public void save(Jogo jogo) {
        ContentValues values = new ContentValues();
        values.put("tipoJogo", jogo.getTipoJogo().ordinal());
        String numeros = "";
        for (Integer numero: jogo.getNumeros()) {
            if (!numero.equals("")) {
                numeros += ",";
            }
            numeros += numero.toString();
        }
        values.put("numeros", numeros);
        values.put("numeroConcurso", jogo.getNumeroConcurso());
        values.put("dataConcurso", jogo.getDataConcursoStr());
        values.put("resultadoConcurso", jogo.getResultado());

        if (jogo.getQtdeAcertos() != null) {
            values.put("qtdeAcertos", jogo.getQtdeAcertos());
        }
        if (jogo.getId() != null) {
            db.update("jogos", values, "id=" + jogo.getId(), null);
        } else {
            db.insert("jogos", null, values);
        }
    }

    public void remover(Jogo jogo){
        db.delete("jogos","id="+jogo.getId(),null);
    }
}
