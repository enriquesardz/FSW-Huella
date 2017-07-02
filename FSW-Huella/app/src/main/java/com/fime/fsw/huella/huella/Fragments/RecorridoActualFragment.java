package com.fime.fsw.huella.huella.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fime.fsw.huella.huella.Data.API.Modelos.Task;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Objetos.RecorridoActualItem;
import com.fime.fsw.huella.huella.Utilidad.RecorridoActualAdapter;
import com.fime.fsw.huella.huella.Utilidad.RecyclerViewItemClickListener;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecorridoActualFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RecorridoActualFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private RecorridoActualAdapter mRecyclerAdapter;
    private ArrayList<RecorridoActualItem> mData = new ArrayList<>();

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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recorrido_actual_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), linearLayoutManager.getOrientation()));

        //Se obtiene la info de nuestro Realm
        RealmResults<Task> query = mRealm.where(Task.class).findAll();

        for (int i = 0; i < query.size(); i++) {
            Task task = query.get(i);
            mData.add(new RecorridoActualItem(task.get_id(), task.getAcademyHour(), task.getRoom()));
        }

        mRecyclerAdapter = new RecorridoActualAdapter(mContext, mData, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                long itemId = mData.get(position).getID();
                String horaFime = mData.get(position).getHoraFime();
                String salonFime = mData.get(position).getSalonFime();

                Toast.makeText(mContext, "ID: " + itemId + " Hora: " + horaFime + " Salon: " + salonFime, Toast.LENGTH_SHORT).show();
                //Trigger de onRecorridoActualItemSelected en RecorridoMainActivity para comunicar con
                //DatosVisitaFragment
                if (mListener != null) {
                    mListener.onRecorridoActualItemSelected(itemId, horaFime, salonFime);
                }
            }
        });
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }
}
