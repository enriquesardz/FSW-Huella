package com.fime.fsw.huella.huella.Data.Modelos;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Quique on 19/07/2017.
 */

public class Route extends RealmObject {
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
