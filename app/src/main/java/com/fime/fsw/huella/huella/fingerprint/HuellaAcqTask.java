package com.fime.fsw.huella.huella.fingerprint;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.rscja.deviceapi.Fingerprint;

import org.w3c.dom.Text;

/**
 * Created by ensardz on 20/06/2017.
 */

public class HuellaAcqTask extends AsyncTask<Integer, Integer, String> {

    public static String datab2;

    private ProgressDialog progressDialog;
    private Context mContext;

    private Fingerprint mFingerprint;

    private int pid;
    private String uname;
    private String data;
    //    private boolean isShowImg;

    private TextView showDataTV;

    public HuellaAcqTask(int pageId, String name, Context context, Fingerprint fingerprint, TextView showData, ProgressDialog progressDialog) {

        //TODO: El mFingerprint hace referencia al objeto Fingerprint que se tiene corriendo en la Actividad que llama a esta clase
        //TODO: Por ello, se requiere el Contexto de esa Actividad.

        mContext = context;
        mFingerprint = fingerprint;
        pid = pageId;
        uname = name;
        showDataTV = showData;
        this.progressDialog = progressDialog;
//        isShowImg = showImg; Por si se quiere mostrar la imagen se pasa un booleano
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
            if (mFingerprint.storChar(Fingerprint.BufferEnum.B1, pid)) {

                //Checa si la opcion ISO esta activada, podemos saltarnos esto
                //Por lo pronto se quita ese check

                //Save as ISO
                //data = mContext.mFingerprint.upChar(Fingerprint.BufferEnum.B11);

                //Save as default
                data = mFingerprint.upChar(Fingerprint.BufferEnum.B1);
                datab2 = data;

                //Aqui se puede meter codigo para traer la imagen de la huella
                //para mostrarlo en un ImageView
            }
            return "ok";
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

//        progressDialog.cancel();

        if (TextUtils.isEmpty(result)) {

            //Fallo la adquisicion de datos
            return;
        }

        //Si el if anterior no atrapa nada, entonces la adquisicion fue exitosa.
        showDataTV.setText(data);
        Log.e("HUELLA", data);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    public String getData(){
        return data;
    }

}
