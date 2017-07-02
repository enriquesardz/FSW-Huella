package com.fime.fsw.huella.huella;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.fime.fsw.huella.huella.Fragments.DatosVisitaFragment;
import com.fime.fsw.huella.huella.Fragments.RecorridoActualFragment;
import com.fime.fsw.huella.huella.Fragments.RecorridoFragment;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class RecorridoMainActivity extends AppCompatActivity implements RecorridoFragment.OnFragmentInteractionListener, RecorridoActualFragment.OnFragmentInteractionListener, DatosVisitaFragment.OnFragmentInteractionListener{

    public static final String KEY_ID_RECORRIDO_ITEM = "id";
    public static final String KEY_HORA_FIME = "hora_fime";
    public static final String KEY_SALON_FIME = "salon_fime";

    private BottomBar mBarraNav;
    private Fragment mFragment;

    private Bundle codigoArgs;


    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorrido_main);

        mContext = getApplicationContext();

        codigoArgs = new Bundle();

        mBarraNav = (BottomBar)findViewById(R.id.barra_navegacion);
        mBarraNav.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_datos_visita){
                    //Puede iniciar vacio/hard coded o se inicia porque
                    //se le dio click a un salon del recorrido actual.
                    mFragment = new DatosVisitaFragment();
                    if (codigoArgs != null){
                        mFragment.setArguments(codigoArgs);
                    }
                }
                else if (tabId == R.id.tab_recorrido_actual){
                    mFragment = new RecorridoActualFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,mFragment).commit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
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
    public void onRecorridoFragmentInteraction(Uri uri){

    }

    @Override
    public void onRecorridoActualItemSelected(long id, String horaFime, String salonFime){
        codigoArgs.putLong(KEY_ID_RECORRIDO_ITEM, id);
        codigoArgs.putString(KEY_HORA_FIME, horaFime);
        codigoArgs.putString(KEY_SALON_FIME, salonFime);
        mBarraNav.selectTabWithId(R.id.tab_datos_visita);
    }

    @Override
    public void onCodigoBarrasFragmentInteraction(Uri uri){

    }


}

