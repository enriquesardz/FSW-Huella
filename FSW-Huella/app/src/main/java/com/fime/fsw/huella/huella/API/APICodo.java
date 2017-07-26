package com.fime.fsw.huella.huella.API;

import com.fime.fsw.huella.huella.API.Deserializadores.RouteDeserializer;
import com.fime.fsw.huella.huella.API.Deserializadores.RoutesListDeserializer;
import com.fime.fsw.huella.huella.Data.Modelos.Route;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
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
    public static final String BASE_URL = "https://nyx-codo.herokuapp.com/";
    public static OkHttpClient client = new OkHttpClient();

    //Esta ruta esta firmada por la API entonces puede hacer requests.
    public static Retrofit signedSingleRoute() {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Route.class, new RouteDeserializer());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build();

        return retrofit;
    }

    public static Retrofit signedRouteList() {
        GsonBuilder builder = new GsonBuilder();
        Type listType = new TypeToken<List<Route>>() {
        }.getType();
        builder.registerTypeAdapter(listType, new RoutesListDeserializer());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build();
        return retrofit;
    }

    public static Retrofit requestToken() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
