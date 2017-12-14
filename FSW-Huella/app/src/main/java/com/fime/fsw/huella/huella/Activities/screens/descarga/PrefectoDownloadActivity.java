package com.fime.fsw.huella.huella.Activities.screens.descarga;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fime.fsw.huella.huella.API.APIManager;
import com.fime.fsw.huella.huella.BuildConfig;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Grupo;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Prefecto;
import com.fime.fsw.huella.huella.R;
import com.fime.fsw.huella.huella.Utilidad.SesionAplicacion;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

/**
 * Con esta actividad, el prefecto DIOS puede elegir que dia descargar, de esta forma
 * el prefecto podra preparar las descargas un dia anterior o descargar las del dia de hoy.
 * Aqui se descarga la tabla de Prefectos tambien, asi que el prefecto no podra iniciar sesion sin que antes el
 * prefecto DIOS descargue desde aqui.
 */

public class PrefectoDownloadActivity extends AppCompatActivity implements APIManager.onPrefectosGroupsDownload, RouteAndTaskGenerator.onRouteAndTaskGeneration {


    private Button btnDownloadRoutes;
    private RadioGroup rdioGroup;
    private View loadingState;


    private static final String TAG = APP_TAG + PrefectoDownloadActivity.class.getSimpleName();

    private Context mContext;
    private SesionAplicacion mSesionApp;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_download);

        mContext = PrefectoDownloadActivity.this;
        mSesionApp = new SesionAplicacion(mContext);
        mRealm = Realm.getDefaultInstance();

        initComponentes();
    }

    public void initComponentes() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        btnDownloadRoutes = (Button) findViewById(R.id.descargar_rutas);
        rdioGroup = (RadioGroup) findViewById(R.id.fecha_radio_group);
        loadingState = findViewById(R.id.loading_state);

        btnDownloadRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Primero confirma que este seguro el prefecto
                new AlertDialog.Builder(mContext)
                        .setMessage("¿Esta seguro de la fecha?")
                        .setPositiveButton("Si",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        startDownload();
                                    }
                                })
                        .setNegativeButton(getResources().getString(R.string.mrecorrido_no), null)
                        .show();
            }
        });
    }

    public void startDownload() {
        if (BuildConfig.DEBUG) {
            /* Si estamos en modo DEBUG, sacamos los Grupos y Prefectos de archivos JSON
             */
            loadingState.setVisibility(View.VISIBLE);
            RouteAndTaskGenerator.getInstance().offlineGroupsPrefectos(mContext, PrefectoDownloadActivity.this);
            return;
        }

        loadingState.setVisibility(View.VISIBLE);

        int HOY = 0;
        String date;
        //Obtiene la fecha dependiendo de la fecha que eligio

        int option = rdioGroup.getCheckedRadioButtonId();
        if (option < 0) {
            Toast.makeText(mContext, "No ha seleccionado una fecha.", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton optionBtn = (RadioButton) findViewById(option);
        if (option == HOY || TextUtils.equals(optionBtn.getText().toString().toLowerCase(), "hoy")) {
            date = getDate(0);
        } else {
            date = getDate(1);
        }

        APIManager.getInstance().downloadPrefectosGroups(date, PrefectoDownloadActivity.this);

    }

    /**
     * Funcion a la que se le pasa dias a agregar, regresa la fecha de hoy si se pasa 0
     *
     * @param daysToAdd Numero de dias a agregar
     * @return Fecha en tipo String formateada a yyyy-MM-dd
     */
    public String getDate(int daysToAdd) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        if (daysToAdd > 0) {
            cal.add(Calendar.DATE, daysToAdd);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return dateFormat.format(cal.getTime());
    }

    @Override
    public void onPrefectosGroupsDownloadSuccess(List<Grupo> grupos, List<Prefecto> prefectos) {
        //Ya nos regresan los grupos y los prefectos, ahora debemos de procesarlos y guardarlos a nuestro Realm
        RouteAndTaskGenerator.getInstance().generateRoutesAndTasks(prefectos, grupos, PrefectoDownloadActivity.this);
    }

    @Override
    public void onPrefectosGroupsDownloadFailure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingState.setVisibility(View.GONE);
                //ERROR!!!!!
                Toast.makeText(mContext, "No se han descargado las rutas.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRouteAndTaskGenerationSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingState.setVisibility(View.GONE);
                //SUCCESS!!!!
                Toast.makeText(mContext, "Se ha descargado exitosamente.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onRouteAndTaskGenerationFailure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingState.setVisibility(View.GONE);
                //ERROR!!!!!
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
