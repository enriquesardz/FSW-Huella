package com.fime.fsw.huella.huella.Data.Provider;

import android.util.Log;

import com.fime.fsw.huella.huella.Data.Modelos.Route;
import com.fime.fsw.huella.huella.Data.Modelos.Task;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

/**
 * Created by Quique on 08/07/2017.
 */

public class RealmProvider{

    private final static String TAG = APP_TAG + RealmProvider.class.getSimpleName();

    private Realm mRealm;

    public RealmProvider(){}

    public RealmProvider(Realm realm){
        mRealm = realm;
    }
    //CREATE
    public static void saveTasksToRealm(Realm mRealm, final List<Task> tasks){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(Task task : tasks){
                   Task realmTask = realm.copyToRealmOrUpdate(task);
                }
            }
        });
    }

    public static void saveRouteToRealm(Realm mRealm, final Route route){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(route);
            }
        });
    }

    //READ
    public static RealmResults<Task> getAllTasks(Realm realm){
        return realm.where(Task.class).findAll();
    }

    public static OrderedRealmCollection<Task> getAllOrderedTasks(Realm mRealm){
        return mRealm.where(Task.class).findAllSorted(Task.SEQUENCE_KEY);
    }

    public static Task getTaskById(Realm mRealm, String _id){
        return mRealm.where(Task.class).equalTo(Task._ID_KEY, _id).findFirst();
    }

    public static Route getRoute(Realm mRealm){
        return mRealm.where(Route.class).findFirst();
    }

    public static Route getRouteByTaskId(Realm mRealm, String _id){
        return mRealm.where(Route.class).equalTo("tasks._id", _id).findFirst();
    }


    //UPDATE
    public static void setVisitAtCheckout(Realm mRealm, final Task task){
        final String timeInMillis = String.valueOf(System.currentTimeMillis());
        //Se actualiza el visitAt del Checkout del Task
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                task.getCheckout().setVisitAt(timeInMillis);
            }
        });
        Log.i(TAG, "Se agrego el visitAt al Checkout");
        Log.d(TAG, task.getCheckout().toString());
    }

    public static void setStartedAtCheckout(Realm mRealm, final Task task){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                task.getCheckout().setStartedAt(String.valueOf(System.currentTimeMillis()));
                Log.i(TAG, task.getCheckout().toString());
            }
        });
    }

    //El codigo de barras no estaba, se le pone falta al maestro.
    public static void setCheckoutTaskValuesNoBarcode(Realm mRealm, final Task task){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                task.setTaskState(Task.STATE_PASO_NO_VINO_MAESTRO);
                task.getCheckout().setVisitAt(String.valueOf(System.currentTimeMillis()));
                task.getCheckout().setSignedAt(String.valueOf(System.currentTimeMillis()));
                task.getCheckout().setFinishedAt(String.valueOf(System.currentTimeMillis()));

                Log.i(TAG, "No se encontro salon visitAt signedAt: " + task.getCheckout().toString());
            }
        });
    }

    //Se identifico la huella del maestro y si vino
    public static void setCheckoutsTaskValuesVinoMaestro(Realm mRealm, final Task task){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                task.setTaskState(Task.STATE_PASO_VINO_MAESTRO);
                task.getCheckout().setSignedAt(String.valueOf(System.currentTimeMillis()));
                task.getCheckout().setFinishedAt(String.valueOf(System.currentTimeMillis()));

                Log.i(TAG, "Vino maestro: " + task.getCheckout().toString());
            }
        });
    }

    //El maestro no estaba o no vino.
    public static void setCheckoutsTaskValuesNoVinoMaestro(Realm mRealm, final Task task) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                task.setTaskState(Task.STATE_PASO_VINO_MAESTRO);
                task.getCheckout().setSignedAt(String.valueOf(System.currentTimeMillis()));
                task.getCheckout().setFinishedAt(String.valueOf(System.currentTimeMillis()));

                Log.i(TAG, "No vino maestro: " + task.getCheckout().toString());
            }
        });
    }

    //DELETE
    public static void dropAllRealmTables(Realm mRealm){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    public void deleteAllTasks(Realm realm){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                getAllTasks(realm).deleteAllFromRealm();
            }
        });
    }
}
