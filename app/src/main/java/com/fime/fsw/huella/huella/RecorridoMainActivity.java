package com.fime.fsw.huella.huella;

import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fime.fsw.huella.huella.fragments.CodigoBarrasFragment;
import com.fime.fsw.huella.huella.fragments.RecorridoActualFragment;
import com.fime.fsw.huella.huella.fragments.RecorridoFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class RecorridoMainActivity extends AppCompatActivity implements RecorridoFragment.OnFragmentInteractionListener, RecorridoActualFragment.OnFragmentInteractionListener, CodigoBarrasFragment.OnFragmentInteractionListener{

    private BottomBar mBarraNav;
    private Fragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorrido_main);
        mBarraNav = (BottomBar)findViewById(R.id.barra_navegacion);
        mBarraNav.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_codigo){
                    mFragment = new CodigoBarrasFragment();
                }
                else if (tabId == R.id.tab_datos){
                    mFragment = new RecorridoActualFragment();
                }
                else if (tabId == R.id.tab_recorrido){
                    mFragment = new RecorridoFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,mFragment).commit();
            }
        });
    }
    @Override
    public void onRecorridoFragmentInteraction(Uri uri){

    }

    @Override
    public void onDatosMaestroFragmentInteraction(Uri uri){

    }

    @Override
    public void onCodigoBarrasFragmentInteraction(Uri uri){

    }


}

