package com.fime.fsw.huella.huella.Utilidad;

import android.util.Log;

import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Grupo;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Route;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

/**
 * Created by quiqu on 10/09/2017.
 */

public class RouteAndTaskGenerator {

    private static final String TAG = APP_TAG + RouteAndTaskGenerator.class.getSimpleName();

    Realm mRealm;

    public RouteAndTaskGenerator(Realm realm) {
        mRealm = realm;
    }

    public static RouteAndTaskGenerator getInstance(Realm realm) {
        return new RouteAndTaskGenerator(realm);
    }

    public void createRoutesAndTasks() {
        //TODO: El area esta hardcodeada, el area depende del prefecto que se logee
        RealmResults<Grupo> grupoRealmResults = mRealm.where(Grupo.class).equalTo(Grupo.AREA_ID_FIELD, "01").findAll();
        Log.i(TAG, String.valueOf(grupoRealmResults.size()));

        final List<Route> routes = new ArrayList<>();

        //Primero se crean las rutas
        String[] horas = {"M", "V", "N"};

        int rsecuencia = 0;

        for (String hora : horas) {
            for (int i = 1; i <= 6; i++) {
                String _id = UUID.randomUUID().toString();
                String horarioId = hora.concat(String.valueOf(i));
                String diaNum = grupoRealmResults.first().getDia();
                String diaNombre = getDayName(Integer.valueOf(diaNum));

                int tasksCount = 0;
                int currentTask = 0;
                int lastTask = 0;

                //Se pasa el RealmList como valor null
                routes.add(Route.create(_id,horarioId,diaNum, diaNombre,tasksCount,null,currentTask,lastTask, rsecuencia));
                rsecuencia++;
            }
        }

        for(Route route : routes){
            //TODO:Hacer async?
            RealmList<Task> tasks = new RealmList<>();
            RealmResults<Grupo> gruposPorHora = grupoRealmResults.where().equalTo(Grupo.HORARIO_ID_FIELD, route.getHorarioId()).findAll();

            route.setTasksCount(gruposPorHora.size());
            route.setCurrentTask(1);
            route.setLastTask(gruposPorHora.size());

            int i =1;
            for (Grupo grupo: gruposPorHora){
                String _id = String.valueOf(grupo.getId());
                String routeId = route.get_id();
                String planId = grupo.getPlanId();
                String materiaId = grupo.getMateriaId();
                String materia = grupo.getMateria();
                String salonId = grupo.getSalonId();
                String areaId = grupo.getAreaId();
                String edificioId = grupo.getEdificioId();
                String numeroEmpleado = grupo.getNumeroEmpleado();
                String nombreEmpleado = grupo.getNombreEmpleado();
                String tipo = grupo.getTipo();
                boolean isNexus = grupo.isNexus();
                int sequence = i;

                tasks.add(Task.create(_id,routeId,planId,materiaId,materia,salonId,areaId,
                        edificioId,numeroEmpleado,nombreEmpleado,tipo,isNexus,sequence));

                i++;
            }

            route.setTasks(tasks);
        }

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(routes);
            }
        });

    }

    public String getDayName(int dayNum){
        String dayName = null;

        switch(dayNum){
            case 1:
                dayName = "Lunes";
                break;
            case 2:
                dayName = "Martes";
                break;
            case 3:
                dayName = "Miercoles";
                break;
            case 4:
                dayName = "Jueves";
                break;
            case 5:
                dayName = "Viernes";
                break;
            case 6:
                dayName = "Sabado";
                break;
            case 0:
                dayName = "Domingo";
                break;
            default:
                dayName = "Domingo";
                break;
        }

        return dayName;
    }
}
