package com.fime.fsw.huella.huella.API.Deserializadores;

import com.fime.fsw.huella.huella.Data.Modelos.Route;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ensardz on 21/07/2017.
 */

public class RoutesListDeserializer implements JsonDeserializer<List<Route>> {
    @Override
    public List<Route> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray routeArray = json.getAsJsonObject().get("data").getAsJsonArray();

        List<Route> routes = new ArrayList<>();

        for (JsonElement e : routeArray){
            JsonObject route = e.getAsJsonObject();
            String _id = route.get("_id").getAsString();
            String day = route.get("day").getAsString();
            String academyHour = route.get("academyHour").getAsString();
            String assignedTo = route.get("assignedTo").getAsString();
            String createdAt = route.get("createdAt").getAsString();
            int tasksCount = route.get("tasksCounts").getAsInt();
            routes.add(Route.create(_id,day,academyHour,assignedTo,createdAt,tasksCount,null));
        }

        return routes;
    }
}
