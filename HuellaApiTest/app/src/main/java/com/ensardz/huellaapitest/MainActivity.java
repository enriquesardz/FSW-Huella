package com.ensardz.huellaapitest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ensardz.huellaapitest.Datos.API.API;
import com.ensardz.huellaapitest.Datos.API.Models.Task;
import com.ensardz.huellaapitest.Datos.API.APIServices.DescargaRecorridoService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "https://young-escarpment-48238.herokuapp.com/";

    // https://young-escarpment-48238.herokuapp.com/routes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        DescargaRecorridoService service = API.getApi().create(DescargaRecorridoService.class);

        Call<List<Task>> call = service.descargaRecorrido();

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                List<Task> tasks = response.body();
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {

            }
        });

    }
}
