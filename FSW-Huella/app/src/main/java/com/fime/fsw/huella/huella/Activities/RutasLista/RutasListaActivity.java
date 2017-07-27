package com.fime.fsw.huella.huella.Activities.RutasLista;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fime.fsw.huella.huella.API.APICodo;
import com.fime.fsw.huella.huella.API.Endpoints.APIServices;
import com.fime.fsw.huella.huella.Activities.InicioSesion.PrefectoLoginActivity;
import com.fime.fsw.huella.huella.Activities.RecorridoMain.RecorridoMainActivity;
import com.fime.fsw.huella.huella.Data.Modelos.Route;
import com.fime.fsw.huella.huella.Data.Provider.RealmProvider;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.UI.RecyclerView.RecyclerViewItemClickListener;
import com.fime.fsw.huella.huella.UI.RecyclerView.RutasRecyclerViewAdapter;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;

import java.util.HashMap;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

public class RutasListaActivity extends AppCompatActivity {

    private static final String TAG = APP_TAG + RutasListaActivity.class.getSimpleName();


    Context mContext;
    SesionAplicacion mSesionApp;
    Realm mRealm;

    TextView tvResponse;
    RecyclerView rvRutas;

    RutasRecyclerViewAdapter rvRutasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas_lista);

        mContext = RutasListaActivity.this;
        mSesionApp = new SesionAplicacion(mContext);
        mRealm = Realm.getDefaultInstance();

        initComponentes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cerrar_sesion:
                mSesionApp.terminarSesionAplicacion();
                RealmProvider.dropAllRealmTables(mRealm);
                startActivity(new Intent(mContext, PrefectoLoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(mContext)
                .setMessage(getResources().getString(R.string.rutl_salir_aplicacion))
                .setPositiveButton(getResources().getString(R.string.mrecorrido_si),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RutasListaActivity.super.onBackPressed();
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.mrecorrido_no), null)
                .show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    public void initComponentes() {

        getSupportActionBar().hide();

        tvResponse = (TextView) findViewById(R.id.dia_textview);
        rvRutas = (RecyclerView) findViewById(R.id.rutas_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvRutas.setHasFixedSize(true);
        rvRutas.setLayoutManager(linearLayoutManager);

        Route route = RealmProvider.getRoute(mRealm);

        if (route == null){
            descargarRutas();
        } else{
            loadRecyclerView();
        }


    }

    public void descargarRutas(){
        HashMap<String, String> datosUsuario = mSesionApp.getDetalleUsuario();

        APIServices service = APICodo.signedRouteList().create(APIServices.class);
        Call<List<Route>> call = service.descargaRutas(datosUsuario.get(SesionAplicacion.KEY_USER_TOKEN));

        call.enqueue(new Callback<List<Route>>() {
            @Override
            public void onResponse(Call<List<Route>> call, Response<List<Route>> response) {

                tvResponse.setText(response.body().get(0).getDay());
                //Si el web service no regresa nada
                if (response.body() == null || !response.isSuccessful()) {
                    Log.e(TAG, "Api retorno NULL: " + response.toString());
                    return;
                }

                //Guarda los datos al Realm
                RealmProvider.saveRouteListToRealm(mRealm, response.body());
                loadRecyclerView();
            }

            @Override
            public void onFailure(Call<List<Route>> call, Throwable t) {
                Toast.makeText(mContext, "Fallo en la descarga", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadRecyclerView(){

        OrderedRealmCollection<Route> orderedRoutes = RealmProvider.getAllOrderedRoutes(mRealm);

        rvRutasAdapter = new RutasRecyclerViewAdapter(mContext, orderedRoutes, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Route route = rvRutasAdapter.getItem(position);
                saveRouteIdStartRecorrido(route);
            }
        });

        rvRutas.setAdapter(rvRutasAdapter);
    }

    public void saveRouteIdStartRecorrido(Route route){
        Intent intent = new Intent(mContext, RecorridoMainActivity.class);
        String routeId = route.get_id();

        mSesionApp.crearSesionRutaSeleccionada(routeId);
        startActivity(intent);

        finish();
    }
}
