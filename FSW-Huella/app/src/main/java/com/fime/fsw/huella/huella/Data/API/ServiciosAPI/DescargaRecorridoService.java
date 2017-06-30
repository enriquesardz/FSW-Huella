package com.fime.fsw.huella.huella.Data.API.ServiciosAPI;


import com.fime.fsw.huella.huella.Data.API.Modelos.Task;

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
