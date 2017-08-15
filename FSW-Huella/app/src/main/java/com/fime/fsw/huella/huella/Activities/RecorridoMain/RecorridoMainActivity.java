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
import android.util.Log;
import android.view.View;

import com.fime.fsw.huella.huella.Activities.HuellaApplication;
import com.fime.fsw.huella.huella.Activities.RutasLista.RutasListaActivity;
import com.fime.fsw.huella.huella.Data.Modelos.Route;
import com.fime.fsw.huella.huella.Data.Modelos.Task;
import com.fime.fsw.huella.huella.Data.Provider.RealmProvider;
import com.fime.fsw.huella.huella.Activities.Fragments.DatosVisitaFragment;
import com.fime.fsw.huella.huella.Activities.Fragments.RecorridoActualFragment;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import io.realm.Realm;


public class RecorridoMainActivity extends AppCompatActivity implements RecorridoActualFragment.OnFragmentInteractionListener, DatosVisitaFragment.OnFragmentInteractionListener {

    public static final String TAG = HuellaApplication.APP_TAG + RecorridoMainActivity.class.getSimpleName();
    public static final String IS_TASK_NULL_KEY = "isTaskNull";
    public static final String IS_LAST_TASK_KEY = "isLastTask";

    private BottomBar mBarraNav;
    private Fragment mFragment;
    private Context mContext;
    private Bundle mBundle;
    private Realm mRealm;
    private SesionAplicacion mSesionApp;

    private Task mTask;
    private Route mRoute;

    private int routeCurrentTaskSequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorrido_main);

        mContext = RecorridoMainActivity.this;
        mSesionApp = new SesionAplicacion(mContext);
        mRealm = Realm.getDefaultInstance();

        initComponents();
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
                                    mSesionApp.dropRutaSeleccionada();
                                    startActivity(new Intent(mContext, RutasListaActivity.class));
                                    finish();
                                }
                            })
                    .setNegativeButton(getResources().getString(R.string.mrecorrido_no), null)
                    .show();
        }
    }

    @Override
    public void onRecorridoActualItemSelected(Task task) {
//        String id = task.get_id();
//        mBundle.putString(Task._ID_FIELD, id);
//        mBarraNav.selectTabWithId(R.id.tab_datos_visita);
    }

    @Override
    public void onCodigoBarrasFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void initComponents() {
        mBundle = new Bundle();

        RealmProvider.dataCount(mRealm);

        setUpBarraNavegacion();

        String routeId = mSesionApp.getCurrentRutaId();
        mRoute = RealmProvider.getRouteByRouteId(mRealm, routeId);

        routeCurrentTaskSequence = mRoute.getCurrentTask();
        Log.d(TAG, "Current task sequence " + routeCurrentTaskSequence);

        mTask = mRoute.getTasks().where().equalTo(Task.SEQUENCE_FIELD, routeCurrentTaskSequence).findFirst();


        checkAndSetRouteCompleted();

        if (mTask != null) {
            mBundle.putBoolean(IS_TASK_NULL_KEY, false);
        }
        else{
            mBundle.putBoolean(IS_TASK_NULL_KEY, true);
        }

        if (routeCurrentTaskSequence == mRoute.getLastTask()) {
            mBundle.putBoolean(IS_LAST_TASK_KEY, true);
        }


        mBarraNav.selectTabWithId(R.id.tab_datos_visita);
    }

    public void setUpBarraNavegacion(){
        mBarraNav = (BottomBar) findViewById(R.id.barra_navegacion);

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

    }

    public void checkAndSetRouteCompleted(){
        if(mRoute.getCurrentTask() == mRoute.getLastTask()){
            RealmProvider.setRouteIsCompletedByRoute(mRealm, mRoute);
        }
    }
}