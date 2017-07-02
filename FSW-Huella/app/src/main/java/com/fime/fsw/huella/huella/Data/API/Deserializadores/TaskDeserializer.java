package com.fime.fsw.huella.huella.Data.API.Deserializadores;

import com.fime.fsw.huella.huella.Data.API.Modelos.Task;
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

public class TaskDeserializer implements JsonDeserializer<List<Task>> {
    @Override
    public List<Task> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray m = json.getAsJsonObject().getAsJsonArray("m");
        List<Task> tasks = new ArrayList<Task>();
        for(JsonElement e : m){

            JsonObject taskJsonObject = e.getAsJsonObject();

            String id = taskJsonObject.get("id").getAsString();
            String room = taskJsonObject.get("room").getAsString();
            String assigment = taskJsonObject.get("assigment").getAsString();
            String academyHour = taskJsonObject.get("academyHour").getAsString();
            String barcode = taskJsonObject.get("barcode").getAsString();

            JsonObject owner = taskJsonObject.get("owner").getAsJsonObject();

            String employeeNumber = owner.get("employeeNumber").getAsString();
            String name = owner.get("name").getAsString();
            String fullName = owner.get("fullName").getAsString();

            JsonObject fingerPrint = owner.get("fingerPrint").getAsJsonObject();
            String hexCode = fingerPrint.get("hexCode").getAsString();

            Task task = Task.create(id,room,assigment,academyHour,barcode,employeeNumber,name, fullName,hexCode);

            tasks.add(task);
        }
        return tasks;
    }
}
