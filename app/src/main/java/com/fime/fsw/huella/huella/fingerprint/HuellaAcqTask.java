package com.fime.fsw.huella.huella.fingerprint;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.fime.fsw.huella.huella.data.HuellaContract;
import com.fime.fsw.huella.huella.data.HuellaDBHelper;
import com.rscja.deviceapi.Fingerprint;

import org.w3c.dom.Text;

/**
 * Created by ensardz on 20/06/2017.
 */

public class HuellaAcqTask extends AsyncTask<Integer, Integer, String> {

    private static final String TAG = HuellaAcqTask.class.getSimpleName();

    private Context mContext;
    private Fingerprint mFingerprint;
    private String hexData;
    private String nombreUsuario;
    private HuellaDBHelper mDBHelper;
    ProgressDialog progressDialog;


    public HuellaAcqTask(Context context, Fingerprint fingerprint, String nombre) {

        mContext = context;
        mFingerprint = fingerprint;
        nombreUsuario = nombre;
        mDBHelper = new HuellaDBHelper(mContext);
        progressDialog = new ProgressDialog(mContext);
    }

    @Override
    protected String doInBackground(Integer... params) {

        boolean exeSucc = false;

        // Consigue la imagen de la huella
        if (!mFingerprint.getImage()) {
            return null;
        }

        // Genera char? con BufferEnum.B1
        if (mFingerprint.genChar(Fingerprint.BufferEnum.B1)) {
            exeSucc = true;
        }

        // Vuelve a conseguir la imagen
        if (!mFingerprint.getImage()) {
            return null;
        }

        // Genera char? ahora con BufferEnum.B2
        if (mFingerprint.genChar(Fingerprint.BufferEnum.B2)) {
            exeSucc = true;
        }

        // 合并两个缓冲区到B1
        if (mFingerprint.regModel()) {
            exeSucc = true;
        }

        if (exeSucc) {
            hexData = mFingerprint.upChar(Fingerprint.BufferEnum.B1);
            return "ok";
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //TODO: Aqui se guarda a la base de datos.
//        Guardar los datos aqui
        progressDialog.cancel();

        if (TextUtils.isEmpty(result)) {
            //Fallo la adquisicion de datos
            return;
        }
        //Si el if anterior no atrapa nada, entonces la adquisicion fue exitosa.
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HuellaContract.HuellaEntry.COLUMNA_NOMBRE, nombreUsuario);
        values.put(HuellaContract.HuellaEntry.COLUMNA_HUELLA, hexData);
        long rowID = db.insert(HuellaContract.HuellaEntry.TABLA_USUARIO_NOMBRE,null,values);
        Log.i(TAG, nombreUsuario);
        Log.i(TAG, hexData);
        Log.i(TAG, Long.toString(rowID) +" agregado.");
        mDBHelper.close();
        db.close();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    public String getData() {
        return hexData;
    }

}
