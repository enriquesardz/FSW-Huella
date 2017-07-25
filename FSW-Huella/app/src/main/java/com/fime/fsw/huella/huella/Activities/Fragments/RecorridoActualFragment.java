package com.fime.fsw.huella.huella.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fime.fsw.huella.huella.API.APICodo;
import com.fime.fsw.huella.huella.API.Endpoints.APIServices;
import com.fime.fsw.huella.huella.Data.Modelos.Route;
import com.fime.fsw.huella.huella.Data.Modelos.Task;
import com.fime.fsw.huella.huella.Data.Provider.RealmProvider;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.UI.RecyclerView.RecorridoAdapter;
import com.fime.fsw.huella.huella.UI.RecyclerView.RecyclerViewItemClickListener;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;

import java.util.HashMap;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;


public class RecorridoActualFragment extends Fragment {

    private static final String TAG = APP_TAG + RecorridoActualFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;

    private RecyclerView rvRecorrido;
    private RecorridoAdapter rvRecorridoAdapter;
    private LinearLayout loadingState, recyclerContainer, emptyState;

    private Context mContext;
    private Realm mRealm;
    private SesionAplicacion mSesionApp;

    public RecorridoActualFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recorrido_actual, container, false);
        mContext = getContext();
        mRealm = Realm.getDefaultInstance();
        mSesionApp = new SesionAplicacion(mContext);

        initComponentes(view);

        return view;
    }

    //Se asegura de que se este implementando el OnFragmentInteractionListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    //Interfaz implementada por RecorridoMainActivity que se encarga de pasarle los datos
    //al fragment DatosVisitaFragment
    public interface OnFragmentInteractionListener {
        void onRecorridoActualItemSelected(Task task);
    }

    private void initComponentes(View view) {

        loadingState = (LinearLayout)view.findViewById(R.id.loading_state);
        recyclerContainer = (LinearLayout)view.findViewById(R.id.recyclerview_container);
        emptyState = (LinearLayout)view.findViewById(R.id.empty_state);

        showLoadingState();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //Se inicia el Recycler View Recorrido
        rvRecorrido = (RecyclerView) view.findViewById(R.id.recorrido_actual_recyclerview);

        rvRecorrido.setHasFixedSize(true);
        rvRecorrido.setLayoutManager(linearLayoutManager);

        String routeId = mSesionApp.getCurrentRutaId();

        Route route = RealmProvider.getRouteByRouteId(mRealm, routeId);

        //Si ya se descargaron los datos, getRoute deberia de regresar al menos una ruta
        //por lo tanto, no vuela a descargar y solamente muestra los datos en el RecyclerView

        if (route.getTasks().size() == 0){
            startDescarga();
        }
        else{
            setRVRecorridoAdapter(route);
        }

    }

    private void startDescarga() {

        HashMap<String,String> userData = mSesionApp.getDetalleUsuario();

        String routeId = mSesionApp.getCurrentRutaId();
        String token = userData.get(SesionAplicacion.KEY_USER_TOKEN);

        APIServices service = APICodo.signedSingleRoute().create(APIServices.class);
        Call<Route> call = service.descargaRecorrido(routeId,token);

        call.enqueue(new Callback<Route>() {
            @Override
            public void onResponse(Call<Route> call, Response<Route> response) {
                //Se ejecuta si el webservice regresa algo, la respuesta
                //es una lista de Tasks, entonces la respuesta se guarda en una Lista de tipo Tasks

                Route route = response.body();

                if (route == null) {
                    Log.e(TAG, "No hay respuesta de la API + " + response.toString());
                    showEmptyState();
                    return;
                }

                //Se guardan los datos a nuestro Realm

                /*
                Aqui se hace update a una Route ya existente, se le agregan varios valores pero
                principalmente se le agrega una lista de Tasks
                */
                RealmProvider.saveRouteToRealm(mRealm, route);

                setRVRecorridoAdapter(route);

            }

            @Override
            public void onFailure(Call<Route> call, Throwable t) {
                //No se descargo nada
                Toast.makeText(mContext, "No se descargo.", Toast.LENGTH_SHORT).show();
                showEmptyState();
            }
        });
    }

    public void sendToDetailFragment(Task task) {
        Log.i(TAG, task.toString());
        //Trigger de onRecorridoActualItemSelected en RecorridoMainActivity para comunicar con
        //DatosVisitaFragment
        if (mListener != null) {
            mListener.onRecorridoActualItemSelected(task);
        }
    }

    public void setRVRecorridoAdapter(Route route){

        showRecyclerView();

        long currentTask = route.getCurrentTask();

        //Se obtiene la info de nuestro Realm
        String routeDay = route.getDay();
        String routeHour = route.getAcademyHour();

        final OrderedRealmCollection<Task> recorridoData = RealmProvider.getAllOrderedTasks(mRealm);

        //Creamos un adaptador nuevo, con un onItemClickListener
        rvRecorridoAdapter = new RecorridoAdapter(mContext, recorridoData, currentTask, routeHour, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Task item = rvRecorridoAdapter.getItem(position);
                sendToDetailFragment(item);
            }
        });

        rvRecorrido.setAdapter(rvRecorridoAdapter);
    }

    public void showLoadingState(){
        loadingState.setVisibility(View.VISIBLE);
        recyclerContainer.setVisibility(View.GONE);
        emptyState.setVisibility(View.GONE);
    }

    public void showRecyclerView(){
        recyclerContainer.setVisibility(View.VISIBLE);
        loadingState.setVisibility(View.GONE);
        emptyState.setVisibility(View.GONE);
    }

    public void showEmptyState(){
        emptyState.setVisibility(View.VISIBLE);
        loadingState.setVisibility(View.GONE);
        recyclerContainer.setVisibility(View.GONE);
    }

}