package com.fime.fsw.huella.huella.API.Deserializadores;

import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Room;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by ensardz on 20/07/2017.
 */

public class RoomDeserializer implements JsonDeserializer<Room> {
    @Override
    public Room deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject roomObject = json.getAsJsonObject();


        String area = roomObject.get("area").getAsJsonObject().get("area").getAsString();
        String building = roomObject.get("building").getAsJsonObject().get("building").getAsString();
        String barcode = roomObject.get("barcode").getAsString();
        String roomNumber = roomObject.get("room").getAsString();

        return Room.create(building,barcode,roomNumber, area);
    }
}
