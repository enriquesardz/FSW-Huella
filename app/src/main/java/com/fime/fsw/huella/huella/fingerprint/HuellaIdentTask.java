package com.fime.fsw.huella.huella.fingerprint;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.rscja.deviceapi.Fingerprint;

import static android.content.ContentValues.TAG;

/**
 * Created by ensardz on 20/06/2017.
 */

public class HuellaIdentTask extends AsyncTask<Integer, Integer, String> {

    private ProgressDialog progressDialog;
    private Context mContext;

    private Fingerprint mFingerprint;

    private int searchPageID = -1;
    private int searchScore = -1;
    private String searchName = "";

    public String data;

    public HuellaIdentTask(Context context, Fingerprint fingerprint, ProgressDialog progressDialog) {
        mContext = context;
        mFingerprint = fingerprint;
        this.progressDialog = progressDialog;
    }

    @Override
    protected String doInBackground(Integer... params) {

        if (!mFingerprint.getImage()) {
            return null;
        }

        if (mFingerprint.genChar(Fingerprint.BufferEnum.B1)) {
            int[] result = null;
            int exeCount = 0;

            do {
                exeCount++;
                result = mFingerprint
                        .search(Fingerprint.BufferEnum.B1, 0, 255);

            } while (result == null && exeCount < 3);

            Log.i(TAG, "exeCount=" + exeCount);

            if (result != null) {
                searchPageID = result[0];
                searchScore = result[1];
                data = mFingerprint.upChar(Fingerprint.BufferEnum.B1);
                Log.e("HUELLA",data);

                mFingerprint.downChar(Fingerprint.BufferEnum.B2, HuellaAcqTask.datab2);

                if (mFingerprint.match()!=-1){
                    Log.i("HUELLA", "match b1 b2");
                } else
                {
                    Log.i("HUELLA", "nomatfch");
                }

                return "ok";
            }



            Log.i(TAG, "search result Empty");
        }



        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

//        progressDialog.cancel();

        if (TextUtils.isEmpty(result)) {
            //Fallo la identificacion
            return;
        }

       //Identificacion exitosa

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

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
