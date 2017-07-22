package com.fime.fsw.huella.huella.API.ServiciosAPI;


import com.fime.fsw.huella.huella.Data.Modelos.Route;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by Quique on 28/06/2017.
 */

public interface DescargaRecorridosService {
    @GET("routes/{routeId}")
    Call<Route> descargaRecorrido(@Path("routeId") String routeId, @Header("Authorization") String token);
}
