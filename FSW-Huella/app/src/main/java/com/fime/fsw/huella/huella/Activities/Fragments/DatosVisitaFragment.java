package com.fime.fsw.huella.huella.Activities.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fime.fsw.huella.huella.Activities.Barcode.BarcodeReaderActivity;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Route;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Task;
import com.fime.fsw.huella.huella.Data.Provider.RealmProvider;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;

import java.util.HashMap;

import io.realm.Realm;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;


public class DatosVisitaFragment extends Fragment {

    public static final String TAG = APP_TAG + DatosVisitaFragment.class.getSimpleName();

    private boolean hayDatos = false;
    private long routeCurrentTaskSequence = -1;
    private String itemid;

    private OnFragmentInteractionListener mListener;

    private ImageButton btnEscanner;
    private TextView tvMaestro, tvSalonFime, tvMateria, tvPlan, tvCodigoBarra;
    private View infoContainer;
    private View emptyState;

    private Bundle mBundle;
    private Context mContext;
    private Realm mRealm;
    private SesionAplicacion mSesionApp;

    private Task mTask;
    private Route mRoute;

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
        mSesionApp = new SesionAplicacion(mContext);

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

    //Este metodo permite la comunicacion entre este Fragment y la actividad host, y por ende,
    //otros Fragments tambien.
    public interface OnFragmentInteractionListener {
        void onCodigoBarrasFragmentInteraction(Uri uri);
    }

    private void initComponentes(View view) {

        infoContainer = view.findViewById(R.id.informacion_container);
        emptyState = view.findViewById(R.id.empty_state);
        tvMaestro = (TextView) view.findViewById(R.id.maestro_textview);
        tvSalonFime = (TextView) view.findViewById(R.id.salon_fime_textview);
        tvMateria = (TextView) view.findViewById(R.id.materia_textview);
        tvPlan = (TextView) view.findViewById(R.id.plan_textview);
        tvCodigoBarra = (TextView) view.findViewById(R.id.codigo_barra_textview);
        btnEscanner = (ImageButton) view.findViewById(R.id.escaner_salon_button);


        //Valor default del itemid, con intencion de que si el Bundle no regresa un id,
        //se pueda validar.

        mRoute = RealmProvider.getRouteByRouteId(mRealm, mSesionApp.getCurrentRutaId());

        routeCurrentTaskSequence = mRoute.getCurrentTask();
        Log.d(TAG, "Current task sequence " + routeCurrentTaskSequence);

        mTask = mRoute.getTasks().where().equalTo(Task.SEQUENCE_FIELD, routeCurrentTaskSequence).findFirst();
        //Si el bundle regreso un id, entonces actualiza la UI con datos del Task, y
        //hace visible el contenedor.

        if (mTask != null) {
            cargarDatosTask(mTask);
            itemid = mTask.get_id();
        }

        //TODO: Falta validacion para que cuando se le de click a una tarea de la lista de tareas, este muestre su detalle sin el boton de scanner.
        if (routeCurrentTaskSequence == mRoute.getLastTask()) {
            btnEscanner.setVisibility(View.GONE);
            showEmptyState();
        }

        //Inicia la actividad de lector de codigo de barras
        btnEscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBarcodeActivity(mRoute, mTask, itemid);
            }
        });
    }

    public void cargarDatosTask(Task task) {
        HashMap<String, String> data = RealmProvider.getAllDataAsStringByTask(mRealm, task);

        tvMaestro.setText(getResources().getString(R.string.cbarra_maestro, data.get(Task.NOMBRE_EMPLEADO_KEY)));
        tvSalonFime.setText(getResources().getString(R.string.cbarra_salon, data.get(Task.SALON_ID_KEY)));
        tvMateria.setText(getResources().getString(R.string.cbarra_materia, data.get(Task.MATERIA_KEY)));
        tvPlan.setText(getResources().getString(R.string.cbarra_plan, data.get(Task.PLAN_ID_KEY)));
        tvCodigoBarra.setText(getResources().getString(R.string.cbarra_codigo_barra, data.get(Task.SALON_ID_KEY)));

    }

    public void startBarcodeActivity(Route route, Task task, String itemid) {
        int currentTask = route.getCurrentTask();
        if ((itemid != null && routeCurrentTaskSequence >= currentTask)) {
            //Si mBundle regreso un id, entonces se puede iniciar la actividad del Scanner con un Task id,
            //y si no, el boton no hace nada.
            Intent intent = new Intent(mContext, BarcodeReaderActivity.class);
            intent.putExtra(Task._ID_FIELD, itemid);
            startActivity(intent);
            getActivity().finish();
        }
    }

    public void showInfoContainer() {
        infoContainer.setVisibility(View.VISIBLE);
        emptyState.setVisibility(View.GONE);
    }

    public void showEmptyState() {
        emptyState.setVisibility(View.VISIBLE);
        infoContainer.setVisibility(View.GONE);
    }

}
