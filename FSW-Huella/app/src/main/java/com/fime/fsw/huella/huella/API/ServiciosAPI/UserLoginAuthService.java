package com.fime.fsw.huella.huella.API.ServiciosAPI;

import com.fime.fsw.huella.huella.Data.Modelos.LoginUser;
import com.fime.fsw.huella.huella.Data.Modelos.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ensardz on 21/07/2017.
 */

public interface UserLoginAuthService {
    @POST("auth/loginById")
    Call<TokenResponse> authGetToken(@Body LoginUser loginUser);
}
