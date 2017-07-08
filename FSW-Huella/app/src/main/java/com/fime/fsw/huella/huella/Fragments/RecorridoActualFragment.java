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

import com.fime.fsw.huella.huella.Data.Modelos.Task;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.UI.RecyclerView.RecorridoAdapter;
import com.fime.fsw.huella.huella.UI.RecyclerView.RecyclerViewItemClickListener;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;


public class RecorridoActualFragment extends Fragment {

    private static final String TAG = APP_TAG + RecorridoActualFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;

    private RecyclerView rvRecorrido;
    private RecorridoAdapter rvRecorridoAdapter;

    private Context mContext;
    private Realm mRealm;

    public RecorridoActualFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recorrido_actual, container, false);
        mContext = getContext();
        mRealm = Realm.getDefaultInstance();

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //Se inicia el Recycler View Recorrido
        rvRecorrido = (RecyclerView) view.findViewById(R.id.recorrido_actual_recyclerview);

        rvRecorrido.setHasFixedSize(true);
        rvRecorrido.setLayoutManager(linearLayoutManager);

        //Se obtiene la info de nuestro Realm
        final OrderedRealmCollection<Task> recorridoData = getAllRealmTasks();

        //Creamos un adaptador nuevo, con un onItemClickListener
        rvRecorridoAdapter = new RecorridoAdapter(mContext, recorridoData, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Task item = rvRecorridoAdapter.getItem(position);
                sendToDetailFragment(item);
            }
        });
        rvRecorrido.setAdapter(rvRecorridoAdapter);
    }

    public OrderedRealmCollection<Task> getAllRealmTasks(){
        return mRealm.where(Task.class).findAllSorted(Task.ROOM_KEY);
    }

    public void sendToDetailFragment(Task task){
        Log.i(TAG, task.toString());
        //Trigger de onRecorridoActualItemSelected en RecorridoMainActivity para comunicar con
        //DatosVisitaFragment
        if (mListener != null) {
            mListener.onRecorridoActualItemSelected(task);
        }
    }
}
