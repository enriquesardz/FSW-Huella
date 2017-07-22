package com.fime.fsw.huella.huella.API.ServiciosAPI;


import com.fime.fsw.huella.huella.Data.Modelos.Route;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Quique on 28/06/2017.
 */

public interface DescargaRecorridosService {
    @GET("routes/596fe35c447b2a31100f82e6")
    Call<Route> descargaRecorrido();
}
