package com.fime.fsw.huella.huella.Network;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fime.fsw.huella.huella.API.APICallbackListener;
import com.fime.fsw.huella.huella.API.APIManager;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Task;
import com.fime.fsw.huella.huella.Data.Modelos.upload_checkouts.UploadCheckouts;
import com.fime.fsw.huella.huella.Data.Modelos.UploadResponse;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

/**
 * Created by quiqu on 30/08/2017.
 * Este esrvicio se lanza cuando se detecta una conexion a Internet, intentará entonces subir las Tasks de
 * manera asyncrona. Si no puede subir, simplemente significa que no hay buena conexion a Internet, por lo que
 * no se debe de hacer nada, caso contrario, se actualizan los datos en la base de datos SOLAMENTE despues de
 * recibir una respuesta positiva del lado de la API.
 */

public class UpCheckoutsIntentService extends IntentService implements APICallbackListener<UploadResponse>{

    private static final String TAG = APP_TAG + UpCheckoutsIntentService.class.getSimpleName();

    private static final String SERVICE_NAME = "UpCheckoutsIntentService";

    private Realm mRealm = Realm.getDefaultInstance();

    public UpCheckoutsIntentService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "UpCeckouts service running...");
        upload();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    /**
     * Este metodo inicia la subida, primero se obtienen las Tasks que se van a subir y despues
     * se le pasa al metodo auxiliar para comunicarnos a la API y que se puedan subir esas Tasks
     * terminadas.
     */
    public void upload(){
        RealmResults<Task> doneTasks = mRealm.where(Task.class)
                .notEqualTo(Task.TASK_STATE_FIELD, Task.STATE_NO_HA_PASADO)
                .equalTo(Task.IS_UPLOADED_FIELD, false)
                .findAllSorted(Task.ROUTE_ID_FIELD);

        if (doneTasks.isEmpty()) {
            Log.d(TAG, "No hay tareas por subir");
            return;
        }

        //Si se llega hasta este punto significa que hay rutas terminadas sin subir.

        //Convertimos las Tasks de Realm a objetos normales tipo Task.
        List<Task> upDoneTasks = mRealm.copyFromRealm(doneTasks);

        //El formato que se le pasa a la API es un poco diferente al que se maneja localmente,
        //por ello, tenemos que darle "formato" y crear un objeto que contenga todos los Tasks
        //a subir.
        UploadCheckouts upCheckouts = UploadCheckouts.create(upDoneTasks);

        //Ya que hicimos todo lo anterior ahora si, podemos subir.
        APIManager.getInstance().uploadCheckouts(upCheckouts, this);
    }

    /**
     * Callback que regresa una respuesta de la API con status SUCCESS
     * @param response Respuesta que regresa la API.
     */
    @Override
    public void response(UploadResponse response) {
        Log.d(TAG, "Success! Se han subido las Tasks");
    }

    /**
     * Callback que regresa un fail en caso de que no se hallan podido subir las tareas, aqui es donde
     * no deberiamos hacer nada... Pero con este callback se podria debugear.
     */
    @Override
    public void failure() {
        Log.d(TAG, "Ha ocurrido un error y no se pudieron subir las Tasks");
    }
}
