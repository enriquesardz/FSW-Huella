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
        JsonObject route = json.getAsJsonArray().get(0).getAsJsonObject();

        JsonArray jsonTasks = route.get("tasks").getAsJsonArray();

        List<Task> tasks = new ArrayList<Task>();

        String academyHour = route.get("academyHour").getAsString();

        for(JsonElement e : jsonTasks){

            JsonObject jsonTaskDataObject = e.getAsJsonObject().get("data").getAsJsonObject();
            String id = e.getAsJsonObject().get("_id").getAsString();

            JsonObject taskRoom = jsonTaskDataObject.get("room").getAsJsonObject();
            JsonObject taskAssigment = jsonTaskDataObject.get("assigment").getAsJsonObject();
            JsonObject taskOwner = jsonTaskDataObject.get("owner").getAsJsonObject();

            String plan = taskAssigment.get("plan").getAsString();
            String room = taskRoom.get("room").getAsString();
            String roomDescription = taskAssigment.get("name").getAsString();
            String assignmentCode = taskAssigment.get("code").getAsString();
            String assignment = taskAssigment.get("rawName").getAsString();
            String startClassAt = "test1";
            String finishClassAt = "test2";
            String barcode = taskRoom.get("barcode").getAsString();

            JsonElement ownerObject = taskOwner;

            Owner owner = new OwnerDeserializer().deserialize(ownerObject, typeOfT, context);
            Checkout checkout = new Checkout();

            Task task = Task.create(plan,id,room,roomDescription,assignmentCode,assignment,academyHour,startClassAt,finishClassAt,barcode,owner,checkout);

            tasks.add(task);
        }
        return tasks;
    }
}
