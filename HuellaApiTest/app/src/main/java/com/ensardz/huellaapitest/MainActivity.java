package com.ensardz.huellaapitest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ensardz.huellaapitest.Database.HuellaContract;
import com.ensardz.huellaapitest.Database.HuellaDBHelper;
import com.ensardz.huellaapitest.Datos.API.API;
import com.ensardz.huellaapitest.Datos.API.Models.Task;
import com.ensardz.huellaapitest.Datos.API.APIServices.DescargaRecorridoService;

import java.security.MessageDigest;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "https://young-escarpment-48238.herokuapp.com/";

    private Context mContext;

    // https://young-escarpment-48238.herokuapp.com/routes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

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

    }

    public void saveTasksToDB(List<Task> tasksList) {
        int i;
        HuellaDBHelper helper = new HuellaDBHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values;

        try {
            for (i = 0; i < tasksList.size(); i++) {
                values = new ContentValues();
                Task task = tasksList.get(i);

                values.put(HuellaContract.HuellaEntry.COLUMNA_ROOM, task.getRoom());
                values.put(HuellaContract.HuellaEntry.COLUMNA_ASSIGNMENT, task.getAssignment());
                values.put(HuellaContract.HuellaEntry.COLUMNA_ACADEMY_HOUR, task.getAcademyHour());
                values.put(HuellaContract.HuellaEntry.COLUMNA_BARCODE, task.getBarcode());
                values.put(HuellaContract.HuellaEntry.COLUMNA_EMPLOYEE_NUMBER, task.getEmployeeNumber());
                values.put(HuellaContract.HuellaEntry.COLUMNA_EMPLOYEE_NAME, task.getName());
                values.put(HuellaContract.HuellaEntry.COLUMNA_EMPLOYEE_FULLNAME, task.getFullName());
                values.put(HuellaContract.HuellaEntry.COLUMNA_HEXCODE, task.getHexCode());

                long newRowId = db.insert(HuellaContract.HuellaEntry.TASK_TABLA_NOMBRE, null, values);
                Log.i(TAG, String.valueOf(newRowId) + " id has been inserted into TABLE Task");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error saving data to database");
        } finally {
            if (helper != null) {
                helper.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }
}
