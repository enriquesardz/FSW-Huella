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
    private long _id;
    private String plan;
    private String id;
    private String room;
    private String roomDescription;
    private String assignmentCode;
    private String assignment;
    private String academyHour;
    private String startClassAt;
    private String finishClassAt;
    private String barcode;
    private Owner owner;
    private Checkout checkout;
    private int taskState;

    public Task(){

    }

    public static Task create(String plan, String id, String room, String roomDescription, String assignmentCode, String assignment, String academyHour, String startClassAt, String finishClassAt, String barcode, Owner owner, Checkout checkout) {
        Task task = new Task();
        task._id = HuellaApplication.TaskID.incrementAndGet();
        task.plan = plan;
        task.id = id;
        task.room = room;
        task.roomDescription = roomDescription;
        task.assignmentCode = assignmentCode;
        task.assignment = assignment;
        task.academyHour = academyHour;
        task.startClassAt = startClassAt;
        task.finishClassAt = finishClassAt;
        task.barcode = barcode;
        task.owner = owner;
        task.checkout = checkout;
        task.taskState = 0;
        return task;
    }

    public long get_id() {
        return _id;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
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

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public String getAssignmentCode() {
        return assignmentCode;
    }

    public void setAssignmentCode(String assignmentCode) {
        this.assignmentCode = assignmentCode;
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

    public String getStartClassAt() {
        return startClassAt;
    }

    public void setStartClassAt(String startClassAt) {
        this.startClassAt = startClassAt;
    }

    public String getFinishClassAt() {
        return finishClassAt;
    }

    public void setFinishClassAt(String finishClassAt) {
        this.finishClassAt = finishClassAt;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Checkout getCheckout() {
        return checkout;
    }

    public void setCheckout(Checkout checkout) {
        this.checkout = checkout;
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
                ", plan='" + plan + '\'' +
                ", id='" + id + '\'' +
                ", room='" + room + '\'' +
                ", roomDescription='" + roomDescription + '\'' +
                ", assignmentCode='" + assignmentCode + '\'' +
                ", assignment='" + assignment + '\'' +
                ", academyHour='" + academyHour + '\'' +
                ", startClassAt='" + startClassAt + '\'' +
                ", finishClassAt='" + finishClassAt + '\'' +
                ", barcode='" + barcode + '\'' +
                ", owner=" + owner +
                ", checkout=" + checkout +
                ", taskState=" + taskState +
                '}';
    }
}
