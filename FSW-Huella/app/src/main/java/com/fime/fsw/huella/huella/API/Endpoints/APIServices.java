package com.fime.fsw.huella.huella.API.Endpoints;


import com.fime.fsw.huella.huella.Data.Modelos.LoginUser;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Grupo;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Prefecto;
import com.fime.fsw.huella.huella.Data.Modelos.TokenResponse;
import com.fime.fsw.huella.huella.Data.Modelos.UploadCheckouts;
import com.fime.fsw.huella.huella.Data.Modelos.UploadResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Quique on 28/06/2017.
 */

public interface APIServices {

    //Gets token from server
    @POST("auth/loginById")
    Call<TokenResponse> authGetToken(@Body LoginUser loginUser);

    @FormUrlEncoded
    @POST("routes/")
    Call<List<Grupo>> downloadGroups(@Field("date")String date);

    @GET("prefectos/")
    Call<List<Prefecto>> downloadPrefectos();

    @POST("upload/checkouts")
    Call<UploadResponse> subirCheckoutsRuta(@Header("Authorization") String token, @Body UploadCheckouts uploadCheckouts);

}
