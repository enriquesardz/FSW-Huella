package com.fime.fsw.huella.huella.API.Deserializadores;

import com.fime.fsw.huella.huella.Data.Modelos.Route;
import com.fime.fsw.huella.huella.Data.Modelos.Task;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by Quique on 20/07/2017.
 */

public class RouteDeserializer implements JsonDeserializer<Route>{
    @Override
    public Route deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Task> tasks = new RealmList<>();
        RealmList<Task> rTasks = new RealmList<Task>(tasks.toArray(new Task[tasks.size()]));
        return null;
    }
}
