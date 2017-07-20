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
import android.widget.Toast;

import com.fime.fsw.huella.huella.API.APICodo;
import com.fime.fsw.huella.huella.API.ServiciosAPI.DescargaRecorridosService;
import com.fime.fsw.huella.huella.Data.Modelos.Task;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.UI.RecyclerView.RecorridoAdapter;
import com.fime.fsw.huella.huella.UI.RecyclerView.RecyclerViewItemClickListener;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;

import java.util.List;

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

        startDescarga();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //Se inicia el Recycler View Recorrido
        rvRecorrido = (RecyclerView) view.findViewById(R.id.recorrido_actual_recyclerview);

        rvRecorrido.setHasFixedSize(true);
        rvRecorrido.setLayoutManager(linearLayoutManager);


    }

    private void startDescarga() {
        DescargaRecorridosService service = APICodo.signedRoute().create(DescargaRecorridosService.class);
        Call<List<Task>> call = service.descargaRecorrido();

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                //Se ejecuta si el webservice regresa algo, la respuesta
                //es una lista de Tasks, entonces la respuesta se guarda en una Lista de tipo Tasks

                final List<Task> tasks = response.body();

                if (tasks == null) {
                    Log.e(TAG, "No hay respuesta de la API + " + response.body());
                    return;
                }
                //Se guardan los datos a nuestro Realm
                guardarRespuestaARealm(tasks);
                setInitialAndFinalTask();

                long currentItem = mSesionApp.getCurrentItemLista();
                //Se obtiene la info de nuestro Realm
                final OrderedRealmCollection<Task> recorridoData = getAllRealmTasks();

                //Creamos un adaptador nuevo, con un onItemClickListener
                rvRecorridoAdapter = new RecorridoAdapter(mContext, recorridoData, currentItem, new RecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Task item = rvRecorridoAdapter.getItem(position);
                        sendToDetailFragment(item);
                    }
                });

                rvRecorrido.setAdapter(rvRecorridoAdapter);
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                //No se descargo nada
                Toast.makeText(mContext, "No se descargo.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public OrderedRealmCollection<Task> getAllRealmTasks() {
        return mRealm.where(Task.class).findAllSorted(Task._ID_KEY);
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
        mSesionApp.setCurrentItemLista(mRealm.where(Task.class).findFirst().get_id());
        mSesionApp.setLastItemLista(mRealm.where(Task.class).max(Task._ID_KEY).longValue());
    }

    public void guardarRespuestaARealm(final List<Task> tasks) {

        //Se recorre la lista y se guarda cada objeto Task a Realm
        if (tasks == null) {
            Log.e(TAG, "No hay tasks");
            return;
        }

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Task task : tasks) {
                    Task realmTask = realm.copyToRealmOrUpdate(task);
                    Log.i(TAG, realmTask.toString());
                }
            }
        });
    }
}
