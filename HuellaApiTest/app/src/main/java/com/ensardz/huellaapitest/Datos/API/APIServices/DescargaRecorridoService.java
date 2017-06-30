package com.ensardz.huellaapitest.Datos.API.APIServices;

import com.ensardz.huellaapitest.Datos.API.Models.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Quique on 28/06/2017.
 */

public interface DescargaRecorridoService {

    @GET("routes/")
    Call<List<Task>> descargaRecorrido();
}
