package com.fime.fsw.huella.huella.Data.Modelos.RealmObjects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Quique on 19/07/2017.
 */

public class Route extends RealmObject {

    public static final String _ID_FIELD = "_id";
    public static final String HORARIO_ID_FIELD = "horarioId";
    public static final String WAS_UPLOADED_FIELD = "wasUploaded";
    public static final String IS_COMPLETED_FIELD = "isCompleted";
    public static final String SEQUENCE_FIELD = "sequence";
    public static final String PREFECTO_USUARIO = "prefectoUsuario";

    //Keys que identifican cada campo de Route
    public static final String HORARIO_ID_KEY = "routeHorarioId";
    public static final String DIA_NOMBRE_KEY = "routeDayName";
    public static final String CURRENT_TASK_KEY = "routeCurrentTask";
    public static final String LAST_TASK_KEY = "routeLastTask";

    @PrimaryKey
    private String _id;
    private String horarioId;
    private String diaNum;
    private String diaNombre;
    private int tasksCount;
    private RealmList<Task> tasks;
    private int currentTask;
    private int lastTask;
    private boolean wasUploaded;
    private boolean isCompleted;
    private int sequence;
    private String areaId;
    private String prefectoUsuario;

    public static Route create(String _id, String horarioId, String diaNum, String diaNombre, int tasksCount,
                               RealmList<Task> tasks, int currentTask, int lastTask, int sequence,
                               String areaId, String prefectoUsuario) {
        Route route = new Route();
        route._id = _id;
        route.horarioId = horarioId;
        route.diaNum = diaNum;
        route.diaNombre = diaNombre;
        route.tasksCount = tasksCount;
        route.tasks = tasks;
        route.currentTask = currentTask;
        route.lastTask = lastTask;
        route.wasUploaded = false;
        route.isCompleted = false;
        route.sequence = sequence;
        route.areaId = areaId;
        route.prefectoUsuario = prefectoUsuario;
        return route;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getHorarioId() {
        return horarioId;
    }

    public void setHorarioId(String horarioId) {
        this.horarioId = horarioId;
    }

    public String getDiaNum() {
        return diaNum;
    }

    public void setDiaNum(String diaNum) {
        this.diaNum = diaNum;
    }

    public int getTasksCount() {
        return tasksCount;
    }

    public void setTasksCount(int tasksCount) {
        this.tasksCount = tasksCount;
    }

    public RealmList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(RealmList<Task> tasks) {
        this.tasks = tasks;
    }

    public int getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(int currentTask) {
        this.currentTask = currentTask;
    }

    public int getLastTask() {
        return lastTask;
    }

    public void setLastTask(int lastTask) {
        this.lastTask = lastTask;
    }

    public boolean isWasUploaded() {
        return wasUploaded;
    }

    public void setWasUploaded(boolean wasUploaded) {
        this.wasUploaded = wasUploaded;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getDiaNombre() {
        return diaNombre;
    }

    public void setDiaNombre(String diaNombre) {
        this.diaNombre = diaNombre;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getPrefectoUsuario() {
        return prefectoUsuario;
    }

    public void setPrefectoUsuario(String prefectoUsuario) {
        this.prefectoUsuario = prefectoUsuario;
    }

    public void moveToNextTask() {
        if (getCurrentTask() <= getLastTask()) {
            setCurrentTask(getCurrentTask() + 1);
        }
    }
}
