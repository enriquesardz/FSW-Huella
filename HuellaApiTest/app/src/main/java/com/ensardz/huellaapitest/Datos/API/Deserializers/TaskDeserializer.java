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

public class TaskDeserializer implements JsonDeserializer<List<Task>> {
    @Override
    public List<Task> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        List<Task> tasks = new ArrayList<Task>();
        int i;
        //Clases de m1 a m6
        for (i = 1; i <= 18; i++) {
            JsonArray arregloHora = json.getAsJsonObject().getAsJsonArray(String.valueOf(i));
            //M1 a M6
            for (JsonElement elemento : arregloHora) {

                JsonObject taskObject = elemento.getAsJsonObject();

                String id = taskObject.get("_id").getAsString();
                String period = taskObject.get("period").getAsString();
                String academyHour = taskObject.get("academyHour").getAsString();
                String group = taskObject.get("group").getAsString();
                String language = taskObject.get("language").getAsString();
                String day = taskObject.get("day").getAsString();
                String modality = taskObject.get("modality").getAsString();

                JsonObject ownerObject = taskObject.get("owner").getAsJsonObject();

                String ownerId = ownerObject.get("_id").getAsString();
                String ownerRawName = ownerObject.get("rawName").getAsString();
                String ownerUserType = ownerObject.get("userType").getAsString();
                String ownerName = ownerObject.get("name").getAsString();
                String ownerLastName = ownerObject.get("lastName").getAsString();
                String ownerFingerPrint = ownerObject.get("fingerPrint").getAsString();
                String ownerEmployeeNumber = ownerObject.get("employeeNumber").getAsString();

                JsonObject assignmentObject = taskObject.get("assigment").getAsJsonObject();

                String assignmentId = assignmentObject.get("_id").getAsString();
                String assignmentRawName = assignmentObject.get("rawName").getAsString();
                String assignmentCode = assignmentObject.get("code").getAsString();
                String assignmentName = assignmentObject.get("name").getAsString();
                String assignmentPlan = assignmentObject.get("plan").getAsString();

                Task task = Task.create(id, period, academyHour, group, language, day, modality, ownerId, ownerRawName, ownerUserType, ownerName, ownerLastName, ownerFingerPrint, ownerEmployeeNumber, assignmentId, assignmentRawName, assignmentCode, assignmentName, assignmentPlan);
                tasks.add(task);
            }
        }
        return tasks;
    }
}
