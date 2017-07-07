package com.fime.fsw.huella.huella.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fime.fsw.huella.huella.API.APICodo;
import com.fime.fsw.huella.huella.Activities.InicioSesion.MenuInicioSesionActivity;
import com.fime.fsw.huella.huella.Activities.RecorridoMain.RecorridoMainActivity;
import com.fime.fsw.huella.huella.Data.Modelos.Task;
import com.fime.fsw.huella.huella.API.ServiciosAPI.DescargaRecorridoService;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescargaRutaActivity extends AppCompatActivity {

    public static final String TAG = HuellaApplication.APP_TAG + DescargaRutaActivity.class.getSimpleName();

    // https://young-escarpment-48238.herokuapp.com/routes
    private MaterialSpinner spinnerClaveArea;
    private MaterialSpinner spinnerPeriodo;
    private Button btnDescargar;

    private Context mContext;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descarga_ruta);

        mContext = DescargaRutaActivity.this;
        mRealm = Realm.getDefaultInstance();

        initComponentes();

        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //El usuario esta logeado; aqui se descarga y ahora la aplicacion continuara a abrir el RecorridoMainActivity.
                descargarDeWebService();
            }
        });
    }

    private void descargarDeWebService() {

        DescargaRecorridoService servicio = APICodo.getApi().create(DescargaRecorridoService.class);
        Call<List<Task>> call = servicio.descargaRecorrido();

        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                final List<Task> tasks = response.body();
                Log.i(TAG, tasks.toString());

                //Se ejecuta si el webservice regresa algo

                //Se guardan los datos descargados a Realm
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        int i;
                        for(i = 0; i < tasks.size(); i++){
                            Task task = tasks.get(i);
                            Task realmTask = realm.copyToRealmOrUpdate(task);
                            Log.i(TAG, realmTask.toString());
                        }
                    }
                });

                progressDialog.cancel();

                SesionAplicacion sesionAplicacion = new SesionAplicacion(mContext);
                sesionAplicacion.crearSesionDescarga();
                startActivity(new Intent(mContext, RecorridoMainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                //Si el web service no regresa nada entonces cae aqui
                progressDialog.cancel();
                Toast.makeText(DescargaRutaActivity.this, "No se descargo", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initComponentes() {
        spinnerClaveArea = (MaterialSpinner) findViewById(R.id.clave_area_spinner);
        spinnerPeriodo = (MaterialSpinner) findViewById(R.id.periodo_spinner);
        btnDescargar = (Button) findViewById(R.id.descargar_button);

        List<String> claveAreaData = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.druta_claves_area_spinner)));
        List<String> periodoData = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.druta_periodo_spinner)));

        spinnerClaveArea.setItems(claveAreaData);
        spinnerPeriodo.setItems(periodoData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cerrar_sesion:
                SesionAplicacion sesionAplicacion = new SesionAplicacion(mContext);
                sesionAplicacion.terminarSesionAplicacion();
                startActivity(new Intent(mContext, MenuInicioSesionActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

}
