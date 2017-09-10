package com.fime.fsw.huella.huella.API.Deserializadores;

import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Grupo;
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

public class GroupsDeserializer implements JsonDeserializer<List<Grupo>> {
    @Override
    public List<Grupo> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonArray data = json.getAsJsonObject().get("data").getAsJsonArray();

        List<Grupo> grupos = new ArrayList<>();

        for(JsonElement e : data){
            JsonObject grupoObject = e.getAsJsonObject();

            int id = grupoObject.get("Id").getAsInt();
            String planId = grupoObject.get("PlanID").getAsString();
            String materiaId = grupoObject.get("MateriaID").getAsString();
            String materia = grupoObject.get("Materia").getAsString();
            String horarioId = grupoObject.get("HorarioID").getAsString();
            String grupo = grupoObject.get("Grupo").getAsString();
            String salonId = grupoObject.get("SalonID").getAsString();
            String areaId = grupoObject.get("AreaID").getAsString();
            String edificioId = grupoObject.get("EdificioID").getAsString();
            int secuenciaUno = 0;//grupoObject.get("SecuenciaUno").getAsInt();
            int secuenciaDos = 0;//grupoObject.get("SecuenciaDos").getAsInt();
            int secuenciaTres = 0;//grupoObject.get("SecuenciaTres").getAsInt();
            String dia = grupoObject.get("Dia").getAsString();
            //TODO: Switch case para que de una vez ponga el nombre del dia.
            String numeroEmpleado = grupoObject.get("NumeroEmpleado").getAsString();
            String nombreEmpleado = grupoObject.get("NombreEmpleado").getAsString();
            String tipo = grupoObject.get("Tipo").getAsString();
            String periodoId = grupoObject.get("PeriodoID").getAsString();
            String tipoPeriodoId = grupoObject.get("TipoPeriodoID").getAsString();
            String nivelAcademicoId = grupoObject.get("NivelAcademicoID").getAsString();
            String gradoAcademicoId = grupoObject.get("GradoAcademicoID").getAsString();
            boolean nexus = grupoObject.get("Nexus").getAsBoolean();
            String calendarioId = grupoObject.get("CalendarioID").getAsString();
            String inscripcionId = grupoObject.get("InscripcionID").getAsString();

            grupos.add(Grupo.create(id,planId,materiaId,materia,horarioId,grupo,salonId,areaId,edificioId,
                    secuenciaUno,secuenciaDos,secuenciaTres,dia,numeroEmpleado,nombreEmpleado,tipo,periodoId,tipoPeriodoId,
                    nivelAcademicoId,gradoAcademicoId,nexus,calendarioId,inscripcionId));
        }

        return grupos;
    }
}
