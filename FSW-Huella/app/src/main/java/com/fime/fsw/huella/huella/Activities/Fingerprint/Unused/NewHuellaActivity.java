package com.fime.fsw.huella.huella.Activities.Fingerprint.Unused;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fime.fsw.huella.huella.Activities.InicioSesion.MenuInicioSesionActivity;
import com.fime.fsw.huella.huella.R;
import com.rscja.deviceapi.Fingerprint;

public class NewHuellaActivity extends AppCompatActivity {

    public Fingerprint mFingerprint;
    public Context mContext;

    private EditText etNombreUsuario;
    private Button btnAdquirirHuella;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recog_huella);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Adquisicion y leectura de Huella");
        }

        try {
            mFingerprint = Fingerprint.getInstance();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        mContext = NewHuellaActivity.this;
        initComponentes();

        //Adquisicion de huella
        //TODO: Esta almacenando en una DB temporal, cambiar por DB para producto final.
        btnAdquirirHuella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreUsuario = etNombreUsuario.getText().toString().trim();

                if (TextUtils.isEmpty(nombreUsuario)) {

                    Toast.makeText(mContext, "Nombre no puede ser nulo",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                new HuellaAcqTask(mContext, mFingerprint, nombreUsuario).execute();
            }
        });

    }

    private void initComponentes(){
        etNombreUsuario = (EditText) findViewById(R.id.page_id_edittext);
        btnAdquirirHuella = (Button) findViewById(R.id.adquirir_button);
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
        //Libera la huella al salir de la actividad
        super.onPause();
        if (mFingerprint != null) {
            mFingerprint.free();
        }
    }

    @Override
    protected void onResume() {
        //Inicia la huella al iniciar la actividad.
        super.onResume();
        new InitTask().execute();
    }

    public class InitTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;
        //Inicia la huella
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
                Toast.makeText(NewHuellaActivity.this, "init fail",
                        Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            mypDialog = new ProgressDialog(NewHuellaActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("init...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }

    }
}
