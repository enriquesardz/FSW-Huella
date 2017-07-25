package com.fime.fsw.huella.huella.Activities.Fragments;

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

import com.fime.fsw.huella.huella.Activities.Barcode.BarcodeReaderActivity;
import com.fime.fsw.huella.huella.Data.Modelos.Assignment;
import com.fime.fsw.huella.huella.Data.Modelos.Owner;
import com.fime.fsw.huella.huella.Data.Modelos.Room;
import com.fime.fsw.huella.huella.Data.Modelos.Route;
import com.fime.fsw.huella.huella.Data.Modelos.Task;
import com.fime.fsw.huella.huella.Data.Provider.RealmProvider;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;

import java.util.HashMap;

import io.realm.Realm;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DatosVisitaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DatosVisitaFragment extends Fragment {

    public static final String TAG = APP_TAG + DatosVisitaFragment.class.getSimpleName();

    private boolean hayDatos = false;
    private long taskSequence = -1;

    private OnFragmentInteractionListener mListener;

    private ImageButton btnEscanner;
    private TextView tvMaestro;
    private TextView tvHoraFime;
    private TextView tvSalonFime;
    private TextView tvMateria;
    private View infoContainer;
    private View emptyState;

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
        void onCodigoBarrasFragmentInteraction(Uri uri);
    }

    private void initComponentes(View view) {
        infoContainer = view.findViewById(R.id.informacion_container);
        emptyState = view.findViewById(R.id.empty_state);
        tvMaestro = (TextView) view.findViewById(R.id.maestro_textview);
        tvHoraFime = (TextView) view.findViewById(R.id.hora_fime_textview);
        tvSalonFime = (TextView) view.findViewById(R.id.salon_fime_textview);
        tvMateria = (TextView) view.findViewById(R.id.materia_textview);
        btnEscanner = (ImageButton) view.findViewById(R.id.escaner_salon_button);


        //Valor default del itemid, con intencion de que si el Bundle no regresa un id,
        //se pueda validar.
        final String itemid = mBundle.getString(Task._ID_FIELD, null);
        final Task task = RealmProvider.getTaskById(mRealm,itemid);
        final Route route = RealmProvider.getRouteByTaskId(mRealm, itemid);

        final int currentTask;

        if(route != null){
            currentTask = route.getCurrentTask();
        } else{
            currentTask = -1;
        }

        //Si el bundle regreso un id, entonces actualiza la UI con datos del Task, y
        //hace visible el contenedor.
        if (itemid != null && task != null){
            taskSequence = task.getSequence();
            cargarDatosTask(task);
        }

        if(hayDatos){
            showInfoContainer();
        }
        else{
            showEmptyState();
        }

        if (taskSequence >= currentTask && taskSequence != -1) {
            btnEscanner.setVisibility(View.VISIBLE);
        }else{
            btnEscanner.setVisibility(View.GONE);
        }

        //Inicia la actividad de lector de codigo de barras
        btnEscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((itemid != null && taskSequence >= currentTask)) {
                    //Si mBundle regreso un id, entonces se puede iniciar la actividad del Scanner con un Task id,
                    //y si no, el boton no hace nada.
                    RealmProvider.setStartedAtCheckout(mRealm, task);
                    Intent intent = new Intent(mContext, BarcodeReaderActivity.class);
                    intent.putExtra(Task._ID_FIELD, itemid);
                    startActivity(intent);
                }
            }
        });
    }

    public void cargarDatosTask(Task task) {
        HashMap<String,String> data = RealmProvider.getAllDataAsStringByTask(mRealm,task);

        tvMaestro.setText(getResources().getString(R.string.cbarra_maestro, data.get(Owner.NAME_KEY), data.get(Owner.LAST_NAME_KEY)));
        tvHoraFime.setText(getResources().getString(R.string.cbarra_hora, data.get(Route.ACADEMY_HOUR_KEY)));
        tvSalonFime.setText(getResources().getString(R.string.cbarra_salon, data.get(Room.ROOM_NUMBER_KEY)));
        tvMateria.setText(getResources().getString(R.string.cbarra_materia, data.get(Assignment.NAME_KEY)));

        hayDatos = true;
    }

    public void showInfoContainer(){
        infoContainer.setVisibility(View.VISIBLE);
        emptyState.setVisibility(View.GONE);
    }

    public void showEmptyState(){
        emptyState.setVisibility(View.VISIBLE);
        infoContainer.setVisibility(View.GONE);
    }

}
