package com.fime.fsw.huella.huella.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fime.fsw.huella.huella.Data.Modelos.Task;
import com.fime.fsw.huella.huella.Objetos.RecorridoActualItem;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.RecorridoActualAdapter;
import com.fime.fsw.huella.huella.Utilidad.RecyclerViewItemClickListener;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;


public class RecorridoActualFragment extends Fragment {

    private static final String TAG = APP_TAG + RecorridoActualFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;

    private RecyclerView rvRecorrido;
    private RecorridoActualAdapter rvRecorridoAdapter;

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
        void onRecorridoActualItemSelected(long id, String horaFime, String salonFime);
    }

    private void initComponentes(View view) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //Se inicia el Recycler View Recorrido
        rvRecorrido = (RecyclerView) view.findViewById(R.id.recorrido_actual_recyclerview);

        rvRecorrido.setHasFixedSize(true);
        rvRecorrido.setLayoutManager(linearLayoutManager);
        rvRecorrido.addItemDecoration(new DividerItemDecoration(rvRecorrido.getContext(), linearLayoutManager.getOrientation()));


        //Se obtiene la info de nuestro Realm
        final ArrayList<RecorridoActualItem> recorridoData = getAllRealmTasks();

        //Creamos un adaptador nuevo, con un onItemClickListener
        rvRecorridoAdapter = new RecorridoActualAdapter(mContext, recorridoData, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                RecorridoActualItem item = recorridoData.get(position);
                sendToDetailFragment(item);
            }
        });
        rvRecorrido.setAdapter(rvRecorridoAdapter);
    }

    public ArrayList<RecorridoActualItem> getAllRealmTasks(){

        ArrayList<RecorridoActualItem> data = new ArrayList<>();
        RealmResults<Task> query = mRealm.where(Task.class).findAll();

        for (Task task : query) {
            data.add(new RecorridoActualItem(task.get_id(), task.getAcademyHour(), task.getRoom(), task.getTaskState()));
        }

        return data;
    }

    public void sendToDetailFragment(RecorridoActualItem item){
        long itemId = item.getID();
        String horaFime = item.getHoraFime();
        String salonFime = item.getSalonFime();

        Log.i(TAG, "ID: " + itemId + " Hora: " + horaFime + " Salon: " + salonFime);
        //Trigger de onRecorridoActualItemSelected en RecorridoMainActivity para comunicar con
        //DatosVisitaFragment
        if (mListener != null) {
            mListener.onRecorridoActualItemSelected(itemId, horaFime, salonFime);
        }
    }
}
