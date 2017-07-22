package com.fime.fsw.huella.huella.API.ServiciosAPI;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ensardz on 21/07/2017.
 */

public interface AuthService {
    @GET("")
    Call<String> authGetToken();
}
