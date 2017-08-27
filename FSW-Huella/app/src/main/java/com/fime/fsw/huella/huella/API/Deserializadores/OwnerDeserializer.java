package com.fime.fsw.huella.huella.API.Deserializadores;

import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Owner;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by ensardz on 12/07/2017.
 */

public class OwnerDeserializer implements JsonDeserializer<Owner> {
    @Override
    public Owner deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject ownerObject = json.getAsJsonObject();

        String rawName = ownerObject.get("rawName").getAsString();
        String userType = ownerObject.get("userType").getAsString();
        String name = ownerObject.get("name").getAsString();
        String title = ownerObject.get("title").getAsString();
        String lastName = ownerObject.get("lastName").getAsString();
        String fingerPrint = ownerObject.get("fingerPrint").getAsString();
        String employeeNumber = ownerObject.get("employeeNumber").getAsString();

        Owner owner = Owner.create(rawName,userType,name,title,lastName,fingerPrint,employeeNumber);
        return owner;
    }
}
