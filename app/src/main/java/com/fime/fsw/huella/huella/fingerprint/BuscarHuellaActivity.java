package com.fime.fsw.huella.huella.fingerprint;

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
import android.widget.TextView;
import android.widget.Toast;

import com.fime.fsw.huella.huella.MenuInicioSesionActivity;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.RecorridoMainActivity;
import com.rscja.deviceapi.Fingerprint;

import org.w3c.dom.Text;

public class BuscarHuellaActivity extends AppCompatActivity {

    public Fingerprint mFingerprint;
    public Context mContext;

    private EditText idUsuarioET;
    private TextView nombreUsuarioTV;
    private Button buscarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_huella);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Buscar Huella");

        try {
            mFingerprint = Fingerprint.getInstance();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        mContext = BuscarHuellaActivity.this;

        nombreUsuarioTV = (TextView) findViewById(R.id.nombre_textview);
        idUsuarioET = (EditText) findViewById(R.id.id_usuario_edittext);
        buscarButton = (Button) findViewById(R.id.buscar_huella_button);

        //Adquisicion de huella
        buscarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuarioID = idUsuarioET.getText().toString();
                if (TextUtils.isEmpty(usuarioID)) {
                    Toast.makeText(mContext, "El id no puede ir vacio", Toast.LENGTH_SHORT).show();
                    return;
                }
                new HuellaIdentTask(mContext, mFingerprint, usuarioID,nombreUsuarioTV).execute();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
                Toast.makeText(BuscarHuellaActivity.this, "init fail",
                        Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            mypDialog = new ProgressDialog(BuscarHuellaActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("Iniciando escanner");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }

    }
}
