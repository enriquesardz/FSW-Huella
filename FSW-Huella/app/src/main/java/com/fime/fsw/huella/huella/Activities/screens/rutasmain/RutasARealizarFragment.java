package com.fime.fsw.huella.huella.Activities.screens.rutasmain;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fime.fsw.huella.huella.Activities.HuellaApplication;
import com.fime.fsw.huella.huella.Activities.screens.tasksmain.RecorridoMainActivity;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Route;
import com.fime.fsw.huella.huella.Data.Provider.RealmProvider;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.UI.RecyclerView.RecyclerViewItemClickListener;
import com.fime.fsw.huella.huella.UI.RecyclerView.RutasRecyclerViewAdapter;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class RutasARealizarFragment extends Fragment {

    private static final String TAG = HuellaApplication.APP_TAG + RutasARealizarFragment.class.getSimpleName();

    private Context mContext;
    private Realm mRealm;
    private SesionAplicacion mSesionApp;

    private RecyclerView rvRutasTodo;
    private RutasRecyclerViewAdapter rvRutasTodoAdapter;

    public RutasARealizarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rutas_arealizar, container, false);

        mContext = getContext();
        mRealm = Realm.getDefaultInstance();
        mSesionApp = new SesionAplicacion(mContext);

        initComponentes(view);

        return view;
    }

    public void initComponentes(View v) {
        rvRutasTodo = (RecyclerView) v.findViewById(R.id.rutas_recyclerview);

        loadRecyclerView();
    }

    public void loadRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvRutasTodo.setHasFixedSize(true);
        rvRutasTodo.setLayoutManager(linearLayoutManager);

        String user = mSesionApp.getDetalleUsuario().get(SesionAplicacion.KEY_USUARIO);

        OrderedRealmCollection<Route> orderedRoutes = RealmProvider.getAllUnfinishedRoutes(user, mRealm);

        rvRutasTodoAdapter = new RutasRecyclerViewAdapter(mContext, orderedRoutes, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Route route = rvRutasTodoAdapter.getItem(position);
                startRecorridoActivity(route);
                Log.d(TAG, route.toString());
            }

            @Override
            public void onItemLongClick(View v, int position) {
            }
        });

        rvRutasTodo.setAdapter(rvRutasTodoAdapter);
    }

    public void startRecorridoActivity(Route route) {
        Intent intent = new Intent(mContext, RecorridoMainActivity.class);
        String routeId = route.get_id();

        //Guarda el ID de la ruta que se selecciona
        mSesionApp.crearSesionRutaSeleccionada(routeId);
        startActivity(intent);
        //finish();
    }

}
