package com.ensardz.huellaapitest.Datos.API;

import com.ensardz.huellaapitest.Datos.API.Deserializers.TaskDeserializer;
import com.ensardz.huellaapitest.Datos.API.Models.Task;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Retrofit;

/**
 * Created by Quique on 29/06/2017.
 */

public class API {

    //https://young-escarpment-48238.herokuapp.com/routes
    public static final String BASE_URL = "https://young-escarpment-48238.herokuapp.com/routes";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            Type listType = new TypeToken<List<Task>>(){}.getType();
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(listType, new TaskDeserializer());

            List<Task> tasks = builder.fro
        }
        return retrofit;
    }
}
