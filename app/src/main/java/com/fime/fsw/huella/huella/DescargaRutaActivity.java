package com.fime.fsw.huella.huella;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.fime.fsw.huella.huella.utilidad.SesionAplicacion;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DescargaRutaActivity extends AppCompatActivity {

    private MaterialSpinner mClaveAreaSpinner;
    private MaterialSpinner mPeriodoSpinner;
    private Button mDescargarButton;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descarga_ruta);

        mContext = this;

        mClaveAreaSpinner = (MaterialSpinner) findViewById(R.id.clave_area_spinner);
        List<String> claveAreaData = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.druta_claves_area_spinner)));
        mClaveAreaSpinner.setItems(claveAreaData);


        mPeriodoSpinner = (MaterialSpinner) findViewById(R.id.periodo_spinner);
        List<String> periodoData = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.druta_periodo_spinner)));
        mPeriodoSpinner.setItems(periodoData);

        mDescargarButton = (Button)findViewById(R.id.descargar_button);
        mDescargarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //El usuario esta logeado; aqui se descarga y ahora la aplicacion continuara a abrir el RecorridoMainActivity.
                SesionAplicacion sesionAplicacion = new SesionAplicacion(mContext);
                sesionAplicacion.crearSesionDescarga();
                startActivity(new Intent(mContext, RecorridoMainActivity.class));
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.cerrar_sesion:
                SesionAplicacion sesionAplicacion = new SesionAplicacion(mContext);
                sesionAplicacion.terminarSesionAplicacion();
                startActivity(new Intent(mContext, MenuInicioSesionActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
