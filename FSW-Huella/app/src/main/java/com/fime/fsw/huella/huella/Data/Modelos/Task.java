package com.fime.fsw.huella.huella.Data.Modelos;

import com.fime.fsw.huella.huella.Activities.HuellaApplication;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Quique on 29/06/2017.
 */

public class Task extends RealmObject {

    //Keys para el objeto Task
    public static final String _ID_KEY = "_id";
    public static final String ROOM_KEY = "room";
    //Estados del Task
    public static final int STATE_NO_HA_PASADO = 0;
    public static final int STATE_PASO_VINO_MAESTRO = 1;
    public static final int STATE_PASO_NO_VINO_MAESTRO = 2;

    @PrimaryKey
    private int _id;
    private String id;
    private String room;
    @SerializedName("assigment")
    private String assignment;
    private String academyHour;
    private String barcode;
    private String employeeNumber;
    private String name;
    private String fullName;
    private String hexCode;
    private int taskState;

    public Task(){

    }
    public static Task create(String id, String room, String assignment, String academyHour, String barcode, String employeeNumber, String name, String fullName, String hexCode) {
        Task task = new Task();
        task._id = HuellaApplication.TaskID.incrementAndGet();
        task.id = id;
        task.room = room;
        task.assignment = assignment;
        task.academyHour = academyHour;
        task.barcode = barcode;
        task.employeeNumber = employeeNumber;
        task.name = name;
        task.fullName = fullName;
        task.hexCode = hexCode;
        task.taskState = 0;
        return task;
    }

    public int get_id() {
        return _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getAcademyHour() {
        return academyHour;
    }

    public void setAcademyHour(String academyHour) {
        this.academyHour = academyHour;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }

    @Override
    public String toString() {
        return "Task{" +
                "_id=" + _id +
                ", id='" + id + '\'' +
                ", room='" + room + '\'' +
                ", assignment='" + assignment + '\'' +
                ", academyHour='" + academyHour + '\'' +
                ", barcode='" + barcode + '\'' +
                ", employeeNumber='" + employeeNumber + '\'' +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", hexCode='" + hexCode + '\'' +
                ", taskState=" + taskState +
                '}';
    }
}
