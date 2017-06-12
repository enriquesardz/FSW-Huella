package com.fime.fsw.huella.huella.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.objetos.RecorridoActualItem;
import com.fime.fsw.huella.huella.utilidad.RecorridoActualAdapter;
import com.fime.fsw.huella.huella.utilidad.RecyclerViewItemClickListener;

import java.util.ArrayList;


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

    public RecorridoActualFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recorrido_actual, container, false);
        mContext = getContext();
        initComponentes(view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDatosMaestroFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDatosMaestroFragmentInteraction(Uri uri);
    }

    private void initComponentes(View view){
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recorrido_actual_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        //Data prueba
        mData.add(new RecorridoActualItem(1,"M2","2030"));
        mData.add(new RecorridoActualItem(2,"N3","4200"));

        mRecyclerAdapter = new RecorridoActualAdapter(mContext, mData, new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(mContext, "Posicion: "+position, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }
}
