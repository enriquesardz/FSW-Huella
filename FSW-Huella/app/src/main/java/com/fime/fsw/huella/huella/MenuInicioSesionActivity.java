package com.fime.fsw.huella.huella;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fime.fsw.huella.huella.Fingerprint.NewHuellaActivity;

public class MenuInicioSesionActivity extends AppCompatActivity {

    private Button btnIniciarSesion;
    private Button btnRegistro;
    private Button btnIniciarConHuella;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicio_sesion);
        mContext = this;

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.menu_inicio_titulo));
        }

        initComponentes();

    }

    private void initComponentes() {
        btnIniciarSesion = (Button) findViewById(R.id.iniciar_sesion_button);
        btnRegistro = (Button) findViewById(R.id.registrarse_button);
        btnIniciarConHuella = (Button) findViewById(R.id.iniciar_huella_button);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, PrefectoLoginActivity.class));
                finish();
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, PrefectoRegistroActivity.class));
                finish();
            }
        });

        btnIniciarConHuella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, NewHuellaActivity.class));
                finish();
            }
        });
    }
}
