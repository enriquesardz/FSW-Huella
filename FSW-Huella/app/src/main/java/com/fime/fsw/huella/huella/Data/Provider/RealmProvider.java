package com.fime.fsw.huella.huella.Data.Provider;

import android.util.Log;

import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Route;
import com.fime.fsw.huella.huella.Data.Modelos.RealmObjects.Task;

import java.util.HashMap;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmCollection;
import io.realm.RealmResults;

import static com.fime.fsw.huella.huella.Activities.HuellaApplication.APP_TAG;

/**
 * Created by Quique on 08/07/2017.
 */

public class RealmProvider {

    private final static String TAG = APP_TAG + RealmProvider.class.getSimpleName();

    private Realm mRealm;

    public RealmProvider() {
    }

    public RealmProvider(Realm realm) {
        mRealm = realm;
    }

    //CREATE
    public static void saveTasksToRealm(Realm mRealm, final List<Task> tasks) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Task task : tasks) {
                    Task realmTask = realm.copyToRealmOrUpdate(task);
                }
            }
        });
    }

    public static void saveRouteListToRealm(Realm mRealm, final List<Route> routes) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Route route : routes) {
                    realm.copyToRealmOrUpdate(route);
                }
            }
        });
    }

    //TODO: Este metodo esta sobre-escribiendo los datos del Route , por lo que si no se le pasan
    //TODOS los datos con valores, los cambia a null o 0.
    public static void saveRouteToRealm(Realm mRealm, final Route routeResponse) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(routeResponse);
            }
        });
    }

    public static void saveRouteListWTasksToRealm(Realm mRealm, final List<Route> routes) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Route route : routes) {
                    realm.copyToRealmOrUpdate(route);
                }
            }
        });
    }

    //READ
    public static RealmResults<Task> getAllTasks(Realm realm) {
        return realm.where(Task.class).findAll();
    }

    public static OrderedRealmCollection<Task> getAllOrderedTasks(Realm mRealm) {
        return mRealm.where(Task.class).findAllSorted(Task.SEQUENCE_FIELD);
    }

    public static Task getTaskById(Realm mRealm, String _id) {
        return mRealm.where(Task.class).equalTo(Task._ID_FIELD, _id).findFirst();
    }

    public static Task getTaskBySequence(Realm mRealm, long taskSequence) {
        return mRealm.where(Task.class).equalTo(Task.SEQUENCE_FIELD, taskSequence).findFirst();
    }

    public static Route getRoute(Realm mRealm) {
        return mRealm.where(Route.class).findFirst();
    }

    public static Route getRouteByTaskId(Realm mRealm, String _id) {
        return mRealm.where(Route.class).equalTo("tasks._id", _id).findFirst();
    }

    public static Route getRouteByRouteId(Realm mRealm, String route_id) {
        return mRealm.where(Route.class).equalTo(Route._ID_FIELD, route_id).findFirst();
    }

    public static List<Task> getRouteCheckoutsFromRealm(Realm mRealm, Route route) {
        RealmCollection<Task> tasks = route.getTasks().where().notEqualTo(Task.TASK_STATE_FIELD, Task.STATE_NO_HA_PASADO).findAll();
        return mRealm.copyFromRealm(tasks);
    }

    public static OrderedRealmCollection<Route> getAllOrderedRoutes(Realm mRealm) {
        return mRealm.where(Route.class).findAllSorted(Route._ID_FIELD);
    }

    public static OrderedRealmCollection<Route> getAllUnfinishedRoutes(String user, Realm mRealm) {
        return mRealm.where(Route.class).equalTo(Route.IS_COMPLETED_FIELD, false)
                .equalTo(Route.PREFECTO_USUARIO, user)
                .findAllSorted(Route.SEQUENCE_FIELD);
    }

    public static OrderedRealmCollection<Route> getAllFinishedRoutes(Realm mRealm) {
        return mRealm.where(Route.class).equalTo(Route.IS_COMPLETED_FIELD, true).findAllSorted(Route.SEQUENCE_FIELD);
    }

    //Regresa un HashMap Key Value pair con la informacion que se muestra en la UI.
    public static HashMap<String, String> getAllDataAsStringByTask(Realm mRealm, Task task) {

        HashMap<String, String> data = new HashMap<>();
        String task_id = task.get_id();
        Route route = mRealm.where(Route.class).equalTo("tasks._id", task_id).findFirst();

        data.put(Route.HORARIO_ID_KEY, route.getHorarioId());
        data.put(Route.DIA_NOMBRE_KEY, route.getDiaNombre());
        data.put(Route.CURRENT_TASK_KEY, String.valueOf(route.getCurrentTask()));
        data.put(Route.LAST_TASK_KEY, String.valueOf(route.getLastTask()));
        data.put(Task.PLAN_ID_KEY, task.getPlanId());
        data.put(Task.MATERIA_KEY, task.getMateria());
        data.put(Task.SALON_ID_KEY, task.getSalonId());
        data.put(Task.AREA_ID_KEY, task.getAreaId());
        data.put(Task.EDIFICIO_ID_KEY, task.getEdificioId());
        data.put(Task.NUMERO_EMPLEADO_KEY, task.getNumeroEmpleado());
        data.put(Task.NOMBRE_EMPLEADO_KEY, task.getNombreEmpleado());
        data.put(Task.HUELLA_EMPLEADO_KEY, task.getHuellaEmpleado());
        data.put(Task.TIPO_KEY, task.getTipo());

        return data;
    }

    public static void dataCount(Realm mRealm) {
        int tasknum = mRealm.where(Task.class).findAll().size();
        int routenum = mRealm.where(Route.class).findAll().size();
        Log.d(TAG, "Task: " + String.valueOf(tasknum) + " Route: " + String.valueOf(routenum));
    }


    //UPDATE
    public static void setVisitAtCheckout(Realm mRealm, final Task task) {
        final String timeInMillis = String.valueOf(System.currentTimeMillis());
        //Se actualiza el visitAt del Checkout del Task
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                task.getCheckout().setVisitAt(timeInMillis);
            }
        });
        Log.i(TAG, "Se agrego el visitAt al Checkout");
        Log.d(TAG, task.getCheckout().toString());
    }

    public static void setStartedAtCheckout(Realm mRealm, final Task task) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                task.getCheckout().setStartedAt(String.valueOf(System.currentTimeMillis()));
                Log.i(TAG, task.getCheckout().toString());
            }
        });
    }

    //El codigo de barras no estaba, se le pone falta al maestro.
    public static void setCheckoutTaskValuesNoBarcode(Realm mRealm, final Task task) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                task.setTaskState(Task.STATE_PASO_NO_VINO_MAESTRO);
                task.getCheckout().setVisitAt(String.valueOf(System.currentTimeMillis()));
                task.getCheckout().setSignedAt(String.valueOf(System.currentTimeMillis()));
                task.getCheckout().setFinishedAt(String.valueOf(System.currentTimeMillis()));

                Log.i(TAG, "No se encontro salon visitAt signedAt: " + task.getCheckout().toString());
            }
        });
    }

    //Se identifico la huella del maestro y si vino
    public static void setCheckoutsTaskValuesVinoMaestro(Realm mRealm, final Task task) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                task.setTaskState(Task.STATE_PASO_VINO_MAESTRO);
                task.getCheckout().setSignedAt(String.valueOf(System.currentTimeMillis()));
                task.getCheckout().setFinishedAt(String.valueOf(System.currentTimeMillis()));

                Log.i(TAG, "Vino maestro: " + task.getCheckout().toString());
            }
        });
    }

    //El maestro no estaba o no vino.
    public static void setCheckoutsTaskValuesNoVinoMaestro(Realm mRealm, final Task task) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                task.setTaskState(Task.STATE_PASO_NO_VINO_MAESTRO);
                task.getCheckout().setSignedAt(String.valueOf(System.currentTimeMillis()));
                task.getCheckout().setFinishedAt(String.valueOf(System.currentTimeMillis()));

                Log.i(TAG, "No vino maestro: " + task.getCheckout().toString());
            }
        });
    }

    public static void moveToNextTaskByRouteId(Realm mRealm, String routeId) {
        final Route route = getRouteByRouteId(mRealm, routeId);
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                route.moveToNextTask();
            }
        });
    }

    public static void setRouteIsCompletedByRoute(Realm mRealm, final Route route) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                route.setCompleted(true);
            }
        });
    }

    public static void setRouteWasUploaded(Realm mRealm, final Route route) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                route.setWasUploaded(true);
            }
        });
    }

    //DELETE
    public static void dropAllRealmTables(Realm mRealm) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    public void deleteAllTasks(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                getAllTasks(realm).deleteAllFromRealm();
            }
        });
    }
}
