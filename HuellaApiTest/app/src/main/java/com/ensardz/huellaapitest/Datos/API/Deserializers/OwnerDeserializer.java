package com.ensardz.huellaapitest.Datos.API.Deserializers;

import com.ensardz.huellaapitest.Datos.API.Models.Owner;
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

        String employeeNumber = ownerObject.get("employeeNumber").getAsString();
        String employeeType = ownerObject.get("type").getAsString();
        String employeeName = ownerObject.get("name").getAsString();
        String employeeFullName = ownerObject.get("fullName").getAsString();
        String fingerPrint = ownerObject.get("fingerPrint").getAsJsonObject().get("hexCode").getAsString();

        Owner owner = Owner.create(employeeNumber,employeeType,employeeName,employeeFullName,fingerPrint);
        return owner;
    }
}
