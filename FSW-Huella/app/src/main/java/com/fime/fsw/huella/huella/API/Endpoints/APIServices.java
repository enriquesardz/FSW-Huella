package com.fime.fsw.huella.huella.API.Endpoints;


import com.fime.fsw.huella.huella.Data.Modelos.LoginUser;
import com.fime.fsw.huella.huella.Data.Modelos.Route;
import com.fime.fsw.huella.huella.Data.Modelos.TokenResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Quique on 28/06/2017.
 */

public interface APIServices {

    //Gets token from server
    @POST("auth/loginById")
    Call<TokenResponse> authGetToken(@Body LoginUser loginUser);

    //Downloads Route list
    @GET("routes/")
    Call<List<Route>> descargaRutas(@Header("Authorization") String token);

    //Gets single Route by ID
    @GET("routes/{routeId}")
    Call<Route> descargaRecorrido(@Path("routeId") String routeId, @Header("Authorization") String token);

    @GET("test/routes")
    Call<List<Route>> descargaAllRoutesWTasks(@Header("Authorization") String token);

}
