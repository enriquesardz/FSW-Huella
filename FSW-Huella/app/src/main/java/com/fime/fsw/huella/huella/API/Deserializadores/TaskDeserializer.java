package com.fime.fsw.huella.huella.API.Deserializadores;

import com.fime.fsw.huella.huella.Data.Modelos.Checkout;
import com.fime.fsw.huella.huella.Data.Modelos.Owner;
import com.fime.fsw.huella.huella.Data.Modelos.Task;
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

            String plan = taskJsonObject.get("plan").getAsString();
            String id = taskJsonObject.get("id").getAsString();
            String room = taskJsonObject.get("room").getAsString();
            String roomDescription = taskJsonObject.get("roomDescription").getAsString();
            String assignmentCode = taskJsonObject.get("assigmentCode").getAsString();
            String assignment = taskJsonObject.get("assigment").getAsString();
            String academyHour = taskJsonObject.get("academyHour").getAsString();
            String startClassAt = taskJsonObject.get("startClassAt").getAsString();
            String finishClassAt = taskJsonObject.get("finishClassAt").getAsString();
            String barcode = taskJsonObject.get("barcode").getAsString();

            JsonElement ownerObject = taskJsonObject.get("owner").getAsJsonObject();
            JsonElement checkoutObject = taskJsonObject.get("checkout").getAsJsonObject();

            Owner owner = new OwnerDeserializer().deserialize(ownerObject, typeOfT, context);
            Checkout checkout = new Checkout();

            Task task = Task.create(plan,id,room,roomDescription,assignmentCode,assignment,academyHour,startClassAt,finishClassAt,barcode,owner,checkout);

            tasks.add(task);
        }
        return tasks;
    }
}
