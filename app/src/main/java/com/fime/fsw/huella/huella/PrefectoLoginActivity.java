package com.fime.fsw.huella.huella;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        mIniciarSesionButton = (Button)findViewById(R.id.iniciar_sesion_button);

        mIniciarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, DescargaRutaActivity.class));
                finish();
            }
        });
    }
}
