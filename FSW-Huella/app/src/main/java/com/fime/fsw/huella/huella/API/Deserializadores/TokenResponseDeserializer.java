package com.fime.fsw.huella.huella.API.Deserializadores;

import com.fime.fsw.huella.huella.Data.Modelos.TokenResponse;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by ensardz on 27/08/2017.
 */

public class TokenResponseDeserializer implements JsonDeserializer<TokenResponse> {
    @Override
    public TokenResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject response = json.getAsJsonObject();
        String status = response.get("status").getAsString();

        JsonObject data = response.get("data").getAsJsonObject();

        String token = data.get("token").getAsString();
        String renew = data.get("renew").getAsString();

        return new TokenResponse(status,token,renew);
    }
}
