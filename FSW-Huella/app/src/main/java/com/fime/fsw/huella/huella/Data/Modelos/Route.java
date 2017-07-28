package com.fime.fsw.huella.huella.Data.Modelos;

import com.fime.fsw.huella.huella.Data.Provider.RealmProvider;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Quique on 19/07/2017.
 */

public class Route extends RealmObject {

    public static final String _ID_FIELD = "_id";

    //Keys que identifican cada campo de Route
    public static final String DAY_KEY = "routeDay";
    public static final String ACADEMY_HOUR_KEY = "routeAcademyHour";
    public static final String ASSIGNED_TO_KEY = "routeAssignedTo";

    @PrimaryKey
    private String _id;
    private String day;
    private String academyHour;
    private String assignedTo;
    private String createdAt;
    private int tasksCount;
    private RealmList<Task> tasks;
    private int currentTask;
    private int lastTask;

    public static Route createAll(String _id, String day, String academyHour, String assignedTo, int tasksCount, RealmList<Task> tasks, int currentTask, int lastTask) {
        //Missing createdAt
        Route route = new Route();
        route._id = _id;
        route.day = day;
        route.academyHour = academyHour;
        route.assignedTo = assignedTo;
        route.tasksCount = tasksCount;
        route.tasks = tasks;
        route.currentTask = currentTask;
        route.lastTask = lastTask;
        return route;
    }

    public static Route create(String _id, String day, String academyHour, String assignedTo, RealmList<Task> tasks, int currentTask, int finalTask) {
        //TODO: Buscar alternativa a esto
        Realm mRealm = Realm.getDefaultInstance();
        Route realmRoute = RealmProvider.getRouteByRouteId(mRealm, _id);
        Route route = new Route();
        route._id = _id;
        route.day = day;
        route.academyHour = academyHour;
        route.assignedTo = assignedTo;
        route.createdAt = realmRoute.getCreatedAt();
        route.tasksCount = realmRoute.getTasksCount();
        route.tasks = tasks;
        route.currentTask = currentTask;
        route.lastTask = finalTask;
        mRealm.close();
        return route;
    }

    public static Route create(String _id, String day, String academyHour, String assignedTo, String createdAt, int tasksCount) {
        Route route = new Route();
        route._id = _id;
        route.day = day;
        route.academyHour = academyHour;
        route.assignedTo = assignedTo;
        route.createdAt = createdAt;
        route.tasksCount = tasksCount;
        return route;
    }

    public String get_id() {
        return _id;
    }

    public String getDay() {
        return day;
    }

    public String getAcademyHour() {
        return academyHour;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public RealmList<Task> getTasks() {
        return tasks;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getTasksCount() {
        return tasksCount;
    }

    public int getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(int currentTask) {
        this.currentTask = currentTask;
    }

    public void moveToNextTask(){
        if (getCurrentTask() <= getLastTask()){
            setCurrentTask(getCurrentTask() + 1);
        }
    }

    public int getLastTask() {
        return lastTask;
    }

    public void setTasks(RealmList<Task> tasks) {
        this.tasks = tasks;
    }

    public void setLastTask(int lastTask) {
        this.lastTask = lastTask;
    }

    @Override
    public String toString() {
        return "Route{" +
                "_id='" + _id + '\'' +
                ", day='" + day + '\'' +
                ", academyHour='" + academyHour + '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
