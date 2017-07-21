package com.fime.fsw.huella.huella.API.ServiciosAPI;


import com.fime.fsw.huella.huella.Data.Modelos.Route;
import com.fime.fsw.huella.huella.Data.Modelos.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Quique on 28/06/2017.
 */

public interface DescargaRecorridosService {
    @GET("w20k7/")
    Call<Route> descargaRecorrido();
}
