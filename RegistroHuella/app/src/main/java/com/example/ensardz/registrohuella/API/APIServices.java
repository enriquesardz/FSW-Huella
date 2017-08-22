package com.example.ensardz.registrohuella.API;

import android.media.session.MediaSession;

import com.example.ensardz.registrohuella.Datos.LoginUser;
import com.example.ensardz.registrohuella.Datos.Professor;
import com.example.ensardz.registrohuella.Datos.TokenResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by ensardz on 18/08/2017.
 */

public interface APIServices {
    @POST("auth/loginById")
    Call<TokenResponse> authGetToken(@Body LoginUser loginUser);

    @GET("professor")
    Call<List<Professor>> downloadProfessors(@Header("Authorization") String token);

    @POST("upload/professor")
    Call<List<Professor>> uploadProfessors(@Header("Authorization") String token, @Body Professor uploadProfessors);
}
