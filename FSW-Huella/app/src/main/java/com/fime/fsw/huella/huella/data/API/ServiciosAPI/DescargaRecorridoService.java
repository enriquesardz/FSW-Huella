package com.fime.fsw.huella.huella.data.API.ServiciosAPI;



import com.fime.fsw.huella.huella.data.API.Modelos.HuellaResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Quique on 28/06/2017.
 */

public interface DescargaRecorridoService {

    @GET("/routes")
    Call<HuellaResponse> descargaRecorrido();
}
