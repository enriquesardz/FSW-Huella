package com.fime.fsw.huella.huella.API.ServiciosAPI;

import com.fime.fsw.huella.huella.Data.Modelos.Route;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ensardz on 21/07/2017.
 */

public interface DescargaRutaService {
    @GET("routes/{prefecto_id}")
    Call<List<Route>> descargaRutas(@Path("prefecto_id") String prefectoId);
}
