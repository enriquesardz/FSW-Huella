package com.fime.fsw.huella.huella.Activities;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class HuellaApplication extends Application {

    public static final String REALM_NAME = "Huella.realm";
    public static final String APP_TAG = "CODO: ";

    @Override
    public void onCreate() {
        super.onCreate();
        Context mContext = getApplicationContext();
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .name(REALM_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);

        Realm realm = Realm.getInstance(config);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build()
        );
        RealmInspectorModulesProvider.builder(this)
                .withFolder(getCacheDir())
                .withMetaTables()
                .withDescendingOrder()
                .withLimit(1000)
                .databaseNamePattern(Pattern.compile(".+.realm"))
                .build();

        realm.close();

    }
}

