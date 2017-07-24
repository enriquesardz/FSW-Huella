package com.fime.fsw.huella.huella.Fragments;

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
import com.fime.fsw.huella.huella.API.ServiciosAPI.DescargaRecorridosService;
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

        loadingState.setVisibility(View.VISIBLE);
        recyclerContainer.setVisibility(View.GONE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //Se inicia el Recycler View Recorrido
        rvRecorrido = (RecyclerView) view.findViewById(R.id.recorrido_actual_recyclerview);

        rvRecorrido.setHasFixedSize(true);
        rvRecorrido.setLayoutManager(linearLayoutManager);

        Route route = RealmProvider.getRoute(mRealm);

        //Si ya se descargaron los datos, getRoute deberia de regresar al menos una ruta
        //por lo tanto, no vuela a descargar y solamente muestra los datos en el RecyclerView

        if (route.getTasks().size() == 0){
            startDescarga();
        }
        else{
            setRVRecorridoAdapter();
        }

    }

    private void startDescarga() {

        HashMap<String,String> userData = mSesionApp.getDetalleUsuario();

        String routeId = mSesionApp.getCurrentRutaId();
        String token = "JWT " + userData.get(SesionAplicacion.KEY_USER_TOKEN);

        DescargaRecorridosService service = APICodo.signedSingleRoute().create(DescargaRecorridosService.class);
        Call<Route> call = service.descargaRecorrido(routeId,token);

        call.enqueue(new Callback<Route>() {
            @Override
            public void onResponse(Call<Route> call, Response<Route> response) {
                //Se ejecuta si el webservice regresa algo, la respuesta
                //es una lista de Tasks, entonces la respuesta se guarda en una Lista de tipo Tasks

                Route route = response.body();

                if (route == null) {
                    Log.e(TAG, "No hay respuesta de la API + " + response.toString());
                    loadingState.setVisibility(View.GONE);
                    emptyState.setVisibility(View.VISIBLE);
                    recyclerContainer.setVisibility(View.GONE);
                    return;
                }

                //Se guardan los datos a nuestro Realm
                RealmProvider.saveRouteToRealm(mRealm, route);

                setInitialAndFinalTask();

                setRVRecorridoAdapter();

            }

            @Override
            public void onFailure(Call<Route> call, Throwable t) {
                //No se descargo nada
                Toast.makeText(mContext, "No se descargo.", Toast.LENGTH_SHORT).show();
                loadingState.setVisibility(View.GONE);
                recyclerContainer.setVisibility(View.GONE);
                emptyState.setVisibility(View.VISIBLE);
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

    public void setInitialAndFinalTask() {
        mSesionApp.setCurrentTaskPosition(mRealm.where(Task.class).min(Task.SEQUENCE_FIELD).longValue());
        mSesionApp.setLastTaskPosition(mRealm.where(Task.class).max(Task.SEQUENCE_FIELD).longValue());
    }

    public void setRVRecorridoAdapter(){

        loadingState.setVisibility(View.GONE);
        emptyState.setVisibility(View.GONE);
        recyclerContainer.setVisibility(View.VISIBLE);

        Route route = RealmProvider.getRoute(mRealm);
        long currentItem = mSesionApp.getCurrentTaskPosition();

        //Se obtiene la info de nuestro Realm
        String routeDay = route.getDay();
        String routeHour = route.getAcademyHour();

        final OrderedRealmCollection<Task> recorridoData = RealmProvider.getAllOrderedTasks(mRealm);

        //Creamos un adaptador nuevo, con un onItemClickListener
        rvRecorridoAdapter = new RecorridoAdapter(mContext, recorridoData, currentItem, routeHour, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Task item = rvRecorridoAdapter.getItem(position);
                sendToDetailFragment(item);
            }
        });

        rvRecorrido.setAdapter(rvRecorridoAdapter);
    }

}
