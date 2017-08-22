package com.example.ensardz.registrohuella;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ensardz.registrohuella.Datos.Professor;
import com.example.ensardz.registrohuella.Datos.RealmProvider;
import com.rscja.deviceapi.Fingerprint;

import io.realm.Realm;


public class RegistroActivity extends AppCompatActivity {

    public static final String TAG = RegistroActivity.class.getSimpleName();

    public Fingerprint mFingerprint;
    public Context mContext;
    public Realm mRealm;
    public Professor mProfessor;

    private TextView tvHuella;
    private Button btnCapturar;
    private Button btnAgregar;

    private String huellaData;
    private boolean scannerConnected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        String professorRawName = getIntent().getStringExtra(Professor.RAW_NAME_KEY);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(professorRawName);
        }

        try {
            mFingerprint = Fingerprint.getInstance();
        } catch (Exception e) {
            scannerConnected = false;
        }

        mContext = RegistroActivity.this;
        mRealm = Realm.getDefaultInstance();

        btnAgregar = (Button) findViewById(R.id.agregar_button);
        btnCapturar = (Button) findViewById(R.id.capturar_button);

        mProfessor = RealmProvider.getProfessorByRawName(mRealm, professorRawName);

        huellaData = null;

        btnCapturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scannerConnected) {
                    new HuellaAcqTask(mContext, mFingerprint).execute();
                } else {
                    Toast.makeText(mContext, "No hay escanner", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(huellaData)) {
                    Toast.makeText(mContext, "No hay escanner " + mProfessor.getRawName(), Toast.LENGTH_SHORT).show();
                    return;
                }

                //Se agrega a la base de datos
                Toast.makeText(mContext, "Se agrego la huella.", Toast.LENGTH_SHORT).show();
            }
        });

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
        if (scannerConnected) {
            new InitTask().execute();
        } else {
            Toast.makeText(mContext, "No hay escanner", Toast.LENGTH_SHORT).show();
        }
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
                Toast.makeText(mContext, "init fail",
                        Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            mypDialog = new ProgressDialog(mContext);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("Iniciando escanner");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }

    }

    public class HuellaAcqTask extends AsyncTask<Integer, Integer, String> {

        private final String TAG = HuellaAcqTask.class.getSimpleName();

        private Context mContext;
        private Fingerprint mFingerprint;

        private String usuarioHexData;
        private String numEmpleado;
        private String usuarioNombre;

        private ProgressDialog progressDialog;


        public HuellaAcqTask(Context context, Fingerprint fingerprint) {

            mContext = context;
            mFingerprint = fingerprint;
            progressDialog = new ProgressDialog(mContext);
        }

        @Override
        protected String doInBackground(Integer... params) {

            boolean exeSucc = false;

            if (isCancelled()) {
                return null;
            }

            // Consigue la imagen de la huella
            if (!mFingerprint.getImage()) {
                return null;
            }

            // Genera un valor y lo guarda en el Buffer 1
            if (mFingerprint.genChar(Fingerprint.BufferEnum.B1)) {
                exeSucc = true;
            }

            // Vuelve a conseguir la imagen
            if (!mFingerprint.getImage()) {
                return null;
            }

            // Ahora lo guarda la segunda imagen en el Buffer 2
            if (mFingerprint.genChar(Fingerprint.BufferEnum.B2)) {
                exeSucc = true;
            }

            // Combina el Buffer 1 y Buffer 2 y el resultado lo guarda en el Buffer 1
            if (mFingerprint.regModel()) {
                exeSucc = true;
            }

            //Si hasta ahora la ejecucion ha sido exitosa entonces,
            //se genera un valor hexadecimal con la funcion upChar y el Buffer donde se tiene la huella
            //y se guarda en una variable usuarioHexData.
            if (exeSucc) {
                usuarioHexData = mFingerprint.upChar(Fingerprint.BufferEnum.B1);
                return "ok";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //TODO: Actualizar para que se guarden con las tablas que se van a usar en el producto final
            progressDialog.cancel();

            if (TextUtils.isEmpty(result)) {
                //Fallo la adquisicion de datos
                return;
            }

            //Adquisicion exitosa
            huellaData = result;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        public String getData() {
            return usuarioHexData;
        }

    }


}
