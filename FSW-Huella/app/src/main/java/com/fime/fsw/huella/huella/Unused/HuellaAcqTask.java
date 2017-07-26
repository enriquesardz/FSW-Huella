//package com.fime.fsw.huella.huella.Unused;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.text.TextUtils;
//
//import com.rscja.deviceapi.Fingerprint;
//
//import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;
//
///**
// * Created by ensardz on 20/06/2017.
// */
//
//public class HuellaAcqTask extends AsyncTask<Integer, Integer, String> {
//
//    private static final String TAG = APP_TAG + HuellaAcqTask.class.getSimpleName();
//
//    private Context mContext;
//    private Fingerprint mFingerprint;
//
//    private String usuarioHexData;
//    private String usuarioNombre;
//
//    private ProgressDialog progressDialog;
//
//
//    public HuellaAcqTask(Context context, Fingerprint fingerprint, String nombre) {
//
//        mContext = context;
//        mFingerprint = fingerprint;
//        usuarioNombre = nombre;
//        progressDialog = new ProgressDialog(mContext);
//    }
//
//    @Override
//    protected String doInBackground(Integer... params) {
//
//        boolean exeSucc = false;
//
//        // Consigue la imagen de la huella
//        if (!mFingerprint.getImage()) {
//            return null;
//        }
//
//        // Genera un valor y lo guarda en el Buffer 1
//        if (mFingerprint.genChar(Fingerprint.BufferEnum.B1)) {
//            exeSucc = true;
//        }
//
//        // Vuelve a conseguir la imagen
//        if (!mFingerprint.getImage()) {
//            return null;
//        }
//
//        // Ahora lo guarda la segunda imagen en el Buffer 2
//        if (mFingerprint.genChar(Fingerprint.BufferEnum.B2)) {
//            exeSucc = true;
//        }
//
//        // Combina el Buffer 1 y Buffer 2 y el resultado lo guarda en el Buffer 1
//        if (mFingerprint.regModel()) {
//            exeSucc = true;
//        }
//
//        //Si hasta ahora la ejecucion ha sido exitosa entonces,
//        //se genera un valor hexadecimal con la funcion upChar y el Buffer donde se tiene la huella
//        //y se guarda en una variable usuarioHexData.
//        if (exeSucc) {
//            usuarioHexData = mFingerprint.upChar(Fingerprint.BufferEnum.B1);
//            return "ok";
//        }
//
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(String result) {
//        super.onPostExecute(result);
//        progressDialog.cancel();
//
//        if (TextUtils.isEmpty(result)) {
//            //Fallo la adquisicion de datos
//            return;
//        }
//
//        //Si el if anterior no atrapa nada, entonces la adquisicion fue exitosa y los datos pueden ser guardados
//        //en esta funcion.
//
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
//    }
//
//    @Override
//    protected void onProgressUpdate(Integer... values) {
//        super.onProgressUpdate(values);
//    }
//
//    public String getData() {
//        return usuarioHexData;
//    }
//
//}
