package com.fime.fsw.huella.huella.Data.Modelos;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Quique on 19/07/2017.
 */

public class Route extends RealmObject {

    //Keys que identifican cada campo de Route
    public static final String _ID_KEY = "_id";
    public static final String DAY_KEY = "day";
    public static final String ACADEMY_HOUR_KEY = "academyHour";
    public static final String ASSIGNED_TO_KEY = "assignedTo";
    public static final String TASKS_KEY = "tasks";

    @PrimaryKey
    private String _id;
    private String day;
    private String academyHour;
    private String assignedTo;
    private RealmList<Task> tasks;

    public static Route create(String _id, String day, String academyHour, String assignedTo, RealmList<Task> tasks) {
        Route route = new Route();
        route._id = _id;
        route.day = day;
        route.academyHour = academyHour;
        route.assignedTo = assignedTo;
        route.tasks = tasks;
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
