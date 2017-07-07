package com.fime.fsw.huella.huella.Activities.InicioSesion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fime.fsw.huella.huella.R;

public class PrefectoRegistroActivity extends AppCompatActivity {

    private Button btnAceptar;
    private TextView tvNombre;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefecto_registro);

        mContext = this;

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.prefecto_registro_titulo));
        }

        initComponentes();

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Bienvenido: " + tvNombre.getText(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mContext, MenuInicioSesionActivity.class));
                finish();
            }
        });
    }

    private void initComponentes() {
        btnAceptar = (Button) findViewById(R.id.aceptar_button);
        tvNombre = (TextView) findViewById(R.id.nombre_textview);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
