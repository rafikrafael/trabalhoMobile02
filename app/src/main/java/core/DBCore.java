package core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rafikrafael on 19/04/2018.
 */


public class DBCore extends SQLiteOpenHelper {
    private static final String NOME="validaMeusJogos";
    private static final int VERSAO=3;

    public DBCore(Context context){
        super(context, NOME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table jogos ( " +
                " id integer primary key autoincrement, " +
                " tipoJogo integer, " +
                " numeros varchar(100), " +
                " dataConcurso datetime, " +
                " qtdeAcertos integer, " +
                " numeroConcurso varchar(100), " +
                " resultadoConcurso varchar(100) " +
                " );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table jogos;");
        onCreate(db);
    }
}
