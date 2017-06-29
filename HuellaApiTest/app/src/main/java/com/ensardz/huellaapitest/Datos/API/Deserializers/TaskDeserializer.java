package com.ensardz.huellaapitest.Datos.API.Deserializers;

import com.ensardz.huellaapitest.Datos.API.Models.Task;
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
 * Created by Quique on 29/06/2017.
 */

public class TaskDeserializer implements JsonDeserializer<List<Task>>{
    @Override
    public List<Task> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray m = json.getAsJsonObject().getAsJsonArray("m");
        List<Task> tasks = new ArrayList<Task>();
        for(JsonElement e : m){
//            this.id = id;
//            this.room = room;
//            this.assignment = assignment;
//            this.academyHour = academyHour;
//            this.barcode = barcode;
//            this.employeeNumber = employeeNumber;
//            this.name = name;
//            this.fullName = fullName;
//            this.hexCode = hexCode;

            JsonObject task = e.getAsJsonObject();

            String id = task.get("id").getAsString();
            String room = task.get("room").getAsString();
            String assigment = task.get("assigment").getAsString();
            String academyHour = task.get("academyHour").getAsString();
            String barcode = task.get("barcode").getAsString();

            JsonObject owner = task.get("owner").getAsJsonObject();

            

            tasks.add((Task)context.deserialize(e, Task.class));
        }
        return null;
    }
}
