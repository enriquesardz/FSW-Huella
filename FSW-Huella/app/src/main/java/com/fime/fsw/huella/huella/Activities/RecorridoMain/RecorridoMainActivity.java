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

import com.fime.fsw.huella.huella.Activities.InicioSesion.MenuInicioSesionActivity;
import com.fime.fsw.huella.huella.Data.Modelos.Task;
import com.fime.fsw.huella.huella.Fragments.DatosVisitaFragment;
import com.fime.fsw.huella.huella.Fragments.RecorridoActualFragment;
import com.fime.fsw.huella.huella.Fragments.RecorridoFragment;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


public class RecorridoMainActivity extends AppCompatActivity implements RecorridoFragment.OnFragmentInteractionListener, RecorridoActualFragment.OnFragmentInteractionListener, DatosVisitaFragment.OnFragmentInteractionListener {

    public static final String KEY_ID_TASK = "_id";

    private BottomBar mBarraNav;
    private Fragment mFragment;
    private Context mContext;
    private Bundle mBundle;
    private SesionAplicacion mSesionApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorrido_main);

        mContext = RecorridoMainActivity.this;

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
                startActivity(new Intent(mContext, MenuInicioSesionActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
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

    @Override
    public void onRecorridoFragmentInteraction(Uri uri) {
    }

    @Override
    public void onRecorridoActualItemSelected(Task task) {
        long id = task.get_id();
        mBundle.putLong(KEY_ID_TASK, id);
        mBarraNav.selectTabWithId(R.id.tab_datos_visita);
    }

    @Override
    public void onCodigoBarrasFragmentInteraction(Uri uri) {

    }

    public void initComponents() {
        mBundle = new Bundle();

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


}