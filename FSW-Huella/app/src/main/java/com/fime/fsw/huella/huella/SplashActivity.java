package com.fime.fsw.huella.huella;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SesionAplicacion sesionAplicacion = new SesionAplicacion(getApplicationContext());
        Intent intent;

        //TODO: Validar si ya hay una sesion en progreso, saltarse a la DescargaRutaActivity si es el caso.
        //TODO: Si ya se descargo, entonces se salta direco a RecorridoMainActivity

        if(!sesionAplicacion.usuarioLogeado()){
            //El usuario no esta logeado
            intent = new Intent(this, MenuInicioSesionActivity.class);
        }
        else if(!sesionAplicacion.usuarioYaDescargo()){
            //El usuario esta logeado pero no ha descargado
            intent = new Intent(this, DescargaRutaActivity.class);
        }
        else {
            //El usuario esta logeado y ya descargo
            intent = new Intent(this, RecorridoMainActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
