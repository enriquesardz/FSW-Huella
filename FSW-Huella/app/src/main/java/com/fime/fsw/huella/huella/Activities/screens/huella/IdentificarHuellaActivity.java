package com.fime.fsw.huella.huella.Activities.screens.huella;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fime.fsw.huella.huella.Activities.screens.codigoBarras.BarcodeReaderActivity;
import com.fime.fsw.huella.huella.Activities.screens.tasksmain.RecorridoMainActivity;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Task;
import com.fime.fsw.huella.huella.Data.Provider.RealmProvider;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;
import com.rscja.deviceapi.Fingerprint;

import io.realm.Realm;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

public class IdentificarHuellaActivity extends AppCompatActivity implements HuellaIdentTask.AsyncTaskResponseListener {

    public static final String TAG = APP_TAG + IdentificarHuellaActivity.class.getSimpleName();


    private Fingerprint mFingerprint;
    private Context mContext;
    private Realm mRealm;
    private SesionAplicacion mSesion;

    private TextView tvNombre;
    private TextView tvFullNombre;
    private Button btnBuscarHuella;
    private Button btnNoEstaMaestro;

    private boolean scannerConnected = true;
    private Task mTask;

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
            scannerConnected = false;
        }

        initComponentes();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, RecorridoMainActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(mContext, BarcodeReaderActivity.class));
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
        if (scannerConnected) {
            new InitTask().execute();
        } else {
            Log.d(TAG, "Se inicio la actividad sin escanner");
        }
    }

    private void initComponentes() {

        tvNombre = (TextView) findViewById(R.id.nombre_textview);
        tvFullNombre = (TextView) findViewById(R.id.full_nombre_textview);
        btnBuscarHuella = (Button) findViewById(R.id.buscar_huella_button);
        btnNoEstaMaestro = (Button) findViewById(R.id.no_esta_maestro_button);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Buscar Huella");
        }

        String itemid = getIntent().getStringExtra(Task._ID_FIELD);

        mTask = RealmProvider.getTaskById(mRealm, itemid);

        cargarDatosTask(mTask);

        //Inicia el task para buscar la huella con el id que se le pasa,
        //ademas, toma la huella que se encuentre en el escanner para comparar.
        final String fingerPrintData = mTask.getHuellaEmpleado();

        btnBuscarHuella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(fingerPrintData)){
                    Toast.makeText(mContext, "No hay huella asignada a este maestro.", Toast.LENGTH_SHORT).show();
                    return;
                }
                buscarHuella(fingerPrintData);
            }
        });


        btnNoEstaMaestro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noEstaMaestro();
            }
        });
    }

    public void buscarHuella(String fingerPrintData) {
        //Este task utiliza las funciones proporcionadas por el SDK para identificar la huella
        if (scannerConnected) {

            new HuellaIdentTask(mContext, mFingerprint, fingerPrintData, IdentificarHuellaActivity.this).execute();

        } else {
            //TODO: No debe de ir en la version final.
            //Debug app
            debugHuellaEncontrada(mTask);
        }
    }


    private void seEncontroHuella() {

        //Update los campos del Task y Route si la huella se encontro
        String routeId = mSesion.getCurrentRutaId();
        RealmProvider.setCheckoutsTaskValuesVinoMaestro(mRealm, mTask);
        RealmProvider.moveToNextTaskByRouteId(mRealm, routeId);

        mContext.startActivity(new Intent(mContext, RecorridoMainActivity.class));
        finish();

    }

    public void noEstaMaestro() {

        String routeId = mSesion.getCurrentRutaId();
        RealmProvider.setCheckoutsTaskValuesNoVinoMaestro(mRealm, mTask);
        RealmProvider.moveToNextTaskByRouteId(mRealm, routeId);
        startActivity(new Intent(mContext, RecorridoMainActivity.class));
        finish();

    }

    public void cargarDatosTask(Task task) {
        tvNombre.setText(getResources().getString(R.string.ih_nombre, task.getNombreEmpleado()));
        tvFullNombre.setText(getResources().getString(R.string.ih_apellido, task.getNombreEmpleado()));
    }

    public void debugHuellaEncontrada(final Task task) {
        //Si hay resultado, entonces fue una Identificacion exitosa
        String routeId = mSesion.getCurrentRutaId();
        Toast.makeText(mContext, "Se encontro usuario debug mode", Toast.LENGTH_SHORT).show();

        RealmProvider.setCheckoutsTaskValuesVinoMaestro(mRealm, task);
        RealmProvider.moveToNextTaskByRouteId(mRealm, routeId);

        startActivity(new Intent(mContext, RecorridoMainActivity.class));
        finish();
    }

    /**
     * Callbacks que regresan el resultado de la identificacion dactilar
     */
    @Override
    public void onSuccess() {

        //Si encontro la huella.
        Toast.makeText(mContext, "Se encontro usuario", Toast.LENGTH_SHORT).show();
        //Se agregan los checkouts finales, se actualiza el estado del task, y se cierra la actividad.
        seEncontroHuella();
    }

    @Override
    public void onFailure() {

        //No encontro la huella.
        Toast.makeText(mContext, "No se encontro el usuario", Toast.LENGTH_SHORT).show();
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
            super.onPreExecute();

            mypDialog = new ProgressDialog(IdentificarHuellaActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("Iniciando escanner");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }

    }
}
