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
import io.realm.RealmResults;
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
    private SesionAplicacion mSesionApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descarga_ruta);

        mContext = DescargaRutaActivity.this;
        mRealm = Realm.getDefaultInstance();
        mSesionApp = new SesionAplicacion(mContext);

        initComponentes();

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
                mSesionApp.terminarSesionAplicacion();
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

    private void initComponentes() {
        spinnerClaveArea = (MaterialSpinner) findViewById(R.id.clave_area_spinner);
        spinnerPeriodo = (MaterialSpinner) findViewById(R.id.periodo_spinner);
        btnDescargar = (Button) findViewById(R.id.descargar_button);

        List<String> claveAreaData = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.druta_claves_area_spinner)));
        List<String> periodoData = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.druta_periodo_spinner)));

        spinnerClaveArea.setItems(claveAreaData);
        spinnerPeriodo.setItems(periodoData);

        //TODO: No se deben dropear los valores si no hacerles update,
        //por ahora se borran cuando regresa a esta pantalla.

        eliminarTasksDeRealm();

        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descargarDeWebService();
            }
        });
    }

    private void descargarDeWebService() {

        //El usuario esta logeado; aqui se descarga y ahora la aplicacion continuara a
        //abrir el RecorridoMainActivity si la descarga es exitosa.

        //Si la descarga regresa error, se queda en la pagina de descarga.

        DescargaRecorridoService servicio = APICodo.getApi().create(DescargaRecorridoService.class);
        Call<List<Task>> call = servicio.descargaRecorrido();

        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {

                //Se ejecuta si el webservice regresa algo, la respuesta
                //es una lista de Tasks, entonces la respuesta se guarda en una Lista de tipo Tasks

                final List<Task> tasks = response.body();
                Log.i(TAG, tasks.toString());

                //Se guardan los datos a nuestro Realm
                guardarRespuestaARealm(tasks);
                //Despues de guardar al Realm, se setea el primer item de la lista.
                setInitialTask();

                //Se inicia sesion de descarga
                mSesionApp.crearSesionDescarga();

                progressDialog.cancel();

                startActivity(new Intent(mContext, RecorridoMainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                //Si el web service no regresa nada entonces cae aqui
                progressDialog.cancel();
                Toast.makeText(DescargaRutaActivity.this, getResources().getString(R.string.druta_error_descarga), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void guardarRespuestaARealm(final List<Task> tasks){

        //Se recorre la lista y se guarda cada objeto Task a Realm
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
    }

    public void eliminarTasksDeRealm(){
        final RealmResults<Task> tasks = mRealm.where(Task.class).findAll();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                tasks.deleteAllFromRealm();
            }
        });
    }

    public void setInitialTask(){
        mSesionApp.setCurrentItemLista(mRealm.where(Task.class).findFirst().get_id());
    }
}
