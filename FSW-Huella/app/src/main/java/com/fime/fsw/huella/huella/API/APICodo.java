package com.fime.fsw.huella.huella.API;

import com.fime.fsw.huella.huella.API.Deserializadores.TaskDeserializer;
import com.fime.fsw.huella.huella.Data.Modelos.Task;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ensardz on 28/06/2017.
 */

public class APICodo {

    //Daniel
    //https://young-escarpment-48238.herokuapp.com/routes
    //Abraham
    //https://api.myjson.com/bins/w20k7
    public static final String BASE_URL = "https://api.myjson.com/bins/";
    private static Retrofit retrofit = null;

    //Esta ruta esta firmada por la API entonces puede hacer requests.
    public static Retrofit signedRoute(){
        if(retrofit == null){
            Type listType = new TypeToken<List<Task>>(){}.getType();

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(listType, new TaskDeserializer());

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))
                    .build();
        }
        return retrofit;
    }
}
