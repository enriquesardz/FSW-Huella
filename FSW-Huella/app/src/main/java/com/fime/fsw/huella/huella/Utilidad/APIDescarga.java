package com.fime.fsw.huella.huella.Utilidad;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fime.fsw.huella.huella.API.APICodo;
import com.fime.fsw.huella.huella.API.ServiciosAPI.DescargaRecorridoService;
import com.fime.fsw.huella.huella.Activities.HuellaApplication;
import com.fime.fsw.huella.huella.Activities.RecorridoMain.RecorridoMainActivity;
import com.fime.fsw.huella.huella.Data.Modelos.Task;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ensardz on 17/07/2017.
 */

public class APIDescarga {

    public static final String TAG = HuellaApplication.APP_TAG + APIDescarga.class.getSimpleName();

    private Context mContext;
    private Realm mRealm;
    private SesionAplicacion mSesionApp;

    public APIDescarga(Context mContext, Realm mRealm, SesionAplicacion mSesionApp) {
        this.mContext = mContext;
        this.mRealm = mRealm;
        this.mSesionApp = mSesionApp;
    }

    public void startDescarga(){
        DescargaRecorridoService service = APICodo.getApi().create(DescargaRecorridoService.class);
        Call<List<Task>> call = service.descargaRecorrido();

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                //Se ejecuta si el webservice regresa algo, la respuesta
                //es una lista de Tasks, entonces la respuesta se guarda en una Lista de tipo Tasks

                final List<Task> tasks = response.body();

                //Se guardan los datos a nuestro Realm
                guardarRespuestaARealm(tasks);
                //Despues de guardar al Realm, se setea el primer item de la lista y el final.
                setInitialAndFinalTask();

                //Se inicia sesion de descarga
                mSesionApp.crearSesionDescarga();


            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                //No se descargo nada
            }
        });



    }

    public void guardarRespuestaARealm(final List<Task> tasks){

        //Se recorre la lista y se guarda cada objeto Task a Realm
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(Task task : tasks){
                    Task realmTask = realm.copyToRealmOrUpdate(task);
                    Log.i(TAG, realmTask.toString());
                }
            }
        });
    }

    public void setInitialAndFinalTask(){
        mSesionApp.setCurrentItemLista(mRealm.where(Task.class).findFirst().get_id());
        mSesionApp.setLastItemLista(mRealm.where(Task.class).max(Task._ID_KEY).longValue());
    }
}
