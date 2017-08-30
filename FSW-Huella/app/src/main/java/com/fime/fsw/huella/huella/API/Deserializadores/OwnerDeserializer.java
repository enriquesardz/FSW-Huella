package com.fime.fsw.huella.huella.API.Deserializadores;

import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Owner;
import com.fime.fsw.huella.huella.R;
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

    private static final String HUELLA_ENRIQUE = "03015B1C0000C002C002C0008000800080008000800080008000800000008000800080008002C00200000000000000000000000000000000239013BE3614137E561B16DE2520915E3DA4909E0C2E263E23304DBE153C0B1E591F441F38AA4E3F152F0E5F48B249DF53B348BF3DB44B5F5A39087F5F3F1EFF19BFC91F4587561C73B9A07C678557DD6708C1BD4109803D7235DED556BCDF9D4A2C0C3A4FAD093A2FC0DEFB353FE0F70000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003015D1C00008002800000000000000000000000000000000000000000000000000080008000C002000000000000000000000000000000002310149E379413DE561B16DE2520917E3DA4909E23304DDE73C39F9E591F441F38AA4E3F152ECE9F48B249DF53B3489F3DB44B5F7235DEDF5A38C89F723A9FF74607561C4A2C0C3C4FAD88FC153C4B1C680557DD6688821D4109805D58BEDF3D19BF895D5FBF9EFD353FE0FB2FC0DEDB00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

    @Override
    public Owner deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject ownerObject = json.getAsJsonObject();

        String employeeNumber = ownerObject.get("employeeNumber").getAsString();
        String fingerPrint = HUELLA_ENRIQUE; //ownerObject.get("fingerPrint").getAsString();
        String rawName = ownerObject.get("name").getAsString();

        String userType = "";//ownerObject.get("userType").getAsString();
        String name = "";//ownerObject.get("name").getAsString();
        String title = "";//ownerObject.get("title").getAsString();
        String lastName = "";//ownerObject.get("lastName").getAsString();

        Owner owner = Owner.create(rawName,userType,name,title,lastName,fingerPrint,employeeNumber);
        return owner;
    }
}
