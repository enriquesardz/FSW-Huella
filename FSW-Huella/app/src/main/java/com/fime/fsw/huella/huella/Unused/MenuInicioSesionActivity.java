//package com.fime.fsw.huella.huella.Activities.InicioSesion;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import com.fime.fsw.huella.huella.R;
//
//public class MenuInicioSesionActivity extends AppCompatActivity {
//
//    private Button btnIniciarSesion;
//
//    private Context mContext;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_menu_inicio_sesion);
//
//        mContext = MenuInicioSesionActivity.this;
//
//        initComponentes();
//
//    }
//
//    private void initComponentes() {
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle(getResources().getString(R.string.menu_inicio_titulo));
//        }
//
//        btnIniciarSesion = (Button) findViewById(R.id.iniciar_sesion_button);
//
//        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(mContext, PrefectoLoginActivity.class));
//                finish();
//            }
//        });
//    }
//}
