package com.fime.fsw.huella.huella.API.Deserializadores;

import com.fime.fsw.huella.huella.Data.Modelos.Assignment;
import com.fime.fsw.huella.huella.Data.Modelos.Checkout;
import com.fime.fsw.huella.huella.Data.Modelos.Owner;
import com.fime.fsw.huella.huella.Data.Modelos.Room;
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

public class TaskDeserializer implements JsonDeserializer<Task> {
    @Override
    public Task deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject taskObject = json.getAsJsonObject();
        String _id = taskObject.get("_id").getAsString();
        int sequence = taskObject.get("sequence").getAsInt();
        Checkout checkout = new Checkout();

        //Stuff inside data object
        JsonObject data = taskObject.get("data").getAsJsonObject();
        String period = data.get("period").getAsString();
        String language = data.get("language").getAsString();
        String group = data.get("group").getAsString();

        Room room = new RoomDeserializer().deserialize(data.get("room").getAsJsonObject(), typeOfT, context);
        Assignment assignment = new AssignmentDeserializer().deserialize(data.get("assigment").getAsJsonObject(), typeOfT, context);
        Owner owner = new OwnerDeserializer().deserialize(data.get("owner").getAsJsonObject(), typeOfT, context);

        String modality = data.get("modality").getAsString();


        return Task.create(_id,sequence,period,language,group,room,assignment,owner,modality,checkout);
    }
}
