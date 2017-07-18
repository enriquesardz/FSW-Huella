package com.fime.fsw.huella.huella.Activities.RecorridoMain;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fime.fsw.huella.huella.API.APICodo;
import com.fime.fsw.huella.huella.API.ServiciosAPI.DescargaRecorridoService;
import com.fime.fsw.huella.huella.Activities.DescargaRutaActivity;
import com.fime.fsw.huella.huella.Activities.HuellaApplication;
import com.fime.fsw.huella.huella.Activities.InicioSesion.MenuInicioSesionActivity;
import com.fime.fsw.huella.huella.Data.Modelos.Checkout;
import com.fime.fsw.huella.huella.Data.Modelos.Owner;
import com.fime.fsw.huella.huella.Data.Modelos.Task;
import com.fime.fsw.huella.huella.Data.Modelos.UploadCheckout;
import com.fime.fsw.huella.huella.Fragments.DatosVisitaFragment;
import com.fime.fsw.huella.huella.Fragments.RecorridoActualFragment;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecorridoMainActivity extends AppCompatActivity implements RecorridoActualFragment.OnFragmentInteractionListener, DatosVisitaFragment.OnFragmentInteractionListener {

    public static final String TAG = HuellaApplication.APP_TAG + RecorridoMainActivity.class.getSimpleName();
    public static final String KEY_ID_TASK = "_id";

    private BottomBar mBarraNav;
    private Fragment mFragment;
    private Context mContext;
    private Bundle mBundle;
    private Realm mRealm;
    private SesionAplicacion mSesionApp;

    private Button btnSubir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorrido_main);

        mContext = RecorridoMainActivity.this;
        mSesionApp = new SesionAplicacion(mContext);
        mRealm = Realm.getDefaultInstance();

        String test = getCheckoutsFromRealmToJson();

        initComponents();
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
                //TODO: Aqui se deben de regresar los checkouts al web service.
                startActivity(new Intent(mContext, MenuInicioSesionActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (mBarraNav.getCurrentTabId() == R.id.tab_datos_visita) {
            mBarraNav.selectTabWithId(R.id.tab_recorrido_actual);
        } else {
            new AlertDialog.Builder(mContext)
                    .setMessage(getResources().getString(R.string.mrecorrido_seguro_salir))
                    .setPositiveButton(getResources().getString(R.string.mrecorrido_si),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    RecorridoMainActivity.super.onBackPressed();
                                }
                            })
                    .setNegativeButton(getResources().getString(R.string.mrecorrido_no), null)
                    .show();
        }
    }

    @Override
    public void onRecorridoActualItemSelected(Task task) {
        long id = task.get_id();
        mBundle.putLong(Task._ID_KEY, id);
        mBarraNav.selectTabWithId(R.id.tab_datos_visita);
    }

    @Override
    public void onCodigoBarrasFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        publishThread.interrupt();
    }

    public void initComponents() {
        mBundle = new Bundle();

        setUpBarraNavegacion();
        eliminarTasksDeRealm();

        //TODO: Si falla a la primera, debe de haber un listener para que vuelva
        //intentar descargar
        descargarDeWebService();

    }

    public void setUpBarraNavegacion(){
        mBarraNav = (BottomBar) findViewById(R.id.barra_navegacion);
//        btnSubir = (Button) findViewById(R.id.subida_test);

        mBarraNav.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_datos_visita) {
                    //Puede iniciar vacio o porque
                    //se le dio click a un salon del recorrido actual.
                    mFragment = new DatosVisitaFragment();
                    if (mBundle != null) {
                        mFragment.setArguments(mBundle);
                    }

                } else if (tabId == R.id.tab_recorrido_actual) {
                    mFragment = new RecorridoActualFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();
            }
        });

        mBarraNav.selectTabWithId(R.id.tab_recorrido_actual);
    }

    public String getCheckoutsFromRealmToJson() {
        RealmResults<Task> tasks = mRealm.where(Task.class).findAll();
        List<UploadCheckout> uploadCheckouts = new ArrayList<UploadCheckout>();
        for (Task task : tasks){
            uploadCheckouts.add(new UploadCheckout(String.valueOf(task.get_id()),mRealm.copyFromRealm(task.getCheckout())));
        }
        Gson gson = new Gson();
        String json = gson.toJson(uploadCheckouts);
        return json;
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

                //Se guardan los datos a nuestro Realm
                guardarRespuestaARealm(tasks);
                //Despues de guardar al Realm, se setea el primer item de la lista y el final.
                setInitialAndFinalTask();

                //Se inicia sesion de descarga
                mSesionApp.crearSesionDescarga();

                progressDialog.cancel();

                //TODO: Pasar un booleano para saber si se va a cargar el empty state
                startActivity(new Intent(mContext, RecorridoMainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                //Si el web service no regresa nada entonces cae aqui
                progressDialog.cancel();
                Toast.makeText(mContext, getResources().getString(R.string.druta_error_descarga), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void guardarRespuestaARealm(final List<Task> tasks){

        //Se recorre la lista y se guarda cada objeto Task a Realm
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(Task task : tasks){
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
                for (Task task : tasks){
                    task.getOwner().deleteFromRealm();
                    task.getCheckout().deleteFromRealm();
                }
                tasks.deleteAllFromRealm();
            }
        });
    }

    public void setInitialAndFinalTask(){
        mSesionApp.setCurrentItemLista(mRealm.where(Task.class).findFirst().get_id());
        mSesionApp.setLastItemLista(mRealm.where(Task.class).max(Task._ID_KEY).longValue());
    }
}