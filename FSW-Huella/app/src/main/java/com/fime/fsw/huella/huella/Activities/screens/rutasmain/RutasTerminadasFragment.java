package com.fime.fsw.huella.huella.Activities.screens.rutasmain;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fime.fsw.huella.huella.Activities.HuellaApplication;
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
public class RutasTerminadasFragment extends Fragment {


    private static final String TAG = HuellaApplication.APP_TAG + RutasARealizarFragment.class.getSimpleName();

    private Context mContext;
    private Realm mRealm;
    private SesionAplicacion mSesionApp;

    private RecyclerView rvRutasDone;
    private RutasRecyclerViewAdapter rvRutasDoneAdapter;
    private View recyclerContainer, emptyContainer;

    public RutasTerminadasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rutas_terminadas, container, false);

        mContext = getContext();
        mRealm = Realm.getDefaultInstance();
        mSesionApp = new SesionAplicacion(mContext);

        initComponentes(view);

        return view;
    }

    public void initComponentes(View v) {
        rvRutasDone = (RecyclerView) v.findViewById(R.id.rutas_recyclerview);
        recyclerContainer = v.findViewById(R.id.recyclerview_container);
        emptyContainer = v.findViewById(R.id.empty_state);

        loadRecyclerView();
    }

    public void loadRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvRutasDone.setHasFixedSize(true);
        rvRutasDone.setLayoutManager(linearLayoutManager);

        OrderedRealmCollection<Route> orderedRoutes = RealmProvider.getAllFinishedRoutes(mRealm);

        rvRutasDoneAdapter = new RutasRecyclerViewAdapter(mContext, orderedRoutes, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Route route = rvRutasDoneAdapter.getItem(position);
                Log.d(TAG, route.toString());
            }

            @Override
            public void onItemLongClick(View v, int position) {
            }
        });

        rvRutasDone.setAdapter(rvRutasDoneAdapter);

        if (orderedRoutes.isEmpty()){
            showEmptyState();
        } else {
            showRecycler();
        }
    }

    public void showRecycler() {
        recyclerContainer.setVisibility(View.VISIBLE);
        emptyContainer.setVisibility(View.GONE);
    }

    public void showEmptyState(){
        emptyContainer.setVisibility(View.VISIBLE);
        recyclerContainer.setVisibility(View.GONE);
    }

}
