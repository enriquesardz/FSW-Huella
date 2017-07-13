package com.ensardz.huellaapitest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ensardz.huellaapitest.Datos.API.API;
import com.ensardz.huellaapitest.Datos.API.Models.Task;
import com.ensardz.huellaapitest.Datos.API.APIServices.DescargaRecorridoService;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "https://young-escarpment-48238.herokuapp.com/";

    private Context mContext;
    private Realm mRealm;

    private Button btnDescargar;

    // https://young-escarpment-48238.herokuapp.com/routes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        mRealm = Realm.getDefaultInstance();

        btnDescargar = (Button) findViewById(R.id.descargar_button);


        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTasksFromRealm();
                downloadFromWebService();
            }
        });


//        RealmQuery<RealmTask> query = mRealm.where(RealmTask.class);
//        RealmResults<RealmTask> result = query.findAll();

//        Log.i(TAG, result.toString());


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
    }

    public void downloadFromWebService(){
        DescargaRecorridoService service = API.getAllTasks().create(DescargaRecorridoService.class);
        Call<List<Task>> call = service.descargaRecorrido();

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                List<Task> tasks = response.body();

                Log.i(TAG, "Se descargo. LUL");

                saveToRealm(tasks);
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Log.e(TAG, "Error downloading data");
            }
        });
    }

    public void saveToRealm(List<Task> tasksList) {
        for (final Task task : tasksList) {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Task realmTask = mRealm.copyToRealmOrUpdate(task);
                    Log.i(TAG, task.toString());
                }
            });
        }

    }


    public void deleteTasksFromRealm(){
        final RealmResults<Task> tasks = mRealm.where(Task.class).findAll();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                tasks.deleteAllFromRealm();
            }
        });
    }
}
