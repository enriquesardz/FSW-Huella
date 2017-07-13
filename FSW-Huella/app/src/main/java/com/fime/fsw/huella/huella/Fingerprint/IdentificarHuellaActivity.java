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

import com.fime.fsw.huella.huella.Data.Modelos.Task;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;
import com.rscja.deviceapi.Fingerprint;

import io.realm.Realm;

public class IdentificarHuellaActivity extends AppCompatActivity {


    public Fingerprint mFingerprint;
    public Context mContext;
    private Realm mRealm;
    private SesionAplicacion mSesion;

    private TextView tvNombre;
    private TextView tvFullNombre;
    private Button btnBuscarHuella;
    private Button btnNoEstaMaestro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_huella);

        mContext = IdentificarHuellaActivity.this;
        mRealm = Realm.getDefaultInstance();
        mSesion = new SesionAplicacion(mContext);

        try {
            mFingerprint = Fingerprint.getInstance();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        initComponentes();

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

    private void initComponentes() {

        tvNombre = (TextView) findViewById(R.id.nombre_textview);
        tvFullNombre = (TextView)findViewById(R.id.full_nombre_textview);
        btnBuscarHuella = (Button) findViewById(R.id.buscar_huella_button);
        btnNoEstaMaestro = (Button) findViewById(R.id.no_esta_maestro_button);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Buscar Huella");
        }

        long itemid = getIntent().getLongExtra(Task._ID_KEY, -1);

        final Task task = getTaskConId(itemid);

        cargarDatosTask(task);

        //Inicia el task para buscar la huella con el id que se le pasa,
        //ademas, toma la huella que se encuentre en el escanner para comparar.
        //TODO: El id se debe de sacar de los datos que nos pasan los web service

        btnBuscarHuella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Este task utiliza las funciones proporcionadas por el SDK para identificar la huella
                new HuellaIdentTask(mContext, mFingerprint, mRealm, mSesion, task).execute();
            }
        });


        btnNoEstaMaestro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        task.setTaskState(Task.STATE_PASO_NO_VINO_MAESTRO);
                    }
                });
                mSesion.setCurrentItemLista(mSesion.getCurrentItemLista() + 1);
                finish();
            }
        });
    }

    public Task getTaskConId(long id){
        return mRealm.where(Task.class).equalTo(Task._ID_KEY, id).findFirst();
    }

    public void cargarDatosTask(Task task){
        tvNombre.setText(getResources().getString(R.string.ih_nombre, task.getOwner().getEmployeeName()));
        tvFullNombre.setText(getResources().getString(R.string.ih_apellido, task.getOwner().getEmployeeFullName()));
    }


    /*
    *
    * ASYNC TASK PARA INICIAR EL LEECTOR DACTILAR
    *
    * */

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
                Toast.makeText(IdentificarHuellaActivity.this, "init fail",
                        Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            mypDialog = new ProgressDialog(IdentificarHuellaActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("Iniciando escanner");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }

    }
}
