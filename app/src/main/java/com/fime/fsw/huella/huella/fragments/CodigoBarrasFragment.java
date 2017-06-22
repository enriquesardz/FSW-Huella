package com.fime.fsw.huella.huella.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.RecorridoMainActivity;
import com.fime.fsw.huella.huella.barcode.BarcodeReaderActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CodigoBarrasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CodigoBarrasFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ImageButton escanerButton;
    private TextView horaFimeTextview;
    private TextView salonFimeTextview;
    private View infoContainer;

    private Bundle mBundle;

    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = this.getArguments();
    }

    public CodigoBarrasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_codigo_barras, container, false);

        mContext = getContext();

        infoContainer = (View)view.findViewById(R.id.informacion_container);
        infoContainer.setVisibility(View.INVISIBLE);

        horaFimeTextview = (TextView)view.findViewById(R.id.hora_fime_textview);
        salonFimeTextview = (TextView)view.findViewById(R.id.salon_fime_textview);

        //Si el bundle tiene contenido entonces cambian los valores de los textviews.
        if(mBundle != null){
            long itemId = mBundle.getLong(RecorridoMainActivity.KEY_ID_RECORRIDO_ITEM);
            String horaFime = mBundle.getString(RecorridoMainActivity.KEY_HORA_FIME);
            horaFimeTextview.setText("Hora: " + horaFime);
            String salonFime = mBundle.getString(RecorridoMainActivity.KEY_SALON_FIME);
            salonFimeTextview.setText("Salon: " + salonFime);
            infoContainer.setVisibility(View.VISIBLE);
        }

        escanerButton = (ImageButton)view.findViewById(R.id.escaner_salon_button);


        //Toast cuando se le da click al boton de escanear.
        escanerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, BarcodeReaderActivity.class));
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onCodigoBarrasFragmentInteraction(uri);
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
        void onCodigoBarrasFragmentInteraction(Uri uri);
    }

}
