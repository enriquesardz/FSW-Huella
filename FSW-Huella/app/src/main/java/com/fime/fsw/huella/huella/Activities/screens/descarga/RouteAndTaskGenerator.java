package com.fime.fsw.huella.huella.Activities.screens.descarga;

import android.content.Context;
import android.util.Log;

import com.fime.fsw.huella.huella.API.Deserializadores.GroupsDeserializer;
import com.fime.fsw.huella.huella.API.Deserializadores.PrefectosDeserializer;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Grupo;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Prefecto;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Route;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Task;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

/**
 * Created by ensardz on 10/11/17.
 * Esta clase se utiliza para procesar los datos que se descargan, debido al cambio que existio en algun momento de este proyecto,
 * es la aplicacion la que genera las rutas y tareas de los prefectos. Dentro de esta clase se espera colocar todo lo relacionado a procesar
 * la informacion, se trabaja en conjunto a {@link com.fime.fsw.huella.huella.Data.Provider.RealmProvider} para poder guardar la informacion.
 * <p>
 * Si se llegara a necesitar agregar mas filtros al prefecto, es aqui donde se tendria que cambiar para que las Tareas y Rutas guarden los datos
 * necesarios.
 */

public class RouteAndTaskGenerator {

    private static final String TAG = APP_TAG + RouteAndTaskGenerator.class.getSimpleName();

    public interface onRouteAndTaskGeneration{
        public void onRouteAndTaskGenerationSuccess();
        public void onRouteAndTaskGenerationFailure();
    }

    private RouteAndTaskGenerator() {
    }

    public static RouteAndTaskGenerator getInstance() {
        return new RouteAndTaskGenerator();
    }

    /**
     * Esta funcion crea las Rutas y Tareas que utilizamos para la logica de la aplicacion, a partir de los Grupos que nos pasen.
     *
     * @param prefectos Los prefectos que nos pasan despues de descargar.
     * @param grupos    Los grupos que nos pasan despues de descargar.
     */
    public void generateRoutesAndTasks(final List<Prefecto> prefectos, final List<Grupo> grupos, final onRouteAndTaskGeneration callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Se crea una instancia de Realm que debemos de cerrar, esto al final del try catch
                Realm realm = Realm.getDefaultInstance();
                try {

                    //Se deben de generar las Rutas y Tareas de TODOS, se toma el arreglo de Prefectps y se itera, para agregarle a cada Prefecto un grupo de Rutas
                    // de su area en especifico, aparte, a cada Ruta se le agrega sus Tareas asignadas.

                    if (prefectos.isEmpty() || grupos.isEmpty()){
                        callback.onRouteAndTaskGenerationFailure();
                    }

                    //Primero tenemos que guardar estos datos a la base de datos

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            for(Prefecto prefecto: prefectos){
                                realm.copyToRealm(prefecto);
                            }
                            for(Grupo grupo : grupos){
                                realm.copyToRealm(grupo);
                            }
                        }
                    });

                    //En el paso anterior crea las tablas Prefecto y Grupo, ahora las utilizamos como consulta
                    for (Prefecto prefecto : prefectos) {

                        String prefectoAreaId = prefecto.getAreaId();
                        String prefectoUsuario = prefecto.getUsuario();

                        //Se filtran los grupos por el area del maestro
                        RealmResults<Grupo> gruposPorArea = realm
                                .where(Grupo.class)
                                .equalTo(Grupo.AREA_ID_FIELD, prefectoAreaId)
                                .findAll();

                        Log.i(TAG, String.valueOf(gruposPorArea.size()));

                        final List<Route> routes = new ArrayList<>();

                        //Primero se crean las rutas, para esto tenemos que recorrer todas las horas de FIME.
                        String[] horas = {"M", "V", "N"};

                        //La secuencia de las rutas empieza en 0
                        int rsecuencia = 0;

                        //Creamos Rutas vacias por cada hora
                        for (String hora : horas) {
                            for (int i = 1; i <= 6; i++) {
                                String _id = UUID.randomUUID().toString();
                                String horarioId = hora.concat(String.valueOf(i));
                                String diaNum = gruposPorArea.first().getDia();
                                String diaNombre = getDayName(Integer.valueOf(diaNum));

                                int tasksCount = 0;
                                int currentTask = 0;
                                int lastTask = 0;

                                //Se pasa el RealmList como valor null
                                routes.add(Route.create(_id, horarioId, diaNum, diaNombre, tasksCount, null, currentTask,
                                        lastTask, rsecuencia, prefectoAreaId, prefectoUsuario));
                                rsecuencia++;
                            }
                        }

                        //Ahora de ese arreglo de rutas vacias, lo recorremos para asignarle sus tareas
                        for (Route route : routes) {

                            RealmList<Task> tasks = new RealmList<>();
                            RealmResults<Grupo> gruposPorHora = gruposPorArea.where().equalTo(Grupo.HORARIO_ID_FIELD, route.getHorarioId()).findAll();

                            route.setTasksCount(gruposPorHora.size());
                            route.setCurrentTask(1);
                            route.setLastTask(gruposPorHora.size());

                            //Esta es la secuencia de las Tareas, en este caso empieza en el valor que se inicialice
                            int taskSecuencia = 0;
                            for (Grupo grupo : gruposPorHora) {
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
                                int sequence = taskSecuencia;
                                String prefectoId = prefecto.getPrefectoId();
                                String horaId = grupo.getHorarioId();

                                tasks.add(Task.create(_id, routeId, planId, materiaId, materia, salonId, areaId,
                                        edificioId, numeroEmpleado, nombreEmpleado, tipo, isNexus, sequence,  prefectoId,horaId));

                                taskSecuencia++;
                            }

                            route.setTasks(tasks);
                        }

                        //Ya que se agregaron la lista de Tareas a cada Ruta, se pasa el arreglo de Rutas a nuestro Realm
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(routes);
                            }
                        });
                    }
                    //Si hasta ahora no ha arrojado errores, pues regresamos el callback de exito
                    callback.onRouteAndTaskGenerationSuccess();
                }catch(Exception e){
                    e.printStackTrace();
                    //Hacer algo por si arroja excepcion
                    callback.onRouteAndTaskGenerationFailure();
                } finally {
                    //Se cierra el realm
                    realm.close();
                }
            }
        }).start();
    }

    /**
     * Esta funcion extrae Grupos y Prefectos de archivos JSON y pasa los arreglos que extraiga a la funcion generateRouteAndTasks
     * para que genere las Rutas y Tareas en la base de datos.
     * @param context Contexto de la aplicacion para hacer referencia a los archivos.
     * @param callback Callback que se pasa a la funcion generateRouteAndTasks para que lo maneje esa funcion.
     */
    public void offlineGroupsPrefectos(final Context context, final onRouteAndTaskGeneration callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Prefecto> prefectos;
                List<Grupo> grupos;
                prefectos = getPrefectosFromFile(context);
                grupos = getGroupsFromFile(context);

                generateRoutesAndTasks(prefectos,grupos,callback);
            }
        }).start();
    }

    /**
     * Esta funcion recibe el numero del dia de la semana y lo convierte a un valor
     * legible para los humanos, o sea un String del dia de la semana.
     * @param dayNum Numero del dia de la semana
     * @return String con el nombre de la fecha de hoy
     */
    public String getDayName(int dayNum) {
        String dayName;

        switch (dayNum) {
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

    /**
     * Funcion que se usa para crear un arreglo de tipo {@link Prefecto} a partir de un archivo
     * JSON
     * @param context Contexto para poder leer el archivo
     * @return Arreglo de Prefectos
     */
    public List<Prefecto> getPrefectosFromFile(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("prefectos.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer);

            Type listPrefectos = new TypeToken<List<Prefecto>>() {
            }.getType();

            GsonBuilder prefectoGson = new GsonBuilder()
                    .serializeNulls()
                    .registerTypeAdapter(listPrefectos, new PrefectosDeserializer());

            return prefectoGson.create().fromJson(json, listPrefectos);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Funcion que se usa para crear un arreglo de tipo {@link Grupo} a partir de un archivo
     * JSON
     * @param context Contexto para poder leer el archivo
     * @return Arreglo de Grupos
     */
    public List<Grupo> getGroupsFromFile(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("groups.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer);

            Type listGrupos = new TypeToken<List<Grupo>>() {
            }.getType();

            GsonBuilder groupGson = new GsonBuilder()
                    .serializeNulls()
                    .registerTypeAdapter(listGrupos, new GroupsDeserializer());

            return groupGson.create().fromJson(json, listGrupos);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
