package com.fime.fsw.huella.huella;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PrefectoLoginActivity extends AppCompatActivity {

    private Button mIniciarSesionButton;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefecto_login);
        mContext = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.prefecto_login_titulo));

        mIniciarSesionButton = (Button)findViewById(R.id.iniciar_sesion_button);

        mIniciarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
