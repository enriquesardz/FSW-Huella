package com.ensardz.huellaapitest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ensardz.huellaapitest.Database.HuellaContract;
import com.ensardz.huellaapitest.Database.HuellaDBHelper;
import com.ensardz.huellaapitest.Database.RealmModels.RealmTask;
import com.ensardz.huellaapitest.Datos.API.API;
import com.ensardz.huellaapitest.Datos.API.Models.Task;
import com.ensardz.huellaapitest.Datos.API.APIServices.DescargaRecorridoService;

import java.security.MessageDigest;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "https://young-escarpment-48238.herokuapp.com/";

    private Context mContext;
    private Realm mRealm;

    // https://young-escarpment-48238.herokuapp.com/routes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        mRealm.init(mContext);
        mRealm = Realm.getDefaultInstance();

        DescargaRecorridoService service = API.getApi().create(DescargaRecorridoService.class);

        Call<List<Task>> call = service.descargaRecorrido();

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                List<Task> tasks = response.body();
                Log.i(TAG, tasks.toString());
                saveTasksToDB(tasks);
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Log.e(TAG, "Error downloading data");
            }
        });

        RealmQuery<RealmTask> query = mRealm.where(RealmTask.class);
        RealmResults<RealmTask> result = query.findAll();

        Log.i(TAG, result.toString());


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
    }

    public void saveTasksToDB(List<Task> tasksList) {
        int i;
        for (i = 0; i < tasksList.size(); i++) {
            Task task = tasksList.get(i);

            mRealm.beginTransaction();
            Task realmTask = mRealm.copyToRealm(task);
            mRealm.commitTransaction();

        }

    }
}
