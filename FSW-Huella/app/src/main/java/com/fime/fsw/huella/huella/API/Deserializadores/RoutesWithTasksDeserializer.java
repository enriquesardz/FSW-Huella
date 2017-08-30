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
 * Created by ensardz on 27/07/2017.
 *
 * Returns a list of Routes, each with a list of Tasks appended to them.
 */

public class RoutesWithTasksDeserializer implements JsonDeserializer<List<Route>> {
    @Override
    public List<Route> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray routeArray = json.getAsJsonObject().get("data").getAsJsonArray();

        List<Route> routes = new ArrayList<>();

        int position = 0;
        for (JsonElement e : routeArray){
            JsonObject route = e.getAsJsonObject();
//            String _id = route.get("_id").getAsString();

            String _id = route.get("tasks").getAsJsonArray().get(0).getAsJsonObject().get("route").getAsString();
            String day = route.get("day").getAsString();
            String academyHour = route.get("academyHour").getAsString();
            //String area = route.get("area").getAsString();
            String assignedTo = "";//route.get("assignedTo").getAsString();
            int tasksCount = route.get("tasksCounts").getAsInt();

            JsonArray jsonTaskArray = route.get("tasks").getAsJsonArray();
            List<Task> tasks = new ArrayList<>();

            //Utiliza el deserializador de Tasks para regresar una lista de Tasks
            if(jsonTaskArray != null){
                for (JsonElement taskObject : jsonTaskArray){
                    Task task = new TaskDeserializer(position).deserialize(taskObject,typeOfT,context);
                    tasks.add(task);
                    position++;
                }
            }

            // Sequence if API returns sequence
//            int currentTask = tasks.get(0).getSequence();
//            int lastTask = tasks.get(tasks.size()-1).getSequence() + 1;

            //Sequence if API doesnt return sequence
            int currentTask = 0;
            int lastTask = tasks.size() - 1;

            RealmList<Task> rTasks = new RealmList<Task>(tasks.toArray(new Task[tasks.size()]));

            routes.add(Route.createAll(_id,day,academyHour,assignedTo,tasksCount,rTasks,currentTask,lastTask));
        }

        return routes;
    }
}