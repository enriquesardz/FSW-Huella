package com.fime.fsw.huella.huella.Fingerprint;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fime.fsw.huella.huella.Data.API.Modelos.Task;
import com.fime.fsw.huella.huella.R;
import com.rscja.deviceapi.Fingerprint;

import io.realm.Realm;

public class BuscarHuellaActivity extends AppCompatActivity {


    public Fingerprint mFingerprint;
    public Context mContext;
    private Realm mRealm;

    private TextView tvNombre;
    private TextView tvFullNombre;
    private Button btnBuscarHuella;

    private String hexCode;
    private long itemid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_huella);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Buscar Huella");
        }

        try {
            mFingerprint = Fingerprint.getInstance();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        itemid = getIntent().getLongExtra("_id", -1);

        mContext = BuscarHuellaActivity.this;
        mRealm = Realm.getDefaultInstance();

        Task task = mRealm.where(Task.class).equalTo("_id", itemid).findFirst();
        hexCode = task.getHexCode();
        initComponentes();

        tvNombre.setText("Nombre: " + task.getName());
        tvFullNombre.setText("Apellido: " + task.getFullName());


        //Inicia el task para buscar la huella con el id que se le pasa,
        //ademas, toma la huella que se encuentre en el escanner para comparar.
        //TODO: El id se debe de sacar de los datos que nos pasan los web service

        btnBuscarHuella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Este task utiliza las funciones proporcionadas por el SDK para identificar la huella
                new HuellaIdentTask(mContext, mFingerprint, hexCode, itemid).execute();
            }
        });

    }

    private void initComponentes() {
        tvNombre = (TextView) findViewById(R.id.nombre_textview);
        tvFullNombre = (TextView)findViewById(R.id.full_nombre_textview);
        btnBuscarHuella = (Button) findViewById(R.id.buscar_huella_button);
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
        //Libera la huella si esta activa cuando sale de la actividad.
        if (mFingerprint != null) {
            mFingerprint.free();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Inicia la huella cuando se inicia la actividad.
        new InitTask().execute();
    }

    //Este task inicia se encarga de iniciar el lector de la huella.
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
