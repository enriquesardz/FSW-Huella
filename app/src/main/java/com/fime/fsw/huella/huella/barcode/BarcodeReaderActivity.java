package com.fime.fsw.huella.huella.barcode;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fime.fsw.huella.huella.R;
import com.rscja.deviceapi.Barcode1D;
import com.rscja.deviceapi.exception.ConfigurationException;

public class BarcodeReaderActivity extends AppCompatActivity {

    private final static String TAG = BarcodeReaderActivity.class.getSimpleName();

    private boolean isOpened = false;

    private TextView txtCodigo;
    private Button btnEscanear;
    private ProgressDialog progressDialog;

    private Context mContext;

    private Barcode1D mBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_reader);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        try {
            mBarcode = Barcode1D.getInstance();
        } catch (ConfigurationException e) {
            Log.e(TAG, e.toString());
            return;
        }

        mContext = getApplicationContext();

        txtCodigo = (TextView)findViewById(R.id.data_textview);
        btnEscanear = (Button) findViewById(R.id.escanear_button);
        progressDialog = new ProgressDialog(mContext);

        //Empieza a escanear
        btnEscanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpened) {
                    new ScanTask().execute();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new BarcodeInitTask().execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBarcode != null) {
            mBarcode.close();
        }
    }

    public class BarcodeInitTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            return mBarcode.open();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
//            progressDialog.cancel();
            isOpened = result;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setMessage("Iniciando scanner");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();

        }
    }

    public class ScanTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String code = "";

            if (isOpened) {
                code = mBarcode.scan();
            }

            return code;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            txtCodigo.setText(result);

        }

    }
}
