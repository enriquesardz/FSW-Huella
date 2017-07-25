//package com.fime.fsw.huella.huella.Unused;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.fime.fsw.huella.huella.Activities.InicioSesion.MenuInicioSesionActivity;
//import com.fime.fsw.huella.huella.R;
//
//public class PrefectoRegistroActivity extends AppCompatActivity {
//
//    private Button btnAceptar;
//    private TextView tvNombre;
//
//    private Context mContext;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_prefecto_registro);
//
//        mContext = this;
//
//        initComponentes();
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        startActivity(new Intent(mContext, MenuInicioSesionActivity.class));
//        finish();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                startActivity(new Intent(mContext, MenuInicioSesionActivity.class));
//                finish();
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    private void initComponentes() {
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setTitle(getResources().getString(R.string.prefecto_registro_titulo));
//        }
//
//        btnAceptar = (Button) findViewById(R.id.aceptar_button);
//        tvNombre = (TextView) findViewById(R.id.nombre_textview);
//
//        btnAceptar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(mContext, MenuInicioSesionActivity.class));
//                finish();
//            }
//        });
//    }
//}
