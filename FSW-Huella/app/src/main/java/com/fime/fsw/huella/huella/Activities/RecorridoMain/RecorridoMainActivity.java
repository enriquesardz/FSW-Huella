package com.fime.fsw.huella.huella.Activities.RecorridoMain;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.fime.fsw.huella.huella.Activities.HuellaApplication;
import com.fime.fsw.huella.huella.Activities.InicioSesion.MenuInicioSesionActivity;
import com.fime.fsw.huella.huella.Data.Modelos.Task;
import com.fime.fsw.huella.huella.Data.Modelos.UploadCheckout;
import com.fime.fsw.huella.huella.Fragments.DatosVisitaFragment;
import com.fime.fsw.huella.huella.Fragments.RecorridoActualFragment;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;
import com.google.gson.Gson;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


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
        String id = task.get_id();
        mBundle.putString(Task._ID_KEY, id);
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

}