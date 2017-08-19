package com.example.ensardz.registrohuella.API.Deserializadores;

import com.example.ensardz.registrohuella.Datos.Professor;
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
 * Created by ensardz on 17/08/2017.
 */

public class ProfessorDeserializer implements JsonDeserializer<List<Professor>>{
    @Override
    public List<Professor> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonArray dataArray = json.getAsJsonArray();
        List<Professor> professors = new ArrayList<>();

        for (JsonElement e : dataArray){
            JsonObject professor = e.getAsJsonObject();
            String rawName = professor.get("rawName").getAsString();
            String employeeNumber = professor.get("employeeNumber").getAsString();
            String fingerPrint = professor.get("fingerPrint").getAsString();
            professors.add(Professor.create(rawName, employeeNumber, fingerPrint));
        }

        return professors;
    }
}
