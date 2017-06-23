package com.fime.fsw.huella.huella.fingerprint;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.fime.fsw.huella.huella.data.HuellaContract;
import com.fime.fsw.huella.huella.data.HuellaDBHelper;
import com.rscja.deviceapi.Fingerprint;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by ensardz on 20/06/2017.
 */

public class HuellaIdentTask extends AsyncTask<Integer, Integer, String> {

    private ProgressDialog progressDialog;
    private Context mContext;

    private Fingerprint mFingerprint;

    private String usuarioNombre;
    private String usuarioHexData;
    private String data;
    private String usuarioID;
    private HuellaDBHelper mDBHelper;

    private TextView nombreTV;

    public HuellaIdentTask(Context context, Fingerprint fingerprint, String uid, TextView nombre) {
        mContext = context;
        mFingerprint = fingerprint;
        progressDialog = new ProgressDialog(mContext);
        usuarioID = uid;
        mDBHelper = new HuellaDBHelper(mContext);
        nombreTV = nombre;
    }

    @Override
    protected String doInBackground(Integer... params) {

        boolean exeSucc = false;

        if (!mFingerprint.getImage()) {
            return null;
        }

        if (mFingerprint.genChar(Fingerprint.BufferEnum.B1)){
            exeSucc = true;
        }

        if (mFingerprint.downChar(Fingerprint.BufferEnum.B2, usuarioHexData)){
            exeSucc = true;
        }

        if (exeSucc) {
            int result = 0;
            int exeCount = 0;

            //TODO: Comparar hulla que se recolecto en AcqTask, guardada en variable de UI
            do {
                exeCount++;
                result = mFingerprint.match();

            } while (result == 0 && exeCount < 3);

            Log.i(TAG, "exeCount=" + exeCount);
            Log.i(TAG, "matchValue= " + result);

            if (result != -1) {
                data = mFingerprint.upChar(Fingerprint.BufferEnum.B1);
                Log.i(TAG, "Hubo match");
                Log.e(TAG,data);
                return "ok";
            }else{
                Log.e(TAG, "No hubo match");
            }



            Log.i(TAG, "search result Empty");
        }



        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        progressDialog.cancel();

        if (TextUtils.isEmpty(result)) {
            //Fallo la identificacion
            usuarioNombre = "empty";
            Toast.makeText(mContext, "No se encontro: "+usuarioNombre, Toast.LENGTH_SHORT).show();
            nombreTV.setText(usuarioNombre);
            return;
        }

       //Identificacion exitosa

        Toast.makeText(mContext, "Se encontro a: "+ usuarioNombre, Toast.LENGTH_SHORT).show();
        nombreTV.setText(usuarioNombre);

        usuarioNombre = null;
        usuarioHexData = null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //TODO: Sacar el hex huella con el id del maestro
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String[] projection = {
                HuellaContract.HuellaEntry.COLUMNA_NOMBRE,
                HuellaContract.HuellaEntry.COLUMNA_HUELLA
        };
        String selection = HuellaContract.HuellaEntry._ID + " = ?";
        String[] selectionArgs = { usuarioID.toString() };
        Cursor cursor = db.query(
                HuellaContract.HuellaEntry.TABLA_USUARIO_NOMBRE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );


        while (cursor.moveToNext()){
            //Hex data huella Maestro por ID
            usuarioNombre = cursor.getString(cursor.getColumnIndex(HuellaContract.HuellaEntry.COLUMNA_NOMBRE));
            usuarioHexData = cursor.getString(cursor.getColumnIndex(HuellaContract.HuellaEntry.COLUMNA_HUELLA));
        }
        cursor.close();

        mDBHelper.close();
        db.close();

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    public String getData(){
        return data;
    }

}
