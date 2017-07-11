package com.fime.fsw.huella.huella.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fime.fsw.huella.huella.Barcode.BarcodeReaderActivity;
import com.fime.fsw.huella.huella.Data.Modelos.Task;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;

import io.realm.Realm;


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
    private SesionAplicacion mSesion;

    public DatosVisitaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_datos_visita, container, false);

        mContext = getContext();
        mBundle = this.getArguments();
        mRealm = Realm.getDefaultInstance();
        mSesion = new SesionAplicacion(mContext);

        initComponentes(view);

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    //Este metodo permite la comunicacion entre este Fragment y la actividad host, y por ende,
    //otros Fragments tambien.
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCodigoBarrasFragmentInteraction(Uri uri);
    }

    private void initComponentes(View view) {
        infoContainer = view.findViewById(R.id.informacion_container);
        tvMaestro = (TextView) view.findViewById(R.id.maestro_textview);
        tvHoraFime = (TextView) view.findViewById(R.id.hora_fime_textview);
        tvSalonFime = (TextView) view.findViewById(R.id.salon_fime_textview);
        tvMateria = (TextView) view.findViewById(R.id.materia_textview);
        btnEscanner = (ImageButton) view.findViewById(R.id.escaner_salon_button);

        infoContainer.setVisibility(View.INVISIBLE);

        //Valor default del itemid, con intencion de que si el Bundle no regresa un id,
        //se pueda validar.
        final long itemid = mBundle.getLong(Task._ID_KEY, -1);
        Task task;

        //Si el bundle regreso un id, entonces actualiza la UI con datos del Task, y
        //hace visible el contenedor.
        if (itemid != -1) {
            task = getTaskConId(itemid);
            cargarDatosTask(task);
        }

        if (itemid >= mSesion.getCurrentItemLista()){
            btnEscanner.setVisibility(View.VISIBLE);
        }else {
            btnEscanner.setVisibility(View.INVISIBLE);
        }
        //Inicia la actividad de lector de codigo de barras
        btnEscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemid != -1) {
                    //Si mBundle regreso un id, entonces se puede iniciar la actividad del Scanner con un Task id,
                    //y si no, el boton no hace nada.
                    Intent intent = new Intent(mContext, BarcodeReaderActivity.class);
                    intent.putExtra(Task._ID_KEY, itemid);
                    startActivity(intent);
                }
            }
        });
    }

    public Task getTaskConId(long id) {
        return mRealm.where(Task.class).equalTo(Task._ID_KEY, id).findFirst();
    }

    public void cargarDatosTask(Task task){
        tvMaestro.setText(getResources().getString(R.string.cbarra_maestro, task.getName(), task.getFullName()));
        tvHoraFime.setText(getResources().getString(R.string.cbarra_hora, task.getAcademyHour()));
        tvSalonFime.setText(getResources().getString(R.string.cbarra_salon, task.getRoom()));
        tvMateria.setText(getResources().getString(R.string.cbarra_materia, task.getAssignment()));
        infoContainer.setVisibility(View.VISIBLE);
    }

}
