package com.fime.fsw.huella.huella.barcode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.fingerprint.BuscarHuellaActivity;
import com.rscja.deviceapi.Barcode1D;
import com.rscja.deviceapi.exception.ConfigurationException;

public class BarcodeReaderActivity extends AppCompatActivity {

    private final static String TAG = BarcodeReaderActivity.class.getSimpleName();

    private boolean estaActivo = false;

    private TextView tvCodigo;
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

        mContext = BarcodeReaderActivity.this;

        initComponentes();

        //Inicia el escanner para leer codigos de barra
        btnEscanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Si el codigo de barras esta activo entonces puede escanear
                if (estaActivo) {
                    new ScanTask().execute();
                }
            }
        });
    }

    private void initComponentes() {
        tvCodigo = (TextView) findViewById(R.id.data_textview);
        btnEscanear = (Button) findViewById(R.id.escanear_button);
        progressDialog = new ProgressDialog(mContext);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Inicia el codigo de barras cuando la actividad se abre
        new BarcodeInitTask().execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Si se sale de la actividad y el codigo de barras esta activo,
        //se cierra.
        if (mBarcode != null) {
            mBarcode.close();
        }
    }

    //Inicia el codigo de barras
    public class BarcodeInitTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            return mBarcode.open();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            progressDialog.cancel();
            estaActivo = result;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Iniciando scanner");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    public class ScanTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String code = "";

            if (estaActivo) {
                code = mBarcode.scan();
            }

            return code;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Aqui se puede usar el codigo que regrese el escanner.
            tvCodigo.setText(result);
            if (!TextUtils.isEmpty(result)) {
                //Se capturo algo de informacion entonces se inicia erl recog de la huella
                Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mContext, BuscarHuellaActivity.class));
            } else {
                Toast.makeText(mContext, "No se capturo el codigo", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
