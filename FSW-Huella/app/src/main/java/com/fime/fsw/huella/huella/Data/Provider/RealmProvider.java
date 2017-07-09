package com.fime.fsw.huella.huella.Data.Provider;

import com.fime.fsw.huella.huella.Data.Modelos.Task;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Quique on 08/07/2017.
 */

public class RealmProvider{
    private Realm mRealm;

    public RealmProvider(){}

    public RealmProvider(Realm realm){
        mRealm = realm;
    }
    //CREATE
    public void saveTasksToRealm(Realm realm, final List<Task> tasks){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(Task task : tasks){
                   Task realmTask = realm.copyToRealmOrUpdate(task);
                }
            }
        });
    }

    //READ
    public RealmResults<Task> getAllTasks(Realm realm){
        return realm.where(Task.class).findAll();
    }
    //UPDATE

    //DELETE
    public void deleteAllTasks(Realm realm){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                getAllTasks(realm).deleteAllFromRealm();
            }
        });
    }
}
