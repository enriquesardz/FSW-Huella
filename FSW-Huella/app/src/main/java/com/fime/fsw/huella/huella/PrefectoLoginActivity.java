package com.fime.fsw.huella.huella;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.fime.fsw.huella.huella.utilidad.ValidacionLogin;

public class PrefectoLoginActivity extends AppCompatActivity {

    private Button btnIniciarSesion;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefecto_login);
        mContext = this;

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.prefecto_login_titulo));
        }

        btnIniciarSesion = (Button) findViewById(R.id.iniciar_sesion_button);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Algun tipo de validacion usando ValidacionLogin.class quitar valores hard coded
                //Guarda la sesion del usuario; el usuario ahora esta logeado, pero no ha descargado.
                ValidacionLogin validacionLogin = new ValidacionLogin(mContext, "text");
                startActivity(new Intent(mContext, DescargaRutaActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, MenuInicioSesionActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(mContext, MenuInicioSesionActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
