package com.fime.fsw.huella.huella;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fime.fsw.huella.huella.Data.API.Modelos.Task;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;


public class HuellaApplication extends Application {

    public static final String REALM_NAME = "Huella.realm";

    public static AtomicInteger TaskID = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();
        Context mContext = getApplicationContext();
        Realm.init(mContext);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .name(REALM_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        Realm realm = Realm.getInstance(config);
        TaskID = setAtomicId(realm, Task.class);
        realm.close();

    }

    private <T extends RealmObject> AtomicInteger setAtomicId(Realm realm, Class<T> anyClass) {
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size() > 0) ? new AtomicInteger(results.max("_id").intValue()) : new AtomicInteger();
    }
}

