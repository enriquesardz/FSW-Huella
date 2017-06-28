package com.fime.fsw.huella.huella.data.services;

import com.fime.fsw.huella.huella.data.objetos.Target;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ensardz on 27/06/2017.
 */

public interface DescargaRutaServicio  {
    //    https://young-escarpment-48238.herokuapp.com/routes
    @GET("routes")
    Call<Target> getTarget();
}
