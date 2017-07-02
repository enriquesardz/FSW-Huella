package com.fime.fsw.huella.huella.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fime.fsw.huella.huella.Data.API.Modelos.Task;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.RecorridoMainActivity;
import com.fime.fsw.huella.huella.Barcode.BarcodeReaderActivity;

import org.w3c.dom.Text;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DatosVisitaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DatosVisitaFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ImageButton btnEscanner;
    private TextView tvMaestro;
    private TextView tvHoraFime;
    private TextView tvSalonFime;
    private TextView tvMateria;
    private View infoContainer;

    private Bundle mBundle;

    private Context mContext;

    private Realm mRealm;

    private long itemid = -1;
    private String codigoBarras;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = this.getArguments();
    }

    public DatosVisitaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_datos_visita, container, false);

        mContext = getContext();

        mRealm = Realm.getDefaultInstance();

        initComponentes(view);

        infoContainer.setVisibility(View.INVISIBLE);

        //Si el bundle tiene contenido entonces cambian los valores de los textviews.
        if (mBundle.size() > 0) {
            itemid = -1;
            itemid = mBundle.getLong(RecorridoMainActivity.KEY_ID_RECORRIDO_ITEM);
            //TODO:Esto posiblmente se puede retirar
            String horaFime = mBundle.getString(RecorridoMainActivity.KEY_HORA_FIME);
            String salonFime = mBundle.getString(RecorridoMainActivity.KEY_SALON_FIME);
            if (itemid != -1) {
                //Trae un task usando el _id que se le pasa cuando le da click al recorrido actual.
                Task task = mRealm.where(Task.class).equalTo("_id", itemid).findFirst();
                tvMaestro.setText("Maestro: " + task.getName() + " " + task.getFullName());
                tvHoraFime.setText("Hora: " + task.getAcademyHour());
                tvSalonFime.setText("Salon: " + task.getRoom());
                tvMateria.setText("Materia: " + task.getAssignment());
                //Se guarda el codigo de barras de una vez.
                codigoBarras = task.getBarcode();
                infoContainer.setVisibility(View.VISIBLE);
            }
        }

        //Inicia la actividad de lector de codigo de barras
        btnEscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemid != -1) {
                    Intent intent = new Intent(mContext, BarcodeReaderActivity.class);
                    //Se pasa el _id y el codigo de barras a la actividad codigo de barras.
                    intent.putExtra("_id", itemid);
                    intent.putExtra("barcode", codigoBarras);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    private void initComponentes(View view) {
        infoContainer = view.findViewById(R.id.informacion_container);
        tvMaestro = (TextView) view.findViewById(R.id.maestro_textview);
        tvHoraFime = (TextView) view.findViewById(R.id.hora_fime_textview);
        tvSalonFime = (TextView) view.findViewById(R.id.salon_fime_textview);
        tvMateria = (TextView) view.findViewById(R.id.materia_textview);
        btnEscanner = (ImageButton) view.findViewById(R.id.escaner_salon_button);
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
