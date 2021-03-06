package com.fime.fsw.huella.huella.Activities.screens.huella;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.rscja.deviceapi.Fingerprint;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

/**
 * Created by ensardz on 20/06/2017.
 */

public class HuellaIdentTask extends AsyncTask<Integer, Integer, String> {

    public static final String TAG = APP_TAG + HuellaIdentTask.class.getSimpleName();

    private ProgressDialog progressDialog;

    private Context mContext;
    private Fingerprint mFingerprint;
    private AsyncTaskResponseListener callback;

    private String usuarioHexData;

    public interface AsyncTaskResponseListener {
        public void onSuccess();
        public void onFailure();
    }

    public HuellaIdentTask(Context context, Fingerprint fingerprint, String fingerPrintData, AsyncTaskResponseListener callback) {
        mContext = context;
        mFingerprint = fingerprint;
        this.callback = callback;

        usuarioHexData = fingerPrintData;

        progressDialog = new ProgressDialog(mContext);

    }

    @Override
    protected String doInBackground(Integer... params) {

        boolean exeSucc = false;

        //Obtiene la imagen del dedo del lector
        if (!mFingerprint.getImage()) {
            return null;
        }
        //De la imagen anterior se crea un valor y se guarda en el Buffer 1
        if (mFingerprint.genChar(Fingerprint.BufferEnum.B1)) {
            exeSucc = true;
        }
        //Se pasa a una funcion el valor hexadecimal que se saca de la base de datos utilizando el ID proporcionado
        //para que este lo cargue al Buffer 2
        if (mFingerprint.downChar(Fingerprint.BufferEnum.B2, usuarioHexData)) {
            exeSucc = true;
        }

        //Si lo anterior es exitoso, tratamos de hacer match.
        if (exeSucc) {
            int result = 0;
            int exeCount = 0;

            do {
                //La funcion match trata de hacer match con las huellas en el Buffer 1 y Buffer 2
                //Si hay match, regresa el score del match, si  noy hay match, regresa -1 porque fallo
                exeCount++;
                result = mFingerprint.match();

            } while (result == 0 && exeCount < 3);

            Log.i(TAG, "exeCount=" + exeCount);
            Log.i(TAG, "matchValue= " + result);

            //Si hubo match, entonces regresa "ok"
            if (result != -1) {
                String data = mFingerprint.upChar(Fingerprint.BufferEnum.B1);
                Log.i(TAG, "Hubo match");
                Log.i(TAG, data);
                return "ok";
            } else {
                Log.e(TAG, "No hubo match");
            }


            Log.e(TAG, "search result Empty");
        }


        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        progressDialog.cancel();

        if (TextUtils.isEmpty(result)) {
            //Fallo la identificacion
            callback.onFailure();
            return;
        }

        //Si hay resultado, entonces fue una Identificacion exitosa
        callback.onSuccess();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Antes de poder hacer el match con la huella, necesitamos sacar
        //el valor hexadecimal de la huella del maestro utilizando algun identificador
        //como su ID.

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

}
