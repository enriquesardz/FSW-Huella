package com.fime.fsw.huella.huella.prueba;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fime.fsw.huella.huella.MenuInicioSesionActivity;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.fingerprint.HuellaAcqTask;
import com.fime.fsw.huella.huella.fingerprint.HuellaIdentTask;
import com.rscja.deviceapi.Fingerprint;

public class RecogHuellaActivity extends AppCompatActivity {

    public Fingerprint mFingerprint;
    public Context mContext;

    private EditText pageIDTextView;

    private Button adquirirButton;
    private Button buscarButton;
    private Button checarButton;
    private TextView dataTextview;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recog_huella);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Adquisicion y leectura de Huella");

        try {
            mFingerprint = Fingerprint.getInstance();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        mContext = getApplicationContext();

        pageIDTextView = (EditText)findViewById(R.id.page_id_edittext);
        adquirirButton = (Button) findViewById(R.id.adquirir_button);
        buscarButton = (Button)findViewById(R.id.buscar_button);
        dataTextview = (TextView)findViewById(R.id.data_textview);
        checarButton = (Button)findViewById(R.id.checar_button);
        progressDialog = new ProgressDialog(mContext);

        //Adquisicion de huella
        adquirirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pageId = pageIDTextView.getText().toString().trim();

                if (TextUtils.isEmpty(pageId)) {

                    Toast.makeText(mContext, "ID no puede ser nulo",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                if (!TextUtils.isDigitsOnly(pageId)) {

                    Toast.makeText(mContext,
                            "ID debe de ser un numero",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                int iPageId = Integer.parseInt(pageId);

                if (iPageId < 0 || iPageId > 254) {

                    Toast.makeText(mContext,
                            "ID debe estar en el rango de 0~254",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                new HuellaAcqTask(iPageId, "", mContext, mFingerprint, dataTextview, progressDialog).execute();
            }
        });

        //TODO: Agregar metodo para encontrar huella con valor hexadecimal
        //TODO: hay un metodo que puede funcionar = downChar(buffer, hexStr);
        buscarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HuellaIdentTask(mContext,mFingerprint, progressDialog).execute();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, MenuInicioSesionActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(mContext, MenuInicioSesionActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mFingerprint != null) {
            mFingerprint.free();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new InitTask().execute();
    }

    public class InitTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            return mFingerprint.init();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            mypDialog.cancel();

            if (!result) {
                Toast.makeText(RecogHuellaActivity.this, "init fail",
                        Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            mypDialog = new ProgressDialog(RecogHuellaActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("init...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }

    }
}
