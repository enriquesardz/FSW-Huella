package com.fime.fsw.huella.huella;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fime.fsw.huella.huella.prueba.RecogHuellaActivity;

public class MenuInicioSesionActivity extends AppCompatActivity {

    private Button mIniciarSesionButton;
    private Button mRegistroButton;
    private Button mIniciarConHuellaButton;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicio_sesion);
        mContext = this;
        getSupportActionBar().setTitle(getResources().getString(R.string.menu_inicio_titulo));

        inicializarComponentes();

    }

    private void inicializarComponentes(){
        mIniciarSesionButton = (Button)findViewById(R.id.iniciar_sesion_button);
        mRegistroButton = (Button)findViewById(R.id.registrarse_button);
        mIniciarConHuellaButton = (Button)findViewById(R.id.iniciar_huella_button);

        mIniciarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, PrefectoLoginActivity.class));
                finish();
            }
        });

        mRegistroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, PrefectoRegistroActivity.class));
                finish();
            }
        });

        mIniciarConHuellaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, RecogHuellaActivity.class));
                finish();
            }
        });
    }
}
