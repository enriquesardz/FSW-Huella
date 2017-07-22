package com.fime.fsw.huella.huella.API.ServiciosAPI;

import com.fime.fsw.huella.huella.Data.Modelos.Route;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by ensardz on 21/07/2017.
 */

public interface DescargaRutaService {
    @GET("routes/")
    Call<List<Route>> descargaRutas(@Header("Authorization") String token);
}
