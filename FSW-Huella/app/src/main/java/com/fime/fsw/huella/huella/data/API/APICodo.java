package com.fime.fsw.huella.huella.data.API;

import com.fime.fsw.huella.huella.data.API.Modelos.HuellaResponse;
import com.google.gson.GsonBuilder;

import java.net.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ensardz on 28/06/2017.
 */

public class APICodo {
    // https://young-escarpment-48238.herokuapp.com/routes
    public static final String URL_BASE = "https://young-escarpment-48238.herokuapp.com/routes";
    private static Retrofit retrofit = null;

    public static Retrofit getApi(){
        if(retrofit == null){
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(HuellaResponse.class, new RutaDeserializer[]);

            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
