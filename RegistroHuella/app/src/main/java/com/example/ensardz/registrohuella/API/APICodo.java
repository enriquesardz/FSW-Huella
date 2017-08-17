package com.example.ensardz.registrohuella.API;

import com.example.ensardz.registrohuella.API.Deserializadores.ProfessorDeserializer;
import com.example.ensardz.registrohuella.Datos.Professor;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ensardz on 17/08/2017.
 */

public class APICodo {

    //Daniel
    //https://young-escarpment-48238.herokuapp.com/routes

    //Abraham
    //https://api.myjson.com/bins/w20k7

    public static final String BASE_URL = "https://nyx-codo.herokuapp.com/";
    public static OkHttpClient client = new OkHttpClient();

    //Esta ruta esta firmada por la API entonces puede hacer requests.
    public static Retrofit signedAllProfessors() {
        GsonBuilder builder = new GsonBuilder();

        Type listType = new TypeToken<List<Professor>>() {
        }.getType();

        builder.registerTypeAdapter(listType, new ProfessorDeserializer());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build();

        return retrofit;
    }

    public static Retrofit requestToken() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

}