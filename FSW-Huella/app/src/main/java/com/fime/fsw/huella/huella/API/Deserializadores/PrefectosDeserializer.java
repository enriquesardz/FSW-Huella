package com.fime.fsw.huella.huella.API.Deserializadores;

import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Prefecto;
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
 * Created by quiqu on 09/09/2017.
 */

public class PrefectosDeserializer implements JsonDeserializer<List<Prefecto>> {
    @Override
    public List<Prefecto> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray data = json.getAsJsonObject().get("data").getAsJsonArray();

        List<Prefecto> prefectos = new ArrayList<>();

        for(JsonElement e : data){
            JsonObject prefectoObject = e.getAsJsonObject();

            String prefectoId = prefectoObject.get("PrefectoID").getAsString();
            String nombre = prefectoObject.get("Nombre").getAsString();
            String usuario = prefectoObject.get("Usuario").getAsString();
            String password = prefectoObject.get("Password").getAsString();
            String huella = prefectoObject.get("PrefectoID").getAsString();
            String areaId = prefectoObject.get("AreaID").getAsString();
            String tipoPeriodoId = prefectoObject.get("TipoPeriodoID").getAsString();
            String periodoId = prefectoObject.get("PeriodoID").getAsString();
            String tipo = prefectoObject.get("Tipo").getAsString();
            String nivelAcademicoId = prefectoObject.get("NivelAcademicoID").getAsString();
            String gradoAcademicoId = prefectoObject.get("GradoAcademicoID").getAsString();
            String inscripcionID = prefectoObject.get("InscripcionID").getAsString();

            prefectos.add(Prefecto.create(prefectoId, nombre, usuario, password, huella, areaId,
                    tipoPeriodoId,periodoId,tipo,nivelAcademicoId,gradoAcademicoId,inscripcionID));
        }
        return prefectos;
    }
}
