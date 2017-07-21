package com.fime.fsw.huella.huella.API.Deserializadores;

import com.fime.fsw.huella.huella.Data.Modelos.Assignment;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by ensardz on 20/07/2017.
 */

public class AssignmentDeserializer implements JsonDeserializer<Assignment>
{
    @Override
    public Assignment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject assignmentObject = json.getAsJsonObject();
        String rawName = assignmentObject.get("rawName").getAsString();
        String code = assignmentObject.get("code").getAsString();
        String name = assignmentObject.get("name").getAsString();
        String plan = assignmentObject.get("plan").getAsString();

        return Assignment.create(rawName,code,name,plan);
    }
}
