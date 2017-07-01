package com.ensardz.huellaapitest.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ensardz on 23/06/2017.
 */

public class HuellaDBHelper extends SQLiteOpenHelper {

    public static final int BASEDATOS_VERSION = 1;
    public static final String NOMBRE_BASEDATOS = "Huella.db";
    public static final String SQL_CREATE =
            "CREATE TABLE " + HuellaContract.HuellaEntry.TASK_TABLA_NOMBRE + " (" +
                    HuellaContract.HuellaEntry._ID + " INTEGER PRIMARY KEY," +
                    HuellaContract.HuellaEntry.COLUMNA_ROOM + " TEXT," +
                    HuellaContract.HuellaEntry.COLUMNA_ASSIGNMENT + " TEXT," +
                    HuellaContract.HuellaEntry.COLUMNA_ACADEMY_HOUR + " TEXT," +
                    HuellaContract.HuellaEntry.COLUMNA_BARCODE + " TEXT," +
                    HuellaContract.HuellaEntry.COLUMNA_EMPLOYEE_NUMBER + " TEXT," +
                    HuellaContract.HuellaEntry.COLUMNA_EMPLOYEE_NAME + " TEXT," +
                    HuellaContract.HuellaEntry.COLUMNA_EMPLOYEE_FULLNAME + " TEXT," +
                    HuellaContract.HuellaEntry.COLUMNA_HEXCODE + " TEXT" +
                    ")";

    public static final String SQL_DELETE = "DROP TABLE IF EXISTS " + HuellaContract.HuellaEntry.TASK_TABLA_NOMBRE;

    public HuellaDBHelper(Context context) {
        super(context, NOMBRE_BASEDATOS, null, BASEDATOS_VERSION);
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
