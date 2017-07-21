package com.fime.fsw.huella.huella.Activities.Barcode;

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

import com.fime.fsw.huella.huella.Activities.Fingerprint.IdentificarHuellaActivity;
import com.fime.fsw.huella.huella.Data.Modelos.Task;
import com.fime.fsw.huella.huella.Data.Provider.RealmProvider;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;
import com.rscja.deviceapi.Barcode1D;
import com.rscja.deviceapi.exception.ConfigurationException;

import io.realm.Realm;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

public class BarcodeReaderActivity extends AppCompatActivity {

    private final static String TAG = APP_TAG + BarcodeReaderActivity.class.getSimpleName();

    private boolean estaActivo = false;
    private boolean scannerConnected = true;

    private Context mContext;
    private Realm mRealm;
    private Barcode1D mBarcode;
    private SesionAplicacion mSesion;

    private TextView tvCodigo;
    private Button btnEscanear;
    private Button btnNoSalon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_reader);

        mContext = BarcodeReaderActivity.this;
        mRealm = Realm.getDefaultInstance();
        mSesion = new SesionAplicacion(mContext);

        //Trata de obtener una instancia del codigo de barras, si no lo logra entonces arroja una excepcion
        try {
            mBarcode = Barcode1D.getInstance();
        } catch (ConfigurationException e) {
            Log.e(TAG, e.toString());
            scannerConnected = false;
        }

        initComponentes();

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
        if(scannerConnected){
            new BarcodeInitTask().execute();
        }
        else{
            Log.d(TAG, "Se inicio la actividad sin escanner");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Si se sale de la actividad y el codigo de barras esta activo,
        //se cierra.
        if (mBarcode != null) mBarcode.close();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    private void initComponentes() {

        tvCodigo = (TextView) findViewById(R.id.data_textview);
        btnEscanear = (Button) findViewById(R.id.escanear_button);
        btnNoSalon = (Button) findViewById(R.id.no_salon_button);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Trae los datos que le paso el DatosVisitaFragment
        String id = getIntent().getStringExtra(Task._ID_KEY);

        final Task task = RealmProvider.getTaskById(mRealm, id);

        final String roomBarcode = task.getRoom().getBarcode();
        final String task_id = task.get_id();

        //Posiblemente se deba mostrar mas informacion, por ahora solo el codigo de barras.
        tvCodigo.setText(task.getRoom().getBarcode());

        //Inicia el escanner para leer codigos de barra
        btnEscanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Si el codigo de barras esta activo entonces puede escanear
                if (estaActivo) {
                    new ScanTask(task).execute();
                }

                if (!scannerConnected){
                    //TODO: No debe de ir en la version final.
                    //Si el escanner no esta conectado entonces es la debug App
                    //Solamente cambia los valores
                    RealmProvider.setVisitAtCheckout(mRealm, task);
                    startHuellaActivity(task_id, roomBarcode);
                }
            }
        });

        btnNoSalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmProvider.setCheckoutTaskValuesNoBarcode(mRealm,task);
                mSesion.setCurrentTaskPosition(mSesion.getCurrentTaskPosition() + 1);
                finish();
            }
        });
    }



    public void startHuellaActivity(String taskId, String barcodeSalon){
        //Inicia el reconocimiento de huella porque se encontro el salon
        Intent intent = new Intent(mContext, IdentificarHuellaActivity.class);

        //Le pasa el id a la nueva actividad de deteccion de huella.
        intent.putExtra(Task._ID_KEY, taskId);

        Log.i(TAG, "Se encontro el codigo de barras");
        Log.d(TAG, "Barcode: " + barcodeSalon + " TaskId: " + taskId);

        startActivity(intent);
        finish();
    }

    /*
    * TASKS ASINCRONAS PARA EL CODIGO DE BARRAS
    * */
    //Inicia el codigo de barras
    public class BarcodeInitTask extends AsyncTask<String, Integer, Boolean> {

        ProgressDialog progressDialog;

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
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Iniciando scanner");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    //Lee el codigo de barras y lo compara
    public class ScanTask extends AsyncTask<String, Integer, String> {

        ProgressDialog progressDialog;
        private String barcodeSalon;
        private String taskId;
        private Task task;

        public ScanTask (Task task){
            this.task = task;
            taskId = task.get_id();
            barcodeSalon = task.getRoom().getBarcode();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Escaneando");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

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

            progressDialog.cancel();

            //Aqui se puede usar el codigo que regrese el escanner.
            if (!TextUtils.isEmpty(result)) {
                if (TextUtils.equals(barcodeSalon, result)) {
                    //Si el codigo de barras no es nulo, y el valor coincide con el codigo de barras
                    //del Task entonces se ejecuta esta parte.

                    //Se agrega la hora a la que visito el salon
                    RealmProvider.setVisitAtCheckout(mRealm, task);
                    //Se inicia la actuvidad de la Huella
                    startHuellaActivity(taskId, barcodeSalon);
                }
                else{
                    Log.e(TAG, "No coinciden los codigos: " + barcodeSalon + " != " + result);
                }
            } else {
                Log.e(TAG, "No se detecto un codigo");
            }

        }



    }
}
