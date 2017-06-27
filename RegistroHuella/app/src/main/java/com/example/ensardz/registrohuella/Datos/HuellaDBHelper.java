package com.example.ensardz.registrohuella.Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ensardz on 26/06/2017.
 */


public class HuellaDBHelper extends SQLiteOpenHelper {

    public static final int BASEDATOS_VERSION = 1;
    public static final String NOMBRE_BASEDATOS = "base3.db";
    public static final String SQL_CREATE =
            "CREATE TABLE " + HuellaContract.HuellaEntry.TABLA_USUARIO_NOMBRE + " (" +
                    HuellaContract.HuellaEntry._ID + " INTEGER PRIMARY KEY," +
                    HuellaContract.HuellaEntry.COLUMNA_NUMERO_EMPLEADO + " TEXT," +
                    HuellaContract.HuellaEntry.COLUMNA_NOMBRE + " TEXT," +
                    HuellaContract.HuellaEntry.COLUMNA_HUELLA + " TEXT)";


    public static final String SQL_DELETE = "DROP TABLE IF EXISTS " + HuellaContract.HuellaEntry.TABLA_USUARIO_NOMBRE;

    public HuellaDBHelper(Context context) {
        super(context, NOMBRE_BASEDATOS, null, BASEDATOS_VERSION);
        Log.i("TABLA SQL", SQL_CREATE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}