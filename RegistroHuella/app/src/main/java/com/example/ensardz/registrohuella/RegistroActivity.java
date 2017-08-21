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
import android.widget.Toast;

import com.rscja.deviceapi.Fingerprint;


public class RegistroActivity extends AppCompatActivity {

    public static final String TAG = RegistroActivity.class.getSimpleName();

    public Fingerprint mFingerprint;
    public Context mContext;

    private EditText edNumero;
    private EditText edNombre;
    private Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        try {
            mFingerprint = Fingerprint.getInstance();
        } catch (Exception e) {
            Toast.makeText(mContext, "No se inicio huella", Toast.LENGTH_SHORT).show();
        }

        mContext = RegistroActivity.this;
        edNumero = (EditText) findViewById(R.id.numero_empleado);
        edNombre = (EditText) findViewById(R.id.huella);
        btnAgregar = (Button) findViewById(R.id.agregar);


        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = edNombre.getText().toString().trim();
                String empleadoId = edNumero.getText().toString().trim();
                long empId;

                if (TextUtils.isEmpty(nombre) && TextUtils.isEmpty(empleadoId)) {
                    Toast.makeText(mContext, "Los campos no pueden ir vacios.", Toast.LENGTH_SHORT).show();

                    return;
                }

                empId = Long.parseLong(empleadoId);

                if (!(empId >= 1000 && empId <= 1030)) {
                    Toast.makeText(mContext, "Rangos entre 1000 y 1030 solamente", Toast.LENGTH_SHORT).show();
                    return;
                }

                new HuellaAcqTask(mContext, mFingerprint, nombre, empleadoId).execute();
                edNombre.setText("");
                edNumero.setText("");
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


        public HuellaAcqTask(Context context, Fingerprint fingerprint, String nombre, String empleado) {

            mContext = context;
            mFingerprint = fingerprint;
            usuarioNombre = nombre;
            numEmpleado = empleado;
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
