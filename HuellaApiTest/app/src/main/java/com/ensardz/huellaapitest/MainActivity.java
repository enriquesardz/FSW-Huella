package com.ensardz.huellaapitest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.ensardz.huellaapitest.Datos.Objetos.HuellaResponse;
import com.ensardz.huellaapitest.Datos.Servicios.DescargaRecorridoService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "https://young-escarpment-48238.herokuapp.com/";

    // https://young-escarpment-48238.herokuapp.com/routes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DescargaRecorridoService service = retrofit.create(DescargaRecorridoService.class);
        Call<HuellaResponse> call = service.descargaRecorrido();

        call.enqueue(new Callback<HuellaResponse>() {
            @Override
            public void onResponse(Call<HuellaResponse> call, Response<HuellaResponse> response) {
                HuellaResponse respusta = response.body();
                Log.i(TAG, "Exito");
            }

            @Override
            public void onFailure(Call<HuellaResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "fallo");
            }
        });

    }
}
