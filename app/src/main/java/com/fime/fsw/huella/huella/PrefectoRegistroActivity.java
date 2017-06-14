package com.fime.fsw.huella.huella;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.fime.fsw.huella.huella.utilidad.SesionAplicacion;
import com.fime.fsw.huella.huella.utilidad.ValidacionLogin;

public class PrefectoRegistroActivity extends AppCompatActivity {

    private Button mAceptarButton;

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefecto_registro);

        mContext = this;

        final SesionAplicacion sesionAplicacion = new SesionAplicacion(mContext);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.prefecto_registro_titulo));

        mAceptarButton = (Button)findViewById(R.id.aceptar_button);
        mAceptarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Revisar si el usuario se debe logear desde aqui o no.
                //ValidacionLogin validacionLogin = new ValidacionLogin(mContext, "test");
                //Tal vez no sea necesario logear al usuario aqui, solamente que cree la cuenta y despues tenga que logearse

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
        switch(item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(mContext, MenuInicioSesionActivity.class));
                finish();
            default:
            return super.onOptionsItemSelected(item);
        }
    }
}
