package com.fime.fsw.huella.huella;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fime.fsw.huella.huella.data.objetos.Target;
import com.fime.fsw.huella.huella.data.services.DescargaRutaServicio;
import com.fime.fsw.huella.huella.utilidad.SesionAplicacion;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DescargaRutaActivity extends AppCompatActivity {

    private MaterialSpinner spinnerClaveArea;
    private MaterialSpinner spinnerPeriodo;
    private Button btnDescargar;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descarga_ruta);

        mContext = DescargaRutaActivity.this;

        initComponentes();

        List<String> claveAreaData = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.druta_claves_area_spinner)));
        List<String> periodoData = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.druta_periodo_spinner)));

        spinnerClaveArea.setItems(claveAreaData);
        spinnerPeriodo.setItems(periodoData);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://young-escarpment-48238.herokuapp.com/")
                //.addConverterFactory(GsonConverterFactory.create())
                .build();


        DescargaRutaServicio servicio = retrofit.create(DescargaRutaServicio.class);
        Call<Target> targetCall = servicio.getTarget();

        targetCall.enqueue(new Callback<Target>() {
            @Override
            public void onResponse(Call<Target> call, Response<Target> response) {
                Target target = response.body();
            }

            @Override
            public void onFailure(Call<Target> call, Throwable t) {
                Toast.makeText(mContext, "Error en la descarga", Toast.LENGTH_SHORT).show();
            }
        });

        btnDescargar.setOnClickListener(new View.OnClickListener() {
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

    private void initComponentes(){
        spinnerClaveArea = (MaterialSpinner) findViewById(R.id.clave_area_spinner);
        spinnerPeriodo = (MaterialSpinner) findViewById(R.id.periodo_spinner);
        btnDescargar = (Button)findViewById(R.id.descargar_button);
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
