package com.fime.fsw.huella.huella.API.Deserializadores;

import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Route;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Task;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by Quique on 20/07/2017.
 *
 * Returns a single Route object, with a list of Tasks appended to it.
 *
 *
 *  ################### U N U S E D ##################
 *
 *
 */

public class RouteDeserializer implements JsonDeserializer<Route> {
    @Override
    public Route deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject routeObject = json.getAsJsonArray().get(0).getAsJsonObject();

        String _id = routeObject.get("_id").getAsString();
        String day = routeObject.get("day").getAsString();
        String academyHour = routeObject.get("academyHour").getAsString();
        String assignedTo = routeObject.get("assignedTo").getAsString();

        JsonArray jsonTaskArray = routeObject.get("tasks").getAsJsonArray();
        List<Task> tasks = new ArrayList<>();

        if (jsonTaskArray != null) {
            for (JsonElement taskObject : jsonTaskArray) {
                Task task = new TaskDeserializer(0).deserialize(taskObject, typeOfT, context);
                tasks.add(task);
            }
        }

        int currentTask = tasks.get(0).getSequence();
        int finalTask = tasks.get(tasks.size()-1).getSequence();

        RealmList<Task> rTasks = new RealmList<Task>(tasks.toArray(new Task[tasks.size()]));

        return Route.create(_id,day,academyHour,assignedTo,rTasks,currentTask,finalTask);
    }
}
