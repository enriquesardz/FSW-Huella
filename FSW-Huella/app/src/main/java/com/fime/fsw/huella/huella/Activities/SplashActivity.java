package com.fime.fsw.huella.huella.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.fime.fsw.huella.huella.Activities.screens.login.PrefectoLoginActivity;
import com.fime.fsw.huella.huella.Activities.screens.tasksmain.RecorridoMainActivity;
import com.fime.fsw.huella.huella.Activities.screens.rutasmain.RutasListaActivity;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SesionAplicacion sesionAplicacion = new SesionAplicacion(getApplicationContext());
        Intent intent;

        Fabric.with(this, new Crashlytics());
              Fabric.with(this, new Crashlytics());
        if(!sesionAplicacion.usuarioLogeado()){
            //El usuario no esta logeado
            intent = new Intent(this, PrefectoLoginActivity.class);
        }
        else if (!sesionAplicacion.routeIsSelected()){
            intent = new Intent(this,RutasListaActivity.class);
        }
        else {
            //El usuario esta logeado y ya descargo
            intent = new Intent(this, RecorridoMainActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
