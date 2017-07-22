package com.fime.fsw.huella.huella.API;

import com.fime.fsw.huella.huella.API.Deserializadores.RouteDeserializer;
import com.fime.fsw.huella.huella.API.Deserializadores.RoutesListDeserializer;
import com.fime.fsw.huella.huella.API.Deserializadores.TaskDeserializer;
import com.fime.fsw.huella.huella.Data.Modelos.Route;
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
    public static final String BASE_URL2 = "https://nyx-codo.herokuapp.com/";

    private static Retrofit retrofit = null;

    //Esta ruta esta firmada por la API entonces puede hacer requests.
    public static Retrofit signedRoute(){
        String tempUrl = "https://api.myjson.com/bins/";
//        if(retrofit == null){

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Route.class, new RouteDeserializer());

            retrofit = new Retrofit.Builder()
                    .baseUrl(tempUrl)
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))
                    .build();
//        }
        return retrofit;
    }

    public static Retrofit signedRoutes(){
        String tempUrl = "https://nyx-codo.herokuapp.com/";
//        if(retrofit == null){
            GsonBuilder builder = new GsonBuilder();
            Type listType = new TypeToken<List<Route>>(){}.getType();
            builder.registerTypeAdapter(listType, new RoutesListDeserializer());

            retrofit = new Retrofit.Builder()
                    .baseUrl(tempUrl)
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))
                    .build();
//        }
        return retrofit;
    }
}
