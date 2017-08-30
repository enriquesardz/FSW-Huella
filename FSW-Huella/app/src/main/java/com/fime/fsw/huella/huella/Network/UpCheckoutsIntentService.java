package com.fime.fsw.huella.huella.Network;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.fime.fsw.huella.huella.Data.Provider.RealmProvider;

import io.realm.Realm;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

/**
 * Created by quiqu on 30/08/2017.
 */

public class UpCheckoutsIntentService extends IntentService {

    private static final String TAG = APP_TAG + UpCheckoutsIntentService.class.getSimpleName();

    private static final String SERVICE_NAME = "UpCheckoutsIntentService";

    private Realm mRealm = Realm.getDefaultInstance();

    public UpCheckoutsIntentService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "UpCHeckouts service running...");
        upload();
    }

    public void upload(){
        Toast.makeText(this, "Uploading checkouts....", Toast.LENGTH_SHORT).show();
        //RealmProvider.getRouteCheckoutsFromRealm()
    }
}
