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

import com.fime.fsw.huella.huella.API.APICodo;
import com.fime.fsw.huella.huella.API.ServiciosAPI.DescargaRutaService;
import com.fime.fsw.huella.huella.Activities.InicioSesion.MenuInicioSesionActivity;
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
                //TODO: Aqui se deben de regresar los checkouts al web service.
                startActivity(new Intent(mContext, MenuInicioSesionActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(mContext)
                .setMessage(getResources().getString(R.string.mrecorrido_seguro_salir))
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

        //TODO: Hard coded?
        DescargaRutaService service = APICodo.signedRouteList().create(DescargaRutaService.class);
        Call<List<Route>> call = service.descargaRutas("JWT " + datosUsuario.get(SesionAplicacion.KEY_USER_TOKEN));



        call.enqueue(new Callback<List<Route>>() {
            @Override
            public void onResponse(Call<List<Route>> call, Response<List<Route>> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    Log.e(TAG, "Api retorno NULL: " + response.toString());
                    tvResponse.setText(response.toString());
                    return;
                }

                //Guarda los datos al Realm
                RealmProvider.saveRouteListToRealm(mRealm, response.body());
                loadRecyclerView();
            }

            @Override
            public void onFailure(Call<List<Route>> call, Throwable t) {
                tvResponse.setText("No descargo");
            }
        });
    }

    public void loadRecyclerView(){
        OrderedRealmCollection<Route> orderedRoutes = RealmProvider.getAllOrderedRoutes(mRealm);
        rvRutasAdapter = new RutasRecyclerViewAdapter(mContext, orderedRoutes, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Route route = rvRutasAdapter.getItem(position);
                sendRouteToRecorridoActivity(route);
            }
        });
        rvRutas.setAdapter(rvRutasAdapter);
    }

    public void sendRouteToRecorridoActivity(Route route){
        Intent intent = new Intent(mContext, RecorridoMainActivity.class);
        String routeId = route.get_id();

        mSesionApp.crearSesionRutaSeleccionada(routeId);
        startActivity(intent);

        finish();
    }
}
